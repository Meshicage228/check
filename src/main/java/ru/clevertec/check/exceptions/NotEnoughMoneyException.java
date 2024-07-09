package ru.clevertec.check.exceptions;

import javax.servlet.http.HttpServletResponse;

public class NotEnoughMoneyException extends AbstractHttpException {
    public NotEnoughMoneyException() {
        super(HttpServletResponse.SC_BAD_REQUEST);
    }
}
