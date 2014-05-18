package kz.trei.acs.servlet;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);
    private static final long serialVersionUID = 1L;
    static{
        PropertyManager.load("configure.properties");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("doGet...");
        processError(request, response);
        request.getRequestDispatcher(PropertyManager.getValue("jsp.view.path")+"/error.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("doPost...");
        processError(request, response);
        response.sendRedirect("error");
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        HttpSession session = request.getSession();
        session.setAttribute("status-code", statusCode);
        session.setAttribute("request-uri", requestUri);
        LOGGER.debug("Status Code "+ statusCode);
        LOGGER.debug("Request Uri "+ requestUri);
        return;
    }
}
