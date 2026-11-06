package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.DeveloperCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.DeveloperResponse;
import ko.tst.therealshoppingbeginsnow.dtos.DeveloperUpdateRequest;
import ko.tst.therealshoppingbeginsnow.services.DeveloperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/developers")
public class DeveloperRestController {
    private final DeveloperService service;

    public DeveloperRestController(DeveloperService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DeveloperResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeveloperResponse> save(
            @RequestBody @Valid DeveloperCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<DeveloperResponse>> saveAll(
            @RequestBody @Valid List<DeveloperCreateRequest> requests
    ) {
        return ResponseEntity.ok(service.saveAll(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponse> replace(
            @PathVariable UUID id,
            @RequestBody @Valid DeveloperUpdateRequest request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeveloperResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid DeveloperUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
