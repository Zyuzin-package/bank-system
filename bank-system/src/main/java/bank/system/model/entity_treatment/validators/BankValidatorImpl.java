package bank.system.model.entity_treatment.validators;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.model.entity_treatment.validators.api.Validator;
import bank.system.rest.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Bank validator. There is no need to check nested entities;
 * entities are already received from the client in the required format.
 */
@Component
public class BankValidatorImpl implements Validator<Bank> {
    private final UUIDValidatorImpl uuidValidatorImpl;
    private static final String nameRegex = "[a-zA-Z\\s]+";

    public BankValidatorImpl(UUIDValidatorImpl uuidValidatorImpl) {
        this.uuidValidatorImpl = uuidValidatorImpl;
    }
    /**
     * The method that processes {@link Bank}. In case of an error in one or more fields - throws an error {@link ValidationException}.
     * @param bank Entity to check
     * @return true if entity successfully passed complete validation, else - throws {@link ValidationException}.
     */
    @Override
    public Bank validate(Bank bank) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        UUID uuid = bank.getId();
        if (uuid!=null){
            if (!uuidValidatorImpl.validate(uuid.toString()).isEmpty()){
                noError=false;
            }
        }

        String title = bank.getTitle();
        if (title == null) {
            errorMessage.append("title must be not be empty|");
            noError = false;
        } else if (!title.matches(nameRegex)){
            errorMessage.append("title cannot contain numbers|");
            noError = false;

        }

        if (!noError) {
            throw new ValidationException(errorMessage.toString());
        }
        return bank;
    }
}
