package ko.tst.therealshoppingbeginsnow.dtos;

import java.util.UUID;

public record GameFilter(
        String name,
        UUID categoryId,
        UUID developerId,
        UUID editorId,
        UUID platformId,
        Integer mark
) {
}