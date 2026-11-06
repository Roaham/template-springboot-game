package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.DeveloperCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.DeveloperResponse;
import ko.tst.therealshoppingbeginsnow.dtos.DeveloperUpdateRequest;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformConflictException;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.DeveloperMapper;
import ko.tst.therealshoppingbeginsnow.repositories.DeveloperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeveloperServiceImpl implements DeveloperService{
    private final DeveloperRepository repository;
    private final DeveloperMapper mapper;

    public DeveloperServiceImpl(DeveloperRepository repository, DeveloperMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<DeveloperResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperResponse findById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Developer not found", id));
        return mapper.toResponse(p);
    }

    @Override
    @Transactional
    public DeveloperResponse save(DeveloperCreateRequest developer) {
        ensureNameIsAvailable(developer.name(), null);
        return mapper.toResponse(repository.save(mapper.toModel(developer)));
    }

    @Override
    @Transactional
    public List<DeveloperResponse> saveAll(List<DeveloperCreateRequest> developers) {
        return developers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    @Transactional
    public DeveloperResponse replace(UUID id, DeveloperUpdateRequest developer) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Developer not found", id));
        ensureNameIsAvailable(developer.name(), id);
        return mapper.toResponse(mapper.replace(original, developer));
    }

    @Override
    @Transactional
    public DeveloperResponse update(UUID id, DeveloperUpdateRequest developer) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Developer not found", id));
        ensureNameIsAvailable(developer.name(), id);
        return mapper.toResponse(mapper.patch(original, developer));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Developer not found", id));
        repository.delete(p);
    }

    private void ensureNameIsAvailable(String name, UUID currentId) {
        repository.findByNameIgnoreCase(name)
                .filter(developer -> !developer.getId().equals(currentId))
                .ifPresent(platform -> {
                    throw new PlatformConflictException("Developer already exists: " + name);
                });
    }
}
