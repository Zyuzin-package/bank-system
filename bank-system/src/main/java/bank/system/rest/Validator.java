package bank.system.rest;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.exception.ServerException;
import bank.system.rest.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Entity validator class. If the field fails validation, adds an error notice to the string.
 * After checking all fields, if there is a validation error, it throws {@link ValidationException}
 * Which is caught by the {@link bank.system.rest.controller.GlobalExceptionHandler} and displays an error page passing a error message to it
 */
@Component
public class Validator {







}
