package ko.tst.therealshoppingbeginsnow.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;
import java.util.Set;

public record GameCreateRequest(
        @NotBlank(message = "Name necessary")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,
        @NotBlank(message = "Description necessary")
        @Size(max = 3000, message = "Description cannot exceed 3000 characters")
        String description,

        Set<UUID> platformIds,
        Set<UUID> developerIds,
        Set<UUID> editorIds,
        Set<UUID> categoryIds,
        Set<String> photos,
        @Min(value = 0, message = "Mark between 0 nd 100")
        @Max(value = 100, message = "Mark between 0 nd 100")
        Integer mark
) {
}
