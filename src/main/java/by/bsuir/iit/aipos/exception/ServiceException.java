package by.bsuir.iit.aipos.exception;

public class ServiceException extends Exception{

    private static final long serialVersionUID = -8754613573185912263L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
