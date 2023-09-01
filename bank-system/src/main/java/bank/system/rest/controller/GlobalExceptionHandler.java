package bank.system.rest.controller;

import bank.system.model.exception.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({EntityNotFoundException.class})
    public String get(Model model, EntityNotFoundException e){
        model.addAttribute("errorTemplate", e.getMessage());
        return "error";
    }
}
