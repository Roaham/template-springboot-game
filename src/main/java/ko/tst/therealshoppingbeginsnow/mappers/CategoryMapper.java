package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.models.Category;
import ko.tst.therealshoppingbeginsnow.models.Developer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                new HashSet<>(category.getPhotos())
        );
    }

    public Set<CategoryResponse> toResponse(Set<Category> categories) {
        // a collector that aim to a set, lol
        return categories.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    public Category toModel(CategoryCreateRequest category) {
        Category model = new Category(category.name());
        if (category.photos() != null) model.setPhotos(category.photos());
        return model;
    }

    public Set<Category> toModel(Set<CategoryCreateRequest> categories) {
        return categories.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Category replace(Category original, CategoryUpdateRequest categories) {
        // change the original directly
        original.setName(categories.name());
        if (categories.photos() != null) original.setPhotos(categories.photos());
        return original;
    }

        public Category patch(Category original, CategoryUpdateRequest category) {
        if (category.name() != null) {
            original.setName(category.name());
        } else {
            original.setName(original.getName());
        }
        if (category.photos() != null) original.setPhotos(category.photos());
        return original;
    }
}
