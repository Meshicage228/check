package ru.clevertec.check.exceptions.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> getEntityNotFound(EntityNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> getAllExceptions(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }
}
