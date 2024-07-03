package ru.clevertec.check.exceptions;

import ru.clevertec.check.utils.ExceptionMessages;

public class BadRequestException extends AbstractErrorFileWriter{

    public BadRequestException() {
        createErrorFile(getErrorMessage());
    }

    @Override
    public String getErrorMessage() {
        return ExceptionMessages.BAD_REQUEST;
    }

    @Override
    public void createErrorFile(String errorMessage) {
        super.createErrorFile(errorMessage);
    }
}
