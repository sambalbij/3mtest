package org.driem.api;

/**
 * Created by jettrocoenradie on 17/08/2016.
 */
public class ErrorResponse {
    private String type;
    private String message;

    public ErrorResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
