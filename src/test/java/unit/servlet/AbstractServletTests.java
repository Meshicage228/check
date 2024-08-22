package unit.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class AbstractServletTests {
    @Mock
    public ObjectMapper objectMapper;

    @Mock
    public PrintWriter writer;

    @Mock
    public InputDataValidator validator;

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

}
