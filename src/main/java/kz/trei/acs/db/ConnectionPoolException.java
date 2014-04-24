package kz.trei.acs.db;

/**
 * Created by alhen on 4/21/14.
 */
public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = -8032709626402655713L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    protected ConnectionPoolException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
