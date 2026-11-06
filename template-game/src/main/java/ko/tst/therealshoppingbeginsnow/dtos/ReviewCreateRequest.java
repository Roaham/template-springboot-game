package ko.tst.therealshoppingbeginsnow.dtos;

import jakarta.validation.constraints.*;

public record ReviewCreateRequest(
        @NotBlank @Size(max = 100) String author,
        @NotBlank @Size(max = 3000) String content,
        @NotNull @Min(0) @Max(100) Integer mark
) {
}
