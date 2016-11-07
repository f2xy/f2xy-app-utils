package io.f2xy.app.utils.exceptions;

/**
 * Date: 27.02.2014
 *
 * @author Hakan GÜR
 * @version 1.0
 */
public abstract class ApplicationException extends Exception {

    protected ApplicationException() {
    }

    protected ApplicationException(String message) {
        super(message);
    }

    protected ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ApplicationException(Throwable cause) {
        super(cause);
    }
}
