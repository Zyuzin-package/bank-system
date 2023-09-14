package bank.system.model.entity_treatment.formatters;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * The class is responsible for transforming the fields of the client’s phone number and ID of the client’s passport,
 * for convenient saving in the database
 */
@Component
public class ClientFormatter {

    public String formatPhoneNumber(String phoneNum) {
        return phoneNum.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

    public String formatPassportId(String passId) {
        return passId.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

}
