package ru.clevertec.check.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public abstract class AbstractHttpException extends Exception{
    private final HttpStatus statusCode;
}
