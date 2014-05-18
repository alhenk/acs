package kz.trei.acs.action.rfidtag;

import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.util.DateStamp;
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
            parameters.append("&id=").append(id);
        }
        if (uidError != null) {
            parameters.append("&uid-error=").append(uidError);
        }
        if (typeError != null) {
            parameters.append("&type-error=").append(typeError);
        }
        if (issueDateError != null) {
            parameters.append("&issue-date-error=").append(issueDateError);
        }
        if (expirationDateError != null) {
            parameters.append("&expiration-date-error=").append(expirationDateError);
        }
        if (uidError != null) {
            parameters.append("&uid-error=").append(uidError);
        }
        if (status != null) {
            parameters.append("&status=").append(status);
        }
        if (error != null) {
            parameters.append("&error=").append(error);
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
        LOGGER.debug("killFieldAttributes ...");
        HttpSession session = request.getSession();
        session.removeAttribute("protocols");
        session.removeAttribute("types");
        session.removeAttribute("original-rfidtag");
    }

    //UID VALIDATION
    public static boolean isUidValid(HttpServletRequest request) {
        LOGGER.debug("isUidValid ...");
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            request.setAttribute("uid-error", "form.rfidtag.empty");
            LOGGER.debug("false");
            return false;
        }
        if (!RfidTag.isUidValid(uid)) {
            request.setAttribute("uid-error", "form.rfidtag.uid.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //ISSUE DATE VALIDATION
    public static boolean isIssueDateValid(HttpServletRequest request) {
        LOGGER.debug("isIssueDateValid ...");
        String issueDate = request.getParameter("issue-date");
        if (issueDate == null || issueDate.isEmpty()) {
            request.setAttribute("issue-date-error", "form.rfidtag.empty");
            LOGGER.debug("false");
            return false;
        }
        if (!DateStamp.isDateStampValid(issueDate)) {
            request.setAttribute("issue-date-error", "form.rfidtag.issue-date.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //EXPIRATION DATE VALIDATION
    public static boolean isExpirationDateValid(HttpServletRequest request) {
        LOGGER.debug("isExpirationDateValid ...");
        String expirationDate = request.getParameter("expiration-date");
        if (expirationDate == null || expirationDate.isEmpty()) {
            request.setAttribute("expiration-date-error", "form.rfidtag.empty");
            LOGGER.debug("false");
            return false;
        }
        if (!DateStamp.isDateStampValid(expirationDate)) {
            request.setAttribute("expiration-date-error", "form.rfidtag.expiration-date.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }
}