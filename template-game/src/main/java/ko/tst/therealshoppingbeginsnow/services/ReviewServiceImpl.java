package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.exceptions.GameNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.ReviewMapper;
import ko.tst.therealshoppingbeginsnow.models.Game;
import ko.tst.therealshoppingbeginsnow.models.Review;
import ko.tst.therealshoppingbeginsnow.repositories.GameRepository;
import ko.tst.therealshoppingbeginsnow.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final GameRepository gameRepository;
    private final ReviewMapper mapper;

    public ReviewServiceImpl(ReviewRepository repository, GameRepository gameRepository, ReviewMapper mapper) {
        this.repository = repository;
        this.gameRepository = gameRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findAllByGameId(UUID gameId) {
        findGame(gameId);
        return repository.findAllByGameIdOrderByCreatedAtDesc(gameId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse findById(UUID gameId, UUID reviewId) {
        return mapper.toResponse(findReview(gameId, reviewId));
    }

    @Override
    @Transactional
    public ReviewResponse save(UUID gameId, ReviewCreateRequest request) {
        Game game = findGame(gameId);
        Review review = new Review(game, request.author(), request.content(), request.mark());
        return mapper.toResponse(repository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse update(UUID gameId, UUID reviewId, ReviewUpdateRequest request) {
        Review review = findReview(gameId, reviewId);
        review.setAuthor(request.author());
        review.setContent(request.content());
        review.setMark(request.mark());
        return mapper.toResponse(review);
    }

    @Override
    @Transactional
    public void deleteById(UUID gameId, UUID reviewId) {
        repository.delete(findReview(gameId, reviewId));
    }

    private Game findGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found", gameId));
    }

    private Review findReview(UUID gameId, UUID reviewId) {
        Review review = repository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + reviewId));
        if (!review.getGame().getId().equals(gameId)) {
            throw new IllegalArgumentException("Review does not belong to game: " + gameId);
        }
        return review;
    }
}
