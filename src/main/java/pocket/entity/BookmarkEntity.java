package pocket.entity;

import javax.persistence.*;

@Entity
@Table(name = "bookmark", schema = "public", catalog = "pocket")
public class BookmarkEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", length = 30)
    private String name;
    @Basic
    @Column(name = "accountId")
    private Integer accountId;
    @Basic
    @Column(name = "link", length = 100)
    private String link;
    @Basic
    @Column(name = "tag", length = 20)
    private String tag;
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private AccountEntity accountEntity;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }
}
