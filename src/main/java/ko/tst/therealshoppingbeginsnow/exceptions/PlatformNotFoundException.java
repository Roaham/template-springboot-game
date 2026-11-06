package ko.tst.therealshoppingbeginsnow.exceptions;

import java.util.UUID;

public class PlatformNotFoundException extends RuntimeException {
    public PlatformNotFoundException(String message, UUID id) {
        super(message + ": " + id);
    }
}
