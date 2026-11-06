package ko.tst.therealshoppingbeginsnow.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String name,
        String description,
        Set<PlatformResponse> platforms,
        Set<DeveloperResponse> developers,
        Set<EditorResponse> editors,
        Set<CategoryResponse> categories,
        Set<String> photos,
        Integer mark,
        Instant createdAt,
        Instant updatedAt
) {
}
