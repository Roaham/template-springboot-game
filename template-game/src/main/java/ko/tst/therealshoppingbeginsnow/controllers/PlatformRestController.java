package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformResponse;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformUpdateRequest;
import ko.tst.therealshoppingbeginsnow.services.PlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/platforms")
public class PlatformRestController {
    private final PlatformService service;

    public PlatformRestController(PlatformService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlatformResponse> save(
            @RequestBody @Valid PlatformCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<PlatformResponse>> saveAll(
            @RequestBody @Valid List<PlatformCreateRequest> requests
    ) {
        return ResponseEntity.ok(service.saveAll(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponse> replace(
            @PathVariable UUID id,
            @RequestBody @Valid PlatformUpdateRequest request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlatformResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid PlatformUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
