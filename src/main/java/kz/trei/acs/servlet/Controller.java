package kz.trei.acs.servlet;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionFactory;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    static{
        PropertyManager.load("configure.properties");
    }
    private ActionFactory actionFactory=new ActionFactory();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("Entered service() Method = " + request.getMethod() + " PathURI = " + request.getRequestURI());
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String actionName = getActionName(request);
        Action action = actionFactory.getAction(actionName);
        ActionResult result = action.execute(request, response);
        switch (result.getMethod()){
            case FORWARD:
                LOGGER.debug("FORWARD -> "+result.getView());
                String path = PropertyManager.getValue("jsp.view.path")+result.getView()+".jsp";
                request.getRequestDispatcher(path).forward(request,response);
                break;
            case REDIRECT:
                LOGGER.debug("REDIRECT -> "+result.getView());
                response.sendRedirect(result.getView());
                break;
            default:
                response.sendRedirect("/");
        }
    }

    String getActionName(HttpServletRequest request) {
        String actionName = request.getMethod() + request.getPathInfo();
        if(actionName == null || actionName.isEmpty()) actionName = "GET/main";
        LOGGER.debug("Entered getActionName()  action = " + actionName);
        return actionName;
    }
}
