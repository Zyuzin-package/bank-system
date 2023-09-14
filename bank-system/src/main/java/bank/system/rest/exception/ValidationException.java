package bank.system.rest.exception;

import javax.persistence.PersistenceException;

/**
 * An error that is thrown when an entity fails validation at {@link bank.system.model.entity_treatment.validators.api.Validator}
 */
public class ValidationException extends PersistenceException {
    public ValidationException(String message) {
        super(message);
    }
}
