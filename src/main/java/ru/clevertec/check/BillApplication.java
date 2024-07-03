package ru.clevertec.check;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.impl.BillServiceImpl;
import ru.clevertec.check.service.impl.ClientServiceImpl;
import ru.clevertec.check.utils.StringInputUtil;

@AllArgsConstructor
public class BillApplication {
    private StringInputUtil inputService;
    private ClientServiceImpl clientService;
    private BillServiceImpl billService;

    public void start(String[] args) {
        if (!inputService.isValid(args)) {
            throw new BadRequestException();
        }
        InputStringDetails inputStringDetails = inputService.formStringDetails(args);
        CurrentClient currentClient = clientService.formClient(inputStringDetails);
        billService.formTotalBill(currentClient);
    }
}
