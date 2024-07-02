package ru.clevertec.check;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.InternalServerError;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.service.BillService;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.service.StringInputService;
import ru.clevertec.check.utils.ExceptionMessages;
import ru.clevertec.check.utils.FileService;

@AllArgsConstructor
public class BillApplication {
    private StringInputService inputService;
    private ClientService clientService;
    private BillService billService;
    private FileService fileService;

    public void start(String[] args) {
        if (!inputService.isValid(args)) {
            fileService.createErrorFile(ExceptionMessages.BAD_REQUEST);
            return;
        }

        try {
            InputStringDetails inputStringDetails = inputService.formStringDetails(args);
            CurrentClient currentClient = clientService.formClient(inputStringDetails);
            billService.formTotalBill(currentClient);
        } catch (NotEnoughMoneyException e) {
            fileService.createErrorFile(e.getErrorMessage());
        } catch (InternalServerError e) {
            fileService.createErrorFile(e.getErrorMessage());
        }
    }
}
