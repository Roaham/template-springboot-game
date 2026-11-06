package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.services.GameServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/games")
@CrossOrigin(origins = "*")
public class GameRestController {
    private final GameServiceImpl service;

    public GameRestController(GameServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<GameResponse>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GameResponse> save(
            @RequestBody @Valid GameCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<GameResponse>> saveAll(
            @RequestBody @Valid GameCreateRequest request
    ) {
        return ResponseEntity.ok(service.saveAll(Collections.singletonList(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> replace(
            @PathVariable UUID id,
            @RequestBody @Valid GameUpdateRequest request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GameResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid GameUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable UUID id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
