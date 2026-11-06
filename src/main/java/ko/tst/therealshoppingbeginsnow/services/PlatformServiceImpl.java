package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.PlatformCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformResponse;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformUpdateRequest;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformConflictException;
import ko.tst.therealshoppingbeginsnow.exceptions.PlatformNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.PlatformMapper;
import ko.tst.therealshoppingbeginsnow.models.Platform;
import ko.tst.therealshoppingbeginsnow.repositories.PlatformRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository repository;
    private final PlatformMapper mapper;

    public PlatformServiceImpl(PlatformRepository repository, PlatformMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatformResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlatformResponse findById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found", id));
        return mapper.toResponse(p);
    }

    @Override
    @Transactional
    public PlatformResponse save(PlatformCreateRequest platform) {
        ensureNameIsAvailable(platform.name(), null);
        return mapper.toResponse(repository.save(mapper.toModel(platform)));
    }

    @Override
    @Transactional
    public List<PlatformResponse> saveAll(List<PlatformCreateRequest> platforms) {
        return platforms.stream()
                .map(this::save)
                .toList();
    }

    @Override
    @Transactional
    public PlatformResponse replace(UUID id, PlatformUpdateRequest platform) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found", id));
        ensureNameIsAvailable(platform.name(), id);
        return mapper.toResponse(mapper.replace(original, platform));
    }

    @Override
    @Transactional
    public PlatformResponse update(UUID id, PlatformUpdateRequest platform) {
        var original = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found", id));
        ensureNameIsAvailable(platform.name(), id);
        return mapper.toResponse(mapper.patch(original, platform));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found", id));
        repository.delete(p);
    }

    private void ensureNameIsAvailable(String name, UUID currentId) {
        repository.findByNameIgnoreCase(name)
                .filter(platform -> !platform.getId().equals(currentId))
                .ifPresent(platform -> {
                    throw new PlatformConflictException("Platform already exists: " + name);
                });
    }
}
