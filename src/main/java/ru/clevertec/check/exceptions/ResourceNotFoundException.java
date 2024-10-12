package ru.clevertec.check.exceptions;

import javax.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends AbstractHttpException{
    public ResourceNotFoundException() {
        super(HttpServletResponse.SC_NOT_FOUND);
    }
}
