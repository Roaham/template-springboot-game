package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<ReviewResponse> findAllByGameId(UUID gameId);
    ReviewResponse findById(UUID gameId, UUID reviewId);
    ReviewResponse save(UUID gameId, ReviewCreateRequest request);
    ReviewResponse update(UUID gameId, UUID reviewId, ReviewUpdateRequest request);
    void deleteById(UUID gameId, UUID reviewId);
}
