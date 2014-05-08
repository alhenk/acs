package kz.trei.acs.office.rfid;

public class UidFormatException extends RuntimeException {
    static final long serialVersionUID =1L;
    public UidFormatException() {
        super();
    }
    public UidFormatException(String message) {
        super(message);
    }
    public UidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    public UidFormatException(Throwable cause) {
        super(cause);
    }
    protected UidFormatException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
