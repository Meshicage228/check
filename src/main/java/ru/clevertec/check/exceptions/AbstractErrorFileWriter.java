package ru.clevertec.check.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.FileWriter;
import java.util.StringJoiner;

@Getter
@AllArgsConstructor
public abstract class AbstractErrorFileWriter extends RuntimeException{
    public abstract String getErrorMessage();

    public void createErrorFile(String errorMessage){
        try (FileWriter fileWriter = new FileWriter("src/result.csv")) {
            StringJoiner stringJoiner = new StringJoiner("\n");
            stringJoiner.add("ERROR").add(errorMessage);
            fileWriter.write(stringJoiner.toString());
            System.out.println("Was formed file with error!");
        } catch (Exception ex) {
            System.out.println("Exception while creating Error file : " + ex.getMessage());
        }
    }
}
