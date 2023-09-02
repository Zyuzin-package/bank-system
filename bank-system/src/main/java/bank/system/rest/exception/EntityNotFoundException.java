package bank.system.rest.exception;

import javax.persistence.PersistenceException;

public class EntityNotFoundException extends PersistenceException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
