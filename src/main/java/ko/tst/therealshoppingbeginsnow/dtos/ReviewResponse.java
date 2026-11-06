package ko.tst.therealshoppingbeginsnow.dtos;

import java.time.Instant;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID gameId,
        String author,
        String content,
        Integer mark,
        Instant createdAt,
        Instant updatedAt
) {
}
