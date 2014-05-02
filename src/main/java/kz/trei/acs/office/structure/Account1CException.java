package kz.trei.acs.office.structure;

public class Account1CException extends RuntimeException {
    public Account1CException() {
        super();
    }
    public Account1CException(String message) {
        super(message);
    }
    public Account1CException(String message, Throwable cause) {
        super(message, cause);
    }
    public Account1CException(Throwable cause) {
        super(cause);
    }
    protected Account1CException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
