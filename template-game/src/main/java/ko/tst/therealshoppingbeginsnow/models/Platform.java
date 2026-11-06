package ko.tst.therealshoppingbeginsnow.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "platforms")
@SQLDelete(sql = "UPDATE platforms SET is_deleted = true, updated_at = now() WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private Long version;

    //! Warning: The name is unique, with a logical delete u couldn't add the same value
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @ElementCollection
    @CollectionTable(name = "platform_photos", joinColumns = @JoinColumn(name = "platform_id"))
    @Column(name = "photo_url", nullable = false, length = 2048)
    private Set<String> photos = new HashSet<>();

    protected Platform() {}

    public Platform(String name) {
        this.name = name;
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
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public Set<String> getPhotos() { return photos; }
    public void setPhotos(Set<String> photos) { this.photos = photos; }
}