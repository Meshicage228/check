package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServletTests {
    @Mock
    public ObjectMapper objectMapper;

    @Mock
    public InputDataValidator validator;

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

}
