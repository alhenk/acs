package kz.trei.acs.util;


public class SecurePasswordException extends RuntimeException{
    static final long serialVersionUID =1L;
    public SecurePasswordException() {
        super();
    }
    public SecurePasswordException(String message) {
        super(message);
    }
    public SecurePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
    public SecurePasswordException(Throwable cause) {
        super(cause);
    }
    protected SecurePasswordException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
