package bank.system.rest.exception;

import javax.persistence.PersistenceException;

public class ValidationException extends PersistenceException {
    public ValidationException(String message) {
        super(message);
    }
}
