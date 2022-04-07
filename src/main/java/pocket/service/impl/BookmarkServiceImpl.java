package pocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pocket.entity.BookmarkEntity;
import pocket.repository.BookmarkRepository;
import pocket.service.BookmarkService;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository repository;

    @Autowired
    public BookmarkServiceImpl(BookmarkRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookmarkEntity add(BookmarkEntity bookmark) {
        return repository.save(bookmark);
    }

    @Override
    public boolean delete(BookmarkEntity bookmark) {
        int id = bookmark.getId();
        repository.delete(bookmark);
        return !repository.existsById(id);
    }

    @Override
    public BookmarkEntity findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<BookmarkEntity> findAllByTag(String tag) {
        return repository.findAllByTag(tag);
    }

    @Override
    public List<BookmarkEntity> findAllByAccountId(int id) {
        return repository.findAllByAccountId(id);
    }

}
