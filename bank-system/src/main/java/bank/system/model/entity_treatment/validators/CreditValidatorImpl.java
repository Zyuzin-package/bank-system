package bank.system.model.entity_treatment.validators;

import bank.system.model.domain.Credit;
import bank.system.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreditValidatorImpl {
    private final UUIDValidatorImpl uuidValidatorImpl;

    /**
     * The maximum allowable amount that the bank can issue to the client
     */
    @Value("${bank.credit.max-limit}")
    double maxCreditLimit;

    /**
     * The minimum percentage at which a bank can issue a loan
     */
    @Value("${bank.credit.min-interest-rate}")
    double minInterestRate;

    public CreditValidatorImpl(UUIDValidatorImpl uuidValidatorImpl) {
        this.uuidValidatorImpl = uuidValidatorImpl;
    }

    /**
     * The method that processes {@link Credit}. In case of an error in one or more fields - throws an error {@link ValidationException}.
     * @param credit Entity to check
     * @return true if entity successfully passed complete validation, else - throws {@link ValidationException}.
     */
    public Credit validate(Credit credit) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (credit.getId() != null) {
            if (!uuidValidatorImpl.validate(credit.getId().toString()).isEmpty()) {
                noError = false;
            }
        }

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
        if (!noError) {
            throw new ValidationException(errorMessage.toString());
        }
        return credit;
    }
}
