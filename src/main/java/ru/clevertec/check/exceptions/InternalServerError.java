package ru.clevertec.check.exceptions;

import ru.clevertec.check.utils.ExceptionMessages;

public class InternalServerError extends AbstractErrorFileWriter {

    public InternalServerError() {
        createErrorFile(getErrorMessage());
    }

    @Override
    public String getErrorMessage() {
        return ExceptionMessages.INTERNAL_SERVER_ERROR;
    }

    @Override
    public void createErrorFile(String errorMessage) {
        super.createErrorFile(errorMessage);
    }
}
