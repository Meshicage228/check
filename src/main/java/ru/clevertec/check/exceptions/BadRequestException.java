package ru.clevertec.check.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadRequestException extends Exception{
    private String errorMessage;
}
