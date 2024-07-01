package ru.clevertec.check;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.service.StringInputService;

@AllArgsConstructor
public class BillApplication {
    private StringInputService inputService;
    private ClientService clientService;

    public void start(String[] args){
        if(inputService.isValid(args)){
            InputStringDetails inputStringDetails = inputService.formStringDetails(args);
            CurrentClient currentClient = clientService.formClient(inputStringDetails);
            System.out.println(currentClient);
        } else {
            System.out.println("bad request");
        }

    }
}
