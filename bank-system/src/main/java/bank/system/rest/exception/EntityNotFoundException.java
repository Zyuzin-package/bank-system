package bank.system.model.exception;

import javax.persistence.PersistenceException;

public class EntityNotFoundException extends PersistenceException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
