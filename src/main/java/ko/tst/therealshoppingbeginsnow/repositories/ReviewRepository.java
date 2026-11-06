package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByGameIdOrderByCreatedAtDesc(UUID gameId);
}
