package kz.trei.acs.exception;

public class GetParameterException extends RuntimeException{
    static final long serialVersionUID =1L;
    public GetParameterException() {
        super();
    }
    public GetParameterException(String message) {
        super(message);
    }
    public GetParameterException(String message, Throwable cause) {
        super(message, cause);
    }
    public GetParameterException(Throwable cause) {
        super(cause);
    }
    protected GetParameterException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
