package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.PlatformCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformResponse;
import ko.tst.therealshoppingbeginsnow.dtos.PlatformUpdateRequest;
import ko.tst.therealshoppingbeginsnow.models.Platform;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlatformMapper {
    public PlatformResponse toResponse(Platform platform) {
        return new PlatformResponse(
                platform.getId(),
                platform.getName(),
                new HashSet<>(platform.getPhotos())
        );
    }

    public Set<PlatformResponse> toResponse(Set<Platform> platforms) {
        // a collector that aim to a set, lol
        return platforms.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    public Platform toModel(PlatformCreateRequest platform) {
        Platform model = new Platform(platform.name());
        if (platform.photos() != null) model.setPhotos(platform.photos());
        return model;
    }

    public Set<Platform> toModel(Set<PlatformCreateRequest> platforms) {
        return platforms.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Platform replace(Platform original, PlatformUpdateRequest platform) {
        // change the original directly
        original.setName(platform.name());
        if (platform.photos() != null) original.setPhotos(platform.photos());
        return original;
    }

    public Platform patch(Platform original, PlatformUpdateRequest platform) {
        if (platform.name() != null) {
            original.setName(platform.name());
        } else {
            original.setName(original.getName());
        }
        if (platform.photos() != null) original.setPhotos(platform.photos());
        return original;
    }
}
