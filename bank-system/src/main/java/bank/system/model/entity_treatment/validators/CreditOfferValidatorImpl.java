package bank.system.model.entity_treatment.validators;

import bank.system.model.domain.CreditOffer;
import bank.system.model.entity_treatment.validators.api.Validator;
import bank.system.rest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreditOfferValidatorImpl implements Validator<CreditOffer> {
    /**
     * The maximum duration for credit offer
     */
    @Value("${bank.credit-offer.duration.max}")
    double maxCreditOfferDuration;
    /**
     * The minimum duration for credit offer
     */
    @Value("${bank.credit-offer.duration.min}")
    double minCreditOfferDuration;
    private final UUIDValidatorImpl uuidValidatorImpl;

    public CreditOfferValidatorImpl(UUIDValidatorImpl uuidValidatorImpl) {
        this.uuidValidatorImpl = uuidValidatorImpl;
    }

    /**
     * Getter that is used to validate property values in {@link bank.system.config.BeanPostProcessorImpl}
     */
    public double getMaxCreditOfferDuration() {
        return maxCreditOfferDuration;
    }

    /**
     * Getter that is used to validate property values in {@link bank.system.config.BeanPostProcessorImpl}
     */
    public double getMinCreditOfferDuration() {
        return minCreditOfferDuration;
    }

    /**
     * The method that processes {@link CreditOffer}. In case of an error in one or more fields - throws an error {@link ValidationException}.
     * @param creditOffer Entity to check
     * @return true if entity successfully passed complete validation, else - throws {@link ValidationException}.
     */
    public CreditOffer validate(CreditOffer creditOffer) {
        StringBuilder errorMessage = new StringBuilder();
        boolean noError = true;

        if (creditOffer.getId() != null) {
            if (!uuidValidatorImpl.validate(creditOffer.getId().toString()).isEmpty()) {
                noError = false;
            }
        }

        if (creditOffer.getCredit() == null) {
            errorMessage.append("credit offer must have credit|");
            noError = false;
        }
        if (creditOffer.getDuration() <= 0) {
            errorMessage.append("credit offer duration must be positive|");
            noError = false;
        }

        if (creditOffer.getDuration() < minCreditOfferDuration) {
            errorMessage.append("credit offer duration must be longer than ")
                    .append(minCreditOfferDuration)
                    .append("|");
            noError = false;
        }
        if (creditOffer.getDuration() > maxCreditOfferDuration) {
            errorMessage.append("credit offer duration must be less than ")
                    .append(maxCreditOfferDuration)
                    .append("|");
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
        }

        if (!noError) {
            throw new ValidationException(errorMessage.toString());
        }
        return creditOffer;
    }
}
