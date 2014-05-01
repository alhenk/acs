package kz.trei.acs.db;

import javax.servlet.ServletException;

public class ConnectionPoolException extends ServletException {
    private static final long serialVersionUID = -8032709626402655713L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public ConnectionPoolException(Throwable rootCause) {
        super(rootCause);
    }

    public Throwable getRootCause() {
        return getRootCause();
    }
}
