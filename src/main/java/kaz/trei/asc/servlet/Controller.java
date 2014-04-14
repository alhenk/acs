package kaz.trei.asc.servlet;

import kaz.trei.asc.action.Action;
import kaz.trei.asc.action.ActionFactory;
import kaz.trei.asc.action.ActionResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private ActionFactory actionFactory=new ActionFactory();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = getActionName(request);
        Action action = actionFactory.create(actionName);
        LOGGER.debug("Action Name = "+actionName);
        ActionResult result = action.execute(request, response);
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

    String getActionName(HttpServletRequest request) {
        String actionName = request.getMethod() + request.getPathInfo();
        if(actionName == null || actionName.isEmpty()) actionName = "GET/main";
        return actionName;
    }
}
