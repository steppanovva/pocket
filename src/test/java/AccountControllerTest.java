import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pocket.controller.AccountController;
import pocket.entity.*;
import pocket.service.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = AccountController.class)
public class AccountControllerTest {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BookmarkService bookmarkService;

    @Test
    public void getAllAccounts_HttpStatusIsOk() throws Exception {
        List<AccountEntity> accounts = new ArrayList<>();
        accounts.add(new AccountEntity());
        Mockito.when(accountService.getAll()).thenReturn(accounts);
        mvc.perform(get("/accounts")).andExpect(status().isOk());
    }

    @Test
    public void getExistingAccountByGivenId_HttpStatusIsFound() throws Exception {
        AccountEntity account = new AccountEntity();
        Mockito.when(accountService.findById(anyInt())).thenReturn(account);
        mvc.perform(get("/accounts/1")).andExpect(status().isFound());
    }

    @Test
    public void getNonExistingAccountByGivenId_HttpStatusIsNotFound() throws Exception {
        Mockito.when(accountService.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/accounts/1")).andExpect(status().isNotFound());
    }

    @Test
    public void getBookmarksByAccountId_HttpStatusIsOk() throws Exception {
        List<BookmarkEntity> bookmarks = new ArrayList<>();
        bookmarks.add(new BookmarkEntity());
        Mockito.when(accountService.getBookmarks(anyInt())).thenReturn(bookmarks);
        mvc.perform(get("/accounts/1/bookmarks")).andExpect(status().isOk());
    }

    @Test
    public void getFilteredBookmarksByTag_HttpStatusIsOk() throws Exception {
        List<BookmarkEntity> bookmarks = new ArrayList<>();
        bookmarks.add(new BookmarkEntity());
        Mockito.when(accountService.getBookmarksFilteredByTag(anyInt(), anyString())).thenReturn(bookmarks);
        mvc.perform(get("/accounts/1/bookmarks/films")).andExpect(status().isOk());
    }

    @Test
    public void addAccount_HttpStatusIsCreated() throws Exception {
        AccountEntity account = new AccountEntity();
        account.setName("User");
        Mockito.when(accountService.add(Mockito.any())).thenReturn(account);
        mvc.perform(post("/accounts").content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("User"));
    }

    @Test
    public void addBookmark_HttpStatusIsCreated() throws Exception {
        BookmarkEntity bookmark = new BookmarkEntity();
        AccountEntity account = new AccountEntity();
        account.setId(0);
        bookmark.setTag("films");
        bookmark.setAccountId(0);
        bookmark.setLink("https://en.wikipedia.org/wiki/The_Shawshank_Redemption");
        bookmark.setName("The Shawshank Redemption");
        Mockito.when(accountService.findById(anyInt())).thenReturn(account);
        Mockito.when(bookmarkService.add(Mockito.any())).thenReturn(bookmark);
        mvc.perform(post("/accounts/0/bookmarks").content(mapper.writeValueAsString(bookmark))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.link")
                        .value("https://en.wikipedia.org/wiki/The_Shawshank_Redemption"))
                .andExpect(jsonPath("$.tag").value("films"));
    }

    @Test
    public void deleteAccount_HttpStatusIsOk() throws Exception {
        AccountEntity account = new AccountEntity();
        Mockito.when(accountService.findById(anyInt())).thenReturn(account);
        Mockito.when(accountService.delete(Mockito.any())).thenReturn(true);
        mvc.perform(delete("/accounts/1")).andExpect(status().isOk());
    }

    @Test
    public void deleteBookmarksByTag_HttpStatusIsOk() throws Exception {
        Mockito.when(bookmarkService.deleteAllByAccountIdAndTag(anyInt(), anyString())).thenReturn(true);
        mvc.perform(delete("/accounts/1/bookmarks/films")).andExpect(status().isOk());
    }

    @Test
    public void deleteBookmarkById_HttpStatusIsOk() throws Exception {
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setAccountId(1);
        Mockito.when(bookmarkService.findById(anyInt())).thenReturn(bookmark);
        Mockito.when(bookmarkService.delete(Mockito.any())).thenReturn(true);
        mvc.perform(delete("/accounts/1/bookmarks/list/1")).andExpect(status().isOk());
    }
}
