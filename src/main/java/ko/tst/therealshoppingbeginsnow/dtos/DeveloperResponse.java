package ko.tst.therealshoppingbeginsnow.dtos;

import java.util.UUID;
import java.util.Set;

public record DeveloperResponse(UUID id, String name, Set<String> photos) {
}
