package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.EditorCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.EditorResponse;
import ko.tst.therealshoppingbeginsnow.dtos.EditorUpdateRequest;
import ko.tst.therealshoppingbeginsnow.services.EditorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/editors")
public class EditorRestController {
    private final EditorService service;

    public EditorRestController(EditorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EditorResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EditorResponse> save(
            @RequestBody @Valid EditorCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<EditorResponse>> saveAll(
            @RequestBody @Valid List<EditorCreateRequest> requests
    ) {
        return ResponseEntity.ok(service.saveAll(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditorResponse> replace(
            @PathVariable UUID id,
            @RequestBody @Valid EditorUpdateRequest request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditorResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid EditorUpdateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
