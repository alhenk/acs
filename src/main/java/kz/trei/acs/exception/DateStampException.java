package kz.trei.acs.exception;

public class DateStampException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public DateStampException() {
        super();
    }

    public DateStampException(String message) {
        super(message);
    }

    public DateStampException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateStampException(Throwable cause) {
        super(cause);
    }

    protected DateStampException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
