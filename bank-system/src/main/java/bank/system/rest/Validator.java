package bank.system.rest;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Value("${bank.credit.max-limit}")
    double maxCreditLimit;

    @Value("${bank.credit.min-interest-rate}")
    double minInterestRate;

    public boolean clientValidation(Client client) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        String email = client.getEmail();
        if (email == null) {
            errorMessage.append("email must be not be empty|");
        } else if (!email.matches("^[A-Za-z0-9_.-]+@[a-z0-9-]+(\\.[a-z]{2,6})$")) {
            errorMessage.append("email should be correct value|");
        }

        String fName = client.getFirstName();
        if (fName == null) {
            errorMessage.append("first name must be not be empty|");
        } else if (!fName.matches("[a-zA-Z\\s]+")) {
            errorMessage.append("first name cannot contain numbers|");
        }

        String phoneNum = client.getPhoneNumber();
        if (phoneNum == null) {
            errorMessage.append("phone number filed must be not be empty|");
        } else if (!phoneNum.matches("8-9\\d{2}-\\d{3}-\\d{2}-\\d{2}")) {
            errorMessage.append("phone number must match the pattern: 8-9**-***-**-**|");
        }

        String sName = client.getSecondName();
        if (sName == null) {
            errorMessage.append("second name must be not be empty|");
        } else if (!sName.matches("[a-zA-Z\\s]+")) {
            errorMessage.append("second name cannot contain numbers|");
            noError = false;
        }

        String passportId = client.getPassportID();
        if (passportId == null) {
            errorMessage.append("passport id must be not be empty|");
            noError = false;
        } else if (!passportId.matches("\\d{2}-\\d{2}-\\d{6}")) {
            errorMessage.append("passport id must match the pattern: **-**-******|");
            noError = false;
        }
        if(noError == false){
            throw new ValidationException(errorMessage.toString());
        }
        return true;
    }

    public boolean creditValidation(Credit credit) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (credit.getLimit() <= 0) {
            errorMessage.append("credit limit must be positive|");
            noError = false;
        } else {
            if (credit.getLimit() > maxCreditLimit) {
                errorMessage.append("credit limit must be less ").append(maxCreditLimit).append("|");
                noError = false;
            }
        }

        if (credit.getInterestRate() <= 0) {
            errorMessage.append("interest rate must be positive|");
            noError = false;
        } else {
            if (credit.getInterestRate() < minInterestRate) {
                errorMessage.append("interest rate must be over ").append(minInterestRate).append("|");
                noError = false;
            }
        }
        if(noError == false){
            throw new ValidationException(errorMessage.toString());
        }
        return true;
    }

    public boolean creditOfferValidation(CreditOffer creditOffer) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (creditOffer.getCredit() == null) {
            errorMessage.append("credit offer must have credit|");
            noError = false;
        } else {
            if (!creditValidation(creditOffer.getCredit())) {
                noError = false;
            }
        }

        if (creditOffer.getDuration() <= 0) {
            errorMessage.append("credit offer duration must be positive|");
            noError = false;
        }
        if (creditOffer.getPaymentSum() <= 0) {
            errorMessage.append("payment sum must be not be positive|");
            noError = false;
        } else {
            if (creditOffer.getPaymentSum() > creditOffer.getCredit().getLimit()) {
                errorMessage.append("payment sum must be less than credit limit|");
                noError = false;
            }
        }
        if (creditOffer.getClient() == null) {
            errorMessage.append("credit offer must have client|");
            noError = false;
        } else {
            if (!clientValidation(creditOffer.getClient())) {
                noError = false;
            }
        }

        if(noError == false){
            throw new ValidationException(errorMessage.toString());
        }
        return true;
    }

    public boolean uuidValidator(String uuid) {
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
        if(noError == false){
            throw new ValidationException(errorMessage.toString());
        }
        return true;
    }
}
