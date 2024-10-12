package ru.clevertec.check.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractHttpException extends Exception{
    private final int statusCode;
}
