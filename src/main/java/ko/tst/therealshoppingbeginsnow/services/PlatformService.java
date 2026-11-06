package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.PlatformCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformResponse;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlatformService {
    List<PlatformResponse> findAll();
    PlatformResponse findById(UUID id);
    PlatformResponse save(PlatformCreateRequest platform);
    List<PlatformResponse> saveAll(List<PlatformCreateRequest> platforms);
    PlatformResponse replace(UUID id, PlatformUpdateRequest platform);
    PlatformResponse update(UUID id, PlatformUpdateRequest platform);
    void deleteById(UUID id);
}
