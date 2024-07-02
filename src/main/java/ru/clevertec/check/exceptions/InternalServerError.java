package ru.clevertec.check.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InternalServerError extends Exception{
    private String errorMessage;
}
