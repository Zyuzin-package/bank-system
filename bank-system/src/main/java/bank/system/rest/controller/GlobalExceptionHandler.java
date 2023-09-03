package bank.system.rest.controller;

import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.exception.ValidationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Класс, который отвечает за отлов ошибок: кастомных и остальных, и редиректит на странцу с ошибкой передавая информацию об ошибке
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({EntityNotFoundException.class, ValidationException.class})
    public String customException(Model model, Exception e){
        List<String> errors = List.of(e.getMessage().split("\\|"));
        model.addAttribute("errorTemplate", errors);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String defaultException(Model model){
        model.addAttribute("errorTemplate","Server error");
        return "error";
    }

}
