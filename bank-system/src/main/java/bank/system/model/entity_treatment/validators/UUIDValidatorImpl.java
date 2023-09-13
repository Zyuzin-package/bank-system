package bank.system.model.entity_treatment.validators;

import bank.system.model.entity_treatment.validators.api.Validator;
import bank.system.rest.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UUIDValidatorImpl implements Validator<String> {
    /**
     * The method that processes {@link java.util.UUID}. In case of an error in one or more fields - throws an error {@link ValidationException}.
     * @param uuid - Entity to check
     * @return - true if entity successfully passed complete validation, else - throws {@link ValidationException}.
     */
    public String validate(String uuid) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;
        if (uuid != null) {
            if (!uuid.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                errorMessage.append("Incorrect id|");
                noError = false;
            }
        } else {
            errorMessage.append("Incorrect id|");
            noError = false;
        }
        if (!noError) {
            throw new ValidationException(errorMessage.toString());
        }
        return errorMessage.toString();
    }
}
