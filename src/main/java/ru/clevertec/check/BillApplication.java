package ru.clevertec.check;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.service.BillService;
import ru.clevertec.check.service.ClientService;

@AllArgsConstructor
public class BillApplication {
    private ClientService clientService;
    private BillService billService;

    public void start(InputStringDetails inputStringDetails) {
        CurrentClient currentClient = clientService.formClient(inputStringDetails);
        billService.formTotalBill(currentClient);
    }
}
