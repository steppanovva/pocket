package pocket.repository;

import pocket.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Integer> {
    List<BookmarkEntity> findAllByAccountIdAndTag(int id, String tag);
    List<BookmarkEntity> findAllByAccountId(int id);
    void deleteAllByAccountId(int id);
    void deleteAllByAccountIdAndTag(int id, String tag);
}
