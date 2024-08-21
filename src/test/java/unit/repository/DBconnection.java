package unit.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@ExtendWith(MockitoExtension.class)
public class DBconnection {
    @Mock
    public Connection connection;

    @Mock
    public PreparedStatement preparedStatement;

    @Mock
    public ResultSet resultSet;

    @BeforeAll
    public static void setup(){
        Mockito.mockStatic(DataBaseConfig.class);
    }

    @AfterAll
    public static void tearDown() {
        Mockito.framework().clearInlineMocks();
    }
}
