package ru.clevertec.check.exceptions;

import ru.clevertec.check.utils.ExceptionMessages;

public class BadRequestException extends AbstractErrorFileWriter{

    public BadRequestException() {
        createErrorFile(getErrorMessage());
    }

    public BadRequestException(String errorFilePath) {
        createErrorFile(getErrorMessage(), errorFilePath);
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
