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
    private List<BookmarkEntity> bookmarksById = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookmarkEntity> getBookmarks() {
        return bookmarksById;
    }
}
