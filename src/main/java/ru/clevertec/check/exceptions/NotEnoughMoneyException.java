package ru.clevertec.check.exceptions;

import ru.clevertec.check.utils.ExceptionMessages;

public class NotEnoughMoneyException extends AbstractErrorFileWriter {

    public NotEnoughMoneyException() {
        createErrorFile(getErrorMessage());
    }

    @Override
    public String getErrorMessage() {
        return ExceptionMessages.NOT_ENOUGH_MONEY;
    }

    @Override
    public void createErrorFile(String errorMessage) {
        super.createErrorFile(errorMessage);
    }
}
