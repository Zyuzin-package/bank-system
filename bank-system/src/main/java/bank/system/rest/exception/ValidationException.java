package bank.system.rest.exception;

import javax.persistence.PersistenceException;

/**
 * An error that is thrown when an entity fails validation in {@link bank.system.rest.Validator}
 */
public class ValidationException extends PersistenceException {
    public ValidationException(String message) {
        super(message);
    }
}
