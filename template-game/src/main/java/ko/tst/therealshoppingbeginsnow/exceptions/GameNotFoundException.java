package ko.tst.therealshoppingbeginsnow.exceptions;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message, UUID id) {
        super(message + " " + id);
    }
}
