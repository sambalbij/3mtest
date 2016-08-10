package org.driem.api;

/**
 * Created by jettrocoenradie on 10/08/2016.
 */
public class NonUniqueParticipantNameException extends RuntimeException {
    public NonUniqueParticipantNameException(String message) {
        super(message);
    }

    public NonUniqueParticipantNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
