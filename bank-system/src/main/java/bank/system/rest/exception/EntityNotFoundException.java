package bank.system.rest.exception;

import javax.persistence.PersistenceException;

/**
 * The error that is thrown when the entity is not found in the database
 */
public class EntityNotFoundException extends PersistenceException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
