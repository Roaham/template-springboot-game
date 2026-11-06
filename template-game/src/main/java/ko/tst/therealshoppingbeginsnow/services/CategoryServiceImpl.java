package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.CategoryCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.CategoryResponse;
import ko.tst.therealshoppingbeginsnow.dtos.CategoryUpdateRequest;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformConflictException;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.CategoryMapper;
import ko.tst.therealshoppingbeginsnow.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Category not found", id));
        return mapper.toResponse(p);    }

    @Override
    @Transactional
    public CategoryResponse save(CategoryCreateRequest category) {
        ensureNameIsAvailable(category.name(), null);
        return mapper.toResponse(repository.save(mapper.toModel(category)));    }

    @Override
    @Transactional
    public List<CategoryResponse> saveAll(List<CategoryCreateRequest> categories) {
        return categories.stream()
                .map(this::save)
                .toList();    }

    @Override
    @Transactional
    public CategoryResponse replace(UUID id, CategoryUpdateRequest category) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Category not found", id));
        ensureNameIsAvailable(category.name(), id);
        return mapper.toResponse(mapper.replace(original, category));    }

    @Override
    @Transactional
    public CategoryResponse update(UUID id, CategoryUpdateRequest category) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Category not found", id));
        ensureNameIsAvailable(category.name(), id);
        return mapper.toResponse(mapper.patch(original, category));    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Category not found", id));
        repository.delete(p);
    }

    private void ensureNameIsAvailable(String name, UUID currentId) {
        repository.findByNameIgnoreCase(name)
                .filter(category -> !category.getId().equals(currentId))
                .ifPresent(platform -> {
                    throw new PlatformConflictException("Category already exists: " + name);
                });
    }
}
