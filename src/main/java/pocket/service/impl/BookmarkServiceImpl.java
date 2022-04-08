package pocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pocket.entity.AccountEntity;
import pocket.entity.BookmarkEntity;
import pocket.exception.PocketException;
import pocket.repository.AccountRepository;
import pocket.repository.BookmarkRepository;
import pocket.service.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public BookmarkEntity add(BookmarkEntity bookmark) throws PocketException {
        AccountEntity account = accountRepository.findById(bookmark.getAccountId())
                .orElseThrow(() -> new PocketException("No account with id " + bookmark.getAccountId() + " exists"));
        account.addBookmark(bookmark);
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public boolean delete(BookmarkEntity bookmark) throws PocketException {
        int id = bookmark.getId();
        AccountEntity account = accountRepository.findById(bookmark.getAccountId())
                .orElseThrow(() -> new PocketException("No account with id " + bookmark.getAccountId() + " exists"));
        account.removeBookmark(bookmark);
        bookmarkRepository.delete(bookmark);
        return !bookmarkRepository.existsById(id);
    }

    @Override
    public boolean deleteAllByAccountId(int id) {
        bookmarkRepository.deleteAllByAccountId(id);
        return bookmarkRepository.findAllByAccountId(id).isEmpty();
    }

    @Override
    public boolean deleteAllByAccountIdAndTag(int id, String tag) {
        bookmarkRepository.deleteAllByAccountIdAndTag(id, tag);
        return bookmarkRepository.findAllByAccountIdAndTag(id, tag).isEmpty();
    }

    @Override
    public BookmarkEntity findById(int id) {
        return bookmarkRepository.findById(id).orElse(null);
    }
}
