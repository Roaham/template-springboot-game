package ko.tst.therealshoppingbeginsnow.controllers;

import jakarta.validation.Valid;
import ko.tst.therealshoppingbeginsnow.dtos.CategoryCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.CategoryResponse;
import ko.tst.therealshoppingbeginsnow.dtos.CategoryUpdateRequest;
import ko.tst.therealshoppingbeginsnow.services.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.path:/api}/${api.version:/v1}/categories")
public class CategoryRestController {
    private final CategoryServiceImpl service;

    public CategoryRestController(CategoryServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        return ResponseEntity.ok(service.save(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<CategoryResponse>> saveAll(
            @RequestBody @Valid List<CategoryCreateRequest> requests
    ) {
        return ResponseEntity.ok(service.saveAll(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> replace(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryUpdateRequest request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryUpdateRequest request
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
