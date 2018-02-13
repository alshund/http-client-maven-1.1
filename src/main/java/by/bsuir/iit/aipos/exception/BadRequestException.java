package by.bsuir.iit.aipos.exception;

public class BadRequestException extends ServiceException {

    private static final long serialVersionUID = 7926440534026166151L;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
