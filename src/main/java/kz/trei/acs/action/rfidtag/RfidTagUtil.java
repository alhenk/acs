package kz.trei.acs.action.rfidtag;

import kz.trei.acs.util.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class RfidTagUtil {
    private static final Logger LOGGER = Logger.getLogger(RfidTagUtil.class);

    private RfidTagUtil() {
    }

    public static String fetchParameters(HttpServletRequest request) {
        LOGGER.debug("fetchParameters ...");
        StringBuilder parameters = new StringBuilder("?");
        String id = request.getParameter("id");
        String uidError = (String) request.getAttribute("uid-error");
        String typeError = (String) request.getAttribute("type-error");
        String issueDateError = (String) request.getAttribute("issue-date-error");
        String expirationDateError = (String) request.getAttribute("expiration-date-error");
        String status = (String) request.getAttribute("status");
        String error = (String) request.getAttribute("error");
        if (id != null) {
            parameters.append("&id=" + id);
        }
        if (uidError != null) {
            parameters.append("&uid-error=" + uidError);
        }
        if (typeError != null) {
            parameters.append("&type-error=" + typeError);
        }
        if (issueDateError != null) {
            parameters.append("&issue-date-error=" + issueDateError);
        }
        if (expirationDateError != null) {
            parameters.append("&expiration-date-error=" + expirationDateError);
        }

        if (uidError != null) {
            parameters.append("&uid-error=" + uidError);
        }
        if (status != null) {
            parameters.append("&status=" + status);
        }
        if (error != null) {
            parameters.append("&error=" + error);
        }
        LOGGER.debug("Get parameters are fetched");
        return parameters.toString();
    }

    public static long takeIdFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeIdFromRequest ...");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
            if (id < 0) {
                throw new NumberFormatException("negative id = " + id);
            }
        } catch (NumberFormatException e) {
            throw new GetParameterException("GET parameter \"id\" is not valid : " + e.getMessage());
        }
        LOGGER.debug(id);
        return id;
    }

    public static void killFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("protocols");
        session.removeAttribute("types");
        session.removeAttribute("original-rfidtag");
    }
}