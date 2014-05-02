package kz.trei.acs.office.structure;

public class Table1CException extends RuntimeException {
    public Table1CException() {
        super();
    }
    public Table1CException(String message) {
        super(message);
    }
    public Table1CException(String message, Throwable cause) {
        super(message, cause);
    }
    public Table1CException(Throwable cause) {
        super(cause);
    }
    protected Table1CException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
