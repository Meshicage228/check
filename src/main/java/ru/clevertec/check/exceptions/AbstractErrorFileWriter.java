package ru.clevertec.check.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.FileWriter;
import java.util.StringJoiner;

@Getter
@AllArgsConstructor
public abstract class AbstractErrorFileWriter extends RuntimeException{
    public abstract String getErrorMessage();

    private final String DEFAULT_ERROR_FILE_PATH = "result.csv";

    public void createErrorFile(String errorMessage) {
        createErrorFile(errorMessage, DEFAULT_ERROR_FILE_PATH);
    }

    public void createErrorFile(String errorMessage, String errorFilePath) {
        try (FileWriter fileWriter = new FileWriter(errorFilePath)) {
            StringJoiner stringJoiner = new StringJoiner("\n");
            stringJoiner.add("ERROR").add(errorMessage);
            fileWriter.write(stringJoiner.toString());
            System.out.println("Was formed file with error!");
        } catch (Exception ex) {
            System.out.println("Exception while creating Error file: " + ex.getMessage());
        }
    }
}
