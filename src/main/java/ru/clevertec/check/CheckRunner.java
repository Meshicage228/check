package ru.clevertec.check;

import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.utils.StringInputUtil;

public class CheckRunner {
    public static void main(String[] args) {
        StringInputUtil stringInputUtil = new StringInputUtil();
        InputStringDetails details = stringInputUtil.formStringDetails(args);

        DataBaseConfig.setProperties(details.getUrl(), details.getUsername(), details.getPassword());
        DataBaseConfig.startDb();

        BillApplication billApplication = ServiceFactory.createBillApplication();
        billApplication.start(details);
    }
}
