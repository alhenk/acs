package kz.trei.acs.exception;

public class TimeStampException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public TimeStampException() {
        super();
    }

    public TimeStampException(String message) {
        super(message);
    }

    public TimeStampException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeStampException(Throwable cause) {
        super(cause);
    }

    protected TimeStampException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
