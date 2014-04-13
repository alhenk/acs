package kaz.trei.asc.servlet;

import kaz.trei.asc.action.Action;
import kaz.trei.asc.action.ActionFactory;
import kaz.trei.asc.action.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private ActionFactory actionFactory=new ActionFactory();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = getActionName(request);
        System.out.println(actionName);
        Action action = actionFactory.create(actionName);
        ActionResult result = action.execute(request, response);
        System.out.println(result.getMethod()+"   "+result.getPath());
        switch (result.getMethod()){
            case FORWARD:
                request.getRequestDispatcher(result.getPath()).forward(request,response);
                break;
            case REDIRECT:
                response.sendRedirect(result.getPath());
                break;
            default:
                response.sendRedirect("/");
        }
    }

    private String getActionName(HttpServletRequest request) {
        return request.getMethod() + request.getPathInfo();
    }
}
