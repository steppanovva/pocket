package pocket.service;

import pocket.entity.AccountEntity;
import pocket.entity.BookmarkEntity;

import java.util.List;

public interface AccountService {
    AccountEntity add(AccountEntity account);
    boolean delete(AccountEntity account);
    AccountEntity findById(int id);
    List<BookmarkEntity> getBookmarks(int accountId);
}
