package kaz.trei.asc.servlet;

import junit.framework.TestCase;

public class ControllerTest extends TestCase {
    public void testGetActionName() throws Exception {
        String expected = "Hello, JUnit!";
        String hello = "Hello, JUnit!";
        assertEquals(hello, expected);
    }
}
