package ru.clevertec.check.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class NotEnoughMoneyException extends AbstractHttpException {
    public NotEnoughMoneyException() {
        super(BAD_REQUEST);
    }
}
