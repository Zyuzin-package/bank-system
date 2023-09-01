package bank.system.rest;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public String clientValidation(Client client) {
        boolean noError = true;
        StringBuilder errorMessage = new StringBuilder();
        if (client.getEmail() == null) {
            errorMessage.append("email filed must be not be empty\n");
            noError = false;
        }
        if (client.getFirstName() == null) {
            errorMessage.append("first name filed must be not be empty\n");
            noError = false;
        }
        if (client.getPhoneNumber() == null) {
            errorMessage.append("phone number filed must be not be empty\n");
            noError = false;
        }
        if (client.getSecondName() == null) {
            errorMessage.append("second name filed must be not be empty\n");
            noError = false;
        }
        if (client.getPassportID() == null) {
            errorMessage.append("passport id filed must be not be empty\n");
            noError = false;
        }

        return noError ? errorMessage.toString() : null;
    }

    public String creditValidation(Credit credit) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (credit.getLimit() <= 0) {
            errorMessage.append("credit limit must be not be positive\n");
            noError = false;
        }
        if (credit.getInterestRate() <= 0) {
            errorMessage.append("interest rate must be not be positive\n");
            noError = false;
        }
        return noError ? errorMessage.toString() : null;
    }

    public String creditOfferValidation(CreditOffer creditOffer) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (creditOffer.getCredit() == null) {
            errorMessage.append("Credit offer must have credit\n");
            noError = false;
        }
        if (creditOffer.getDuration() <= 0) {
            errorMessage.append("interest rate must be not be positive\n");
            noError = false;
        }
        if (creditOffer.getPaymentSum() <= 0) {
            errorMessage.append("interest rate must be not be positive\n");
            noError = false;
        }
        if (creditOffer.getClient() == null) {
            errorMessage.append("Credit offer must have client\n");
            noError = false;
        }

        return noError ? errorMessage.toString() : null;
    }
}
