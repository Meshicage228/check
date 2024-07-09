package ru.clevertec.check.exceptions;

import javax.servlet.http.HttpServletResponse;

public class BadRequestException extends AbstractHttpException {
    public BadRequestException() {
        super(HttpServletResponse.SC_BAD_REQUEST);
    }
}
