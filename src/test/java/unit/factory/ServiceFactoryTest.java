package unit.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.factory.ServiceFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Factory tests")
class ServiceFactoryTest {
    @Test
    public void testFactoryMethods() {
        assertNotNull(ServiceFactory.createProductRepository());
        assertNotNull(ServiceFactory.createCardRepository());
        assertNotNull(ServiceFactory.createProductService());
        assertNotNull(ServiceFactory.createCardService());
        assertNotNull(ServiceFactory.createFileService());
        assertNotNull(ServiceFactory.createClientService());
    }
}