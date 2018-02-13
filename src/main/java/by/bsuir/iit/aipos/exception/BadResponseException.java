package by.bsuir.iit.aipos.exception;

public class BadResponseException extends ServiceException {
    private static final long serialVersionUID = -2309810372973290547L;

    public BadResponseException() {
    }

    public BadResponseException(String message) {
        super(message);
    }

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadResponseException(Throwable cause) {
        super(cause);
    }
}
