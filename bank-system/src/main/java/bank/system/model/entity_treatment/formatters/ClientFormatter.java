package bank.system.model.entity_treatment.formatters;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
