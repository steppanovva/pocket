package pocket.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account", schema = "public", catalog = "pocket")
public class AccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "accountId")
    private List<BookmarkEntity> bookmarks = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBookmark(BookmarkEntity bookmark) {
        bookmark.setAccountId(this.id);
        bookmarks.add(bookmark);
    }

    public void removeBookmark(BookmarkEntity bookmark) {
        bookmarks.remove(bookmark);
    }

    public List<BookmarkEntity> getBookmarks() {
        return bookmarks;
    }
}
