package pocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pocket.entity.*;
import pocket.repository.*;
import pocket.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        this.accountRepository = accountRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public AccountEntity add(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean delete(AccountEntity account) {
        int id = account.getId();
        accountRepository.delete(account);
        return !accountRepository.existsById(id);
    }

    @Override
    public AccountEntity findById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<BookmarkEntity> getBookmarks(int accountId) {
        return bookmarkRepository.findAllByAccountId(accountId);
    }
}
