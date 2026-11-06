package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/games/{gameId}/reviews")
public class ReviewRestController {
    private final ReviewService service;

    public ReviewRestController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> findAll(@PathVariable UUID gameId) {
        return ResponseEntity.ok(service.findAllByGameId(gameId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> findById(
            @PathVariable UUID gameId,
            @PathVariable UUID reviewId
    ) {
        return ResponseEntity.ok(service.findById(gameId, reviewId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> save(
            @PathVariable UUID gameId,
            @RequestBody @Valid ReviewCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(gameId, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> update(
            @PathVariable UUID gameId,
            @PathVariable UUID reviewId,
            @RequestBody @Valid ReviewUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(gameId, reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID gameId,
            @PathVariable UUID reviewId
    ) {
        service.deleteById(gameId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
