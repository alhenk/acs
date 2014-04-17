package kaz.trei.acs.servlet;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest extends TestCase {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        controller = new Controller();
    }

    @Test
    public void testGetActionName() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/main");
        assertEquals(controller.getActionName(request), "GET/main");

        when(request.getMethod()).thenReturn("");
        when(request.getPathInfo()).thenReturn("");
        assertEquals(controller.getActionName(request), "GET/main");
    }
}
