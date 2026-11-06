package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.models.Developer;
import ko.tst.therealshoppingbeginsnow.models.Editor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeveloperMapper {
    public DeveloperResponse toResponse(Developer developer) {
        return new DeveloperResponse(
                developer.getId(),
                developer.getName(),
                new HashSet<>(developer.getPhotos())
        );
    }

    public Set<DeveloperResponse> toResponse(Set<Developer> developers) {
        // a collector that aim to a set, lol
        return developers.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    public Developer toModel(DeveloperCreateRequest developer) {
        Developer model = new Developer(developer.name());
        if (developer.photos() != null) model.setPhotos(developer.photos());
        return model;
    }

    public Set<Developer> toModel(Set<DeveloperCreateRequest> developers) {
        return developers.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Developer replace(Developer original, DeveloperUpdateRequest developer) {
        // change the original directly
        original.setName(developer.name());
        if (developer.photos() != null) original.setPhotos(developer.photos());
        return original;
    }

    public Developer patch(Developer original, DeveloperUpdateRequest developer) {
        if (developer.name() != null) {
            original.setName(developer.name());
        } else {
            original.setName(original.getName());
        }
        if (developer.photos() != null) original.setPhotos(developer.photos());
        return original;
    }
}
