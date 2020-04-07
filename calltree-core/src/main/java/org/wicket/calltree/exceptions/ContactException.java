package org.wicket.calltree.exceptions;

/**
 * @author Alessandro Arosio - 07/04/2020 22:11
 */
public class ContactException extends RuntimeException {
    public ContactException() {
    }

    public ContactException(String message) {
        super(message);
    }

    public ContactException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactException(Throwable cause) {
        super(cause);
    }

    public ContactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
