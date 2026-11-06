package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.EditorCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.EditorResponse;
import ko.tst.therealshoppingbeginsnow.dtos.EditorUpdateRequest;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformConflictException;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.EditorMapper;
import ko.tst.therealshoppingbeginsnow.repositories.EditorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EditorServiceImpl implements EditorService{

    private final EditorRepository repository;
    private final EditorMapper mapper;

    public EditorServiceImpl(EditorRepository repository, EditorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<EditorResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EditorResponse findById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Editor not found", id));
        return mapper.toResponse(p);
    }

    @Override
    @Transactional
    public EditorResponse save(EditorCreateRequest editor) {
        ensureNameIsAvailable(editor.name(), null);
        return mapper.toResponse(repository.save(mapper.toModel(editor)));
    }

    @Override
    @Transactional
    public List<EditorResponse> saveAll(List<EditorCreateRequest> editors) {
        return editors.stream()
                .map(this::save)
                .toList();
    }

    @Override
    @Transactional
    public EditorResponse replace(UUID id, EditorUpdateRequest editor) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Editor not found", id));
        ensureNameIsAvailable(editor.name(), id);
        return mapper.toResponse(mapper.replace(original, editor));
    }

    @Override
    @Transactional
    public EditorResponse update(UUID id, EditorUpdateRequest editor) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Editor not found", id));
        ensureNameIsAvailable(editor.name(), id);
        return mapper.toResponse(mapper.patch(original, editor));    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Editor not found", id));
        repository.delete(p);
    }

    private void ensureNameIsAvailable(String name, UUID currentId) {
        repository.findByNameIgnoreCase(name)
                .filter(editor -> !editor.getId().equals(currentId))
                .ifPresent(platform -> {
                    throw new PlatformConflictException("Editor already exists: " + name);
                });
    }
}
