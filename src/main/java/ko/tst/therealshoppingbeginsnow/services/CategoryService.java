package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> findAll();
    CategoryResponse findById(UUID id);
    CategoryResponse save(CategoryCreateRequest category);
    List<CategoryResponse> saveAll(List<CategoryCreateRequest> categories);
    CategoryResponse replace(UUID id, CategoryUpdateRequest category);
    CategoryResponse update(UUID id, CategoryUpdateRequest category);
    void deleteById(UUID id);
}
