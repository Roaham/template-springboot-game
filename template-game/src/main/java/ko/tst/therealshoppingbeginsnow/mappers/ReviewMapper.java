package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.ReviewResponse;
import ko.tst.therealshoppingbeginsnow.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getGame().getId(),
                review.getAuthor(),
                review.getContent(),
                review.getMark(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
