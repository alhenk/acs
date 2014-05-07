package kz.trei.acs.dao;

import javax.servlet.ServletException;
import java.io.Serializable;

public class DaoException extends Exception {
    private static final long serialVersionUID = 1L;

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public DaoException(Throwable rootCause) {
        super(rootCause);
    }
    public Throwable getRootCause() {
        return getRootCause();
    }
}
