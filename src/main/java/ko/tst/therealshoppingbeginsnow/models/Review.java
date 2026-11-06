package ko.tst.therealshoppingbeginsnow.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "game_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(nullable = false)
    private Integer mark;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Review() {
    }

    public Review(Game game, String author, String content, Integer mark) {
        this.game = game;
        this.author = author;
        this.content = content;
        this.mark = mark;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public Game getGame() { return game; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public Integer getMark() { return mark; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setMark(Integer mark) { this.mark = mark; }
}
