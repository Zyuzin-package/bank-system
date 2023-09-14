package bank.system.model.entity_treatment.validators;

import bank.system.model.domain.Client;
import bank.system.model.entity_treatment.formatters.ClientFormatter;
import bank.system.model.entity_treatment.validators.api.Validator;
import bank.system.rest.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ClientValidatorImpl implements Validator<Client> {
    private final UUIDValidatorImpl uuidValidatorImpl;
    private final ClientFormatter clientFormatter;
    private static final String nameRegex = "[a-zA-Z\\s]+";

    public ClientValidatorImpl(UUIDValidatorImpl uuidValidatorImpl, ClientFormatter clientFormatter) {
        this.uuidValidatorImpl = uuidValidatorImpl;
        this.clientFormatter = clientFormatter;
    }

    /**
     * The method that processes {@link Client}. In case of an error in one or more fields - throws an error {@link ValidationException}.
     * @param client Entity to check
     * @return true if entity successfully passed complete validation, else - throws {@link ValidationException}.
     */
    public Client validate(Client client) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (client.getId() != null) {
            if (!uuidValidatorImpl.validate(client.getId().toString()).isEmpty()) {
                noError = false;
            }
        }

        String email = client.getEmail();
        if (email == null) {
            errorMessage.append("email must be not be empty|");
            noError = false;
        } else if (!email.matches("^[A-Za-z0-9_.-]+@[a-z0-9-]+(\\.[a-z]{2,6})$")) {
            errorMessage.append("email should be correct value|");
            noError = false;
        }

        String fName = client.getFirstName();
        if (fName == null) {
            errorMessage.append("first name must be not be empty|");
            noError = false;
        } else if (!fName.matches(nameRegex)) {
            errorMessage.append("first name cannot contain numbers|");
            noError = false;
        }

        String phoneNum = client.getPhoneNumber();
        if (phoneNum == null) {
            errorMessage.append("phone number filed must be not be empty|");
            noError = false;
        } else if (!phoneNum.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) {
            errorMessage.append("phone number must be valid|");
            noError = false;
        }

        String sName = client.getSecondName();
        if (sName == null) {
            errorMessage.append("second name must be not be empty|");
            noError = false;
        } else if (!sName.matches(nameRegex)) {
            errorMessage.append("second name cannot contain numbers|");
            noError = false;
        }

        String passportId = client.getPassportID();
        if (passportId == null) {
            errorMessage.append("passport id must be not be empty|");
            noError = false;
        } else if (!passportId.matches("\\d{2}[\\- ]?\\d{2}[\\- ]?\\d{6}")) {
            errorMessage.append("passport id must be valid|");
            noError = false;
        }
        if (!noError) {
            throw new ValidationException(errorMessage.toString());
        }

        client.setPhoneNumber(clientFormatter.formatPhoneNumber(client.getPhoneNumber()));
        client.setPassportID(clientFormatter.formatPassportId(client.getPassportID()));

        return client;
    }
}
