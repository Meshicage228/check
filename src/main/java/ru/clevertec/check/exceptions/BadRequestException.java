package ru.clevertec.check.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends AbstractHttpException {
    public BadRequestException() {
        super(BAD_REQUEST);
    }
}
