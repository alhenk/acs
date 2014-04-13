package kaz.trei.asc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = getActionName(request);
    }

    private String getActionName(HttpServletRequest request) {
        return request.getMethod()+request.getPathInfo();
    }
}
