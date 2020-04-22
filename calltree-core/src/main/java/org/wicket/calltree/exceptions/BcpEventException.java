package org.wicket.calltree.exceptions;

/**
 * @author Alessandro Arosio - 15/04/2020 22:56
 */
public class BcpEventException extends RuntimeException {
    public BcpEventException() {
    }

    public BcpEventException(String message) {
        super(message);
    }

    public BcpEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public BcpEventException(Throwable cause) {
        super(cause);
    }

    public BcpEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
