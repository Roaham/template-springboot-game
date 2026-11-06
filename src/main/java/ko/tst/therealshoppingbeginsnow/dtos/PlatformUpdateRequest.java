package ko.tst.therealshoppingbeginsnow.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record PlatformUpdateRequest(
        @NotBlank(message = "Name necessary")
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        String name,
        Set<String> photos
) {
}
