package pocket.service;

import pocket.entity.BookmarkEntity;

import java.util.List;

public interface BookmarkService {
    BookmarkEntity add(BookmarkEntity bookmark);
    boolean delete(BookmarkEntity bookmark);
    BookmarkEntity findById(int id);
    List<BookmarkEntity> findAllByTag(String tag);
    List<BookmarkEntity> findAllByAccountId(int id);
}
