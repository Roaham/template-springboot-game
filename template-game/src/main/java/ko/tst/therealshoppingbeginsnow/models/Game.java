package ko.tst.therealshoppingbeginsnow.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
* I use the fk_<middle_table>_<referenced_table> for the fk
*/

@Entity
@Table(name = "games", indexes = {
        @Index(name = "idx_game_name", columnList = "name"),
        @Index(name = "idx_game_deleted", columnList = "is_deleted")
})
@SQLDelete(sql = "UPDATE games SET is_deleted = true, updated_at = now() WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = true, unique = false, length = 3000)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"),
            foreignKey = @ForeignKey(name = "fk_game_platform_game"),
            inverseForeignKey = @ForeignKey(name = "fk_game_platform_platform")
    )
    private Set<Platform> platforms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"),
            foreignKey = @ForeignKey(name = "fk_game_developer_game"),
            inverseForeignKey = @ForeignKey(name = "fk_game_developer_developer")
    )
    private Set<Developer> developers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_editor",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "editor_id"),
            foreignKey = @ForeignKey(name = "fk_game_editor_game"),
            inverseForeignKey = @ForeignKey(name = "fk_game_editor_editor")
    )
    private Set<Editor> editors = new HashSet<>();

    // In N:N Lazy is the default value
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            foreignKey = @ForeignKey(name = "fk_game_category_game"),
            inverseForeignKey = @ForeignKey(name = "fk_game_category_category")
    )
    private Set<Category> categories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "game_photos", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "photo_url", nullable = false, length = 2048)
    private Set<String> photos = new HashSet<>();

    @Column()
    private Integer mark;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private Long version;

    //! Warning: The name is unique, with a logical delete u couldn't add the same value
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    protected Game() {}

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        normalize();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        normalize();
    }

    private void normalize() {
        if (name != null) {
            this.name = name.trim();
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Set<Platform> getPlatforms() { return platforms; }
    public void setPlatforms(Set<Platform> platforms) { this.platforms = platforms; }
    public Set<Developer> getDevelopers() { return developers; }
    public void setDevelopers(Set<Developer> developers) { this.developers = developers; }
    public Set<Editor> getEditors() { return editors; }
    public void setEditors(Set<Editor> editors) { this.editors = editors; }
    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }
    public Set<String> getPhotos() { return photos; }
    public void setPhotos(Set<String> photos) { this.photos = photos; }
    public Integer getMark() { return mark; }
    public void setMark(Integer mark) { this.mark = mark; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
