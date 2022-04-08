package pocket.service;

import pocket.entity.AccountEntity;
import pocket.entity.BookmarkEntity;

import java.util.List;

public interface AccountService {
    AccountEntity add(AccountEntity account);
    boolean delete(AccountEntity account);
    boolean update(int id, AccountEntity updated);
    AccountEntity findById(int id);
    List<BookmarkEntity> getBookmarks(int accountId);
    List<BookmarkEntity> getBookmarksFilteredByTag(int accountId, String tag);
    List<AccountEntity> getAll();
}
