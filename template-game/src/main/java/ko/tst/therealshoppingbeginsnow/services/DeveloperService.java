package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;

import java.util.List;
import java.util.UUID;

public interface DeveloperService {
    List<DeveloperResponse> findAll();
    DeveloperResponse findById(UUID id);
    DeveloperResponse save(DeveloperCreateRequest developer);
    List<DeveloperResponse> saveAll(List<DeveloperCreateRequest> developers);
    DeveloperResponse replace(UUID id, DeveloperUpdateRequest developer);
    DeveloperResponse update(UUID id, DeveloperUpdateRequest developer);
    void deleteById(UUID id);
}
