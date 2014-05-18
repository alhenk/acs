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
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    static {
        PropertyManager.load("configure.properties");
    }

    private ActionFactory actionFactory = new ActionFactory();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("service() ... Method = " + request.getMethod() + "; PathURI = " + request.getRequestURI());
        response.setCharacterEncoding("UTF-8");
        Object test = request.getParameter("test");
        if (test != null) {
            if ( test.equals("servlet-exception")) {
                throw new ServletException("Test Servlet Exception");
            } else if (test.equals("io-exception")) {
                throw new IOException("Test IO Exception");
            } else if (test.equals("404")) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            } else if (test.equals("403")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            } else if (test.equals("401")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        String actionName = getActionName(request);
        Action action = actionFactory.getAction(actionName);
        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        ActionResult result = action.execute(request, response);

        switch (result.getMethod()) {
            case FORWARD:
                String path = PropertyManager.getValue("jsp.view.path") + result.getView() + ".jsp";
                LOGGER.debug("FORWARD -> path =" + path);
                request.getRequestDispatcher(path).forward(request, response);
                break;
            case REDIRECT:
                LOGGER.debug("REDIRECT -> " + result.getView());
                response.sendRedirect(result.getView());
                break;
            default:
                response.sendRedirect("/");
        }
    }

    String getActionName(HttpServletRequest request) {
        LOGGER.debug("getActionName ... ");
        String actionName = request.getMethod() + request.getPathInfo();
        if (actionName == null || actionName.isEmpty()) actionName = "GET/main";
        LOGGER.debug("... action = " + actionName);
        return actionName;
    }
}
