package pocket.service;

import pocket.entity.BookmarkEntity;
import pocket.exception.PocketException;

public interface BookmarkService {
    BookmarkEntity add(BookmarkEntity bookmark) throws PocketException;
    boolean delete(BookmarkEntity bookmark) throws PocketException;
    boolean deleteAllByAccountId(int id);
    boolean deleteAllByAccountIdAndTag(int id, String tag);
    BookmarkEntity findById(int id);
}