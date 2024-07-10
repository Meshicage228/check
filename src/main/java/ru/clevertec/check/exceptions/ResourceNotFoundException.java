package ru.clevertec.check.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends AbstractHttpException{
    public ResourceNotFoundException() {
        super(NOT_FOUND);
    }
}
