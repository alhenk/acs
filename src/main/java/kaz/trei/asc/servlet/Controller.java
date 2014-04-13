package kaz.trei.asc.servlet;

import kaz.trei.asc.action.Action;
import kaz.trei.asc.action.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    ActionFactory actionFactory;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = getActionName(request);
        Action action = actionFactory.create(actionName);
    }

    private String getActionName(HttpServletRequest request) {
        return request.getMethod() + request.getPathInfo();
    }
}
