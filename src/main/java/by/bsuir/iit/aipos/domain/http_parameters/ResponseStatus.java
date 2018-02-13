package by.bsuir.iit.aipos.domain.http_parameters;

public enum ResponseStatus {
    OK, CREATED, ACCEPTED, NO_CONTENT,

    MOVED_PERMANENTLY, FOUND, NOT_MODIFIED,

    BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND,

    INTERNAL_SERVER_ERROR, NOT_IMPLEMENTED, BAD_GATEWAY, SERVICE_UNAVAILABLE;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static String getRegPattern() {
        return "httpPatterns.statusLine.regexp";
    }

    public static int getStatusMessage() {
        return 2;
    }

    public static int getStatusName() {
        return 4;
    }
}
