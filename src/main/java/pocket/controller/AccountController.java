package pocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pocket.entity.AccountEntity;
import pocket.entity.BookmarkEntity;
import pocket.exception.PocketException;
import pocket.service.AccountService;
import pocket.service.BookmarkService;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final BookmarkService bookmarkService;

    @Autowired
    public AccountController(AccountService accountService, BookmarkService bookmarkService) {
        this.accountService = accountService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/accounts")
    ResponseEntity<List<AccountEntity>> all() {
        List<AccountEntity> accounts = accountService.getAll();
        return accounts != null && !accounts.isEmpty() ? new ResponseEntity<> (accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{id}")
    ResponseEntity<AccountEntity> findAccount(@PathVariable int id) {
        AccountEntity account = accountService.findById(id);
        return account != null ? new ResponseEntity<>(account, HttpStatus.FOUND)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{id}/bookmarks")
    ResponseEntity<List<BookmarkEntity>> bookmarks(@PathVariable int id) {
        List<BookmarkEntity> bookmarks = accountService.getBookmarks(id);
        return bookmarks != null && !bookmarks.isEmpty() ? new ResponseEntity<>(bookmarks, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{id}/bookmarks/{tag}")
    ResponseEntity<List<BookmarkEntity>> filterBookmarksByTag(@PathVariable int id, @PathVariable String tag) {
        List<BookmarkEntity> filtered = accountService.getBookmarksFilteredByTag(id, tag);
        return filtered != null && !filtered.isEmpty() ? new ResponseEntity<>(filtered, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/accounts")
    ResponseEntity<AccountEntity> add(@RequestBody AccountEntity account) {
        AccountEntity added = accountService.add(account);
        return added != null ? new ResponseEntity<>(added, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/accounts/{id}/bookmarks")
    ResponseEntity<BookmarkEntity> addBookmark(@PathVariable int id, @RequestBody BookmarkEntity bookmark) throws PocketException {
        if (accountService.findById(id) != null && bookmark.getAccountId() == id) {
            BookmarkEntity added = bookmarkService.add(bookmark);
            return added != null ? new ResponseEntity<>(added, HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/accounts/{id}")
    ResponseEntity<?> update(@PathVariable int id, @RequestBody AccountEntity updated) {
        boolean result = accountService.update(id, updated);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/accounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable int id) {
        boolean result = accountService.delete(accountService.findById(id));
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/accounts/{id}/bookmarks")
    ResponseEntity<?> deleteBookmarks(@PathVariable int id) {
        boolean result = bookmarkService.deleteAllByAccountId(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/accounts/{id}/bookmarks/{tag}")
    ResponseEntity<?> deleteBookmarksByTag(@PathVariable int id, @PathVariable String tag) {
        boolean result = bookmarkService.deleteAllByAccountIdAndTag(id, tag);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/accounts/{accountId}/bookmarks/list/{bookmarkId}")
    ResponseEntity<?> deleteBookmark(@PathVariable int accountId, @PathVariable int bookmarkId) throws PocketException {
        BookmarkEntity bookmark = bookmarkService.findById(bookmarkId);
        if (bookmark != null && bookmark.getAccountId() == accountId) {
            boolean result = bookmarkService.delete(bookmark);
            return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
