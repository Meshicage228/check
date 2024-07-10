package ru.clevertec.check.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class AppExceptionHandling {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> getBadRequest(BadRequestException e){
        return ResponseEntity.status(e.getStatusCode()).build();
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<Void> getNotEnoughMoney(NotEnoughMoneyException e){
        return ResponseEntity.status(e.getStatusCode()).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> getResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(e.getStatusCode()).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> getResourceNotFound(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }
}
