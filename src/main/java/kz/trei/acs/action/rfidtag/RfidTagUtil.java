package kz.trei.acs.action.rfidtag;

import kz.trei.acs.office.rfid.*;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.exception.DateStampException;
import kz.trei.acs.exception.GetParameterException;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class RfidTagUtil {
    private static final Logger LOGGER = Logger.getLogger(RfidTagUtil.class);

    private RfidTagUtil() {
    }

    public static RfidTag buildNewRfidTagFromRequest(HttpServletRequest request) {
        LOGGER.debug("buildNewRfidTagFromRequest ...");
        String uid = request.getParameter("uid");
        RfidType type = takeTypeFromRequest(request);
        ProtocolType protocol = takeProtocolFromRequest(request);
        Issue issue = buildIssueFromRequest(request);
        RfidTag rfidTag = new RfidTag.Builder()
                .uid(uid)
                .type(type)
                .protocol(protocol)
                .issue(issue)
                .build();
        LOGGER.debug("RFID tag " + rfidTag + " is built");
        return rfidTag;
    }

    public static RfidTag buildEditedRfidTagFromRequest(HttpServletRequest request) {
        LOGGER.debug("buildEditedRfidTagFromRequest ...");
        long id = takeIdFromRequest(request);
        String uid = request.getParameter("uid");
        RfidType type = takeTypeFromRequest(request);
        ProtocolType protocol = takeProtocolFromRequest(request);
        Issue issue = buildIssueFromRequest(request);
        RfidTag rfidTag = new RfidTag.Builder()
                .id(id)
                .uid(uid)
                .type(type)
                .protocol(protocol)
                .issue(issue)
                .build();
        LOGGER.debug("RFID tag " + rfidTag + " is built");
        return rfidTag;
    }

    private static Issue buildIssueFromRequest(HttpServletRequest request) {
        LOGGER.debug("buildIssueFromRequest ...");
        DateStamp issueDate = takeIssueDateFromRequest(request);
        DateStamp expirationDate = takeExpirationDateFromRequest(request);
        Issue issue = new Issue.Builder()
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .build();
        LOGGER.debug("Issue " + issue + " is built");
        return issue;
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

    public static void createFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("createFieldAttributes ...");
        HttpSession session = request.getSession();
        session.setAttribute("uid", request.getParameter("uid"));
        session.setAttribute("type", request.getParameter("type"));
        session.setAttribute("protocol", request.getParameter("protocol"));
        session.setAttribute("issue-date", request.getParameter("issue-date"));
        session.setAttribute("expiration-date", request.getParameter("expiration-date"));
    }

    public static void killFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("killFieldAttributes ...");
        HttpSession session = request.getSession();
        session.removeAttribute("uid");
        session.removeAttribute("type");
        session.removeAttribute("protocol");
        session.removeAttribute("issue-date");
        session.removeAttribute("expiration-date");
        session.removeAttribute("protocols");
        session.removeAttribute("types");
        session.removeAttribute("original-rfidtag");
    }

    public static long takeIdFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeId ...");
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

    public static RfidType takeTypeFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeTypeFromRequest ...");
        RfidType type;
        try {
            type = RfidType.valueOf(request.getParameter("type"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID type due to illegal argument : " + e.getMessage());
            type = RfidType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID type due to null value : " + e.getMessage());
            type = RfidType.DEFAULT;
        }
        LOGGER.debug(type);
        return type;
    }

    public static ProtocolType takeProtocolFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeProtocolFromRequest ...");
        ProtocolType protocol;
        try {
            protocol = ProtocolType.valueOf(request.getParameter("protocol"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default RFID protocol due to illegal argument : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default RFID protocol due to null value : " + e.getMessage());
            protocol = ProtocolType.DEFAULT;
        }
        LOGGER.debug(protocol);
        return protocol;
    }

    public static DateStamp takeIssueDateFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeIssueDateFromRequest ...");
        DateStamp issueDate;
        try {
            issueDate = DateStamp.create(request.getParameter("issue-date"));
        } catch (DateStampException e) {
            issueDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty issue date due to exception: " + e.getMessage());
        }
        LOGGER.debug(issueDate.getDate());
        return issueDate;
    }

    public static DateStamp takeExpirationDateFromRequest(HttpServletRequest request) {
        LOGGER.debug("takeExpirationDateFromRequest ...");
        DateStamp expirationDate;
        try {
            expirationDate = DateStamp.create(request.getParameter("expiration-date"));
        } catch (DateStampException e) {
            expirationDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty expiration date due to exception: " + e.getMessage());
        }
        LOGGER.debug(expirationDate.getDate());
        return expirationDate;
    }

    public static long takePage(HttpServletRequest request) {
        long page;
        try {
            page = Integer.valueOf(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1L;
            LOGGER.error("GET parameter \"page\" is empty, assigned default value 1");
        }
        return page;
    }

    public static long takeLimit(HttpServletRequest request) {
        long limit;
        try {
            limit = Integer.valueOf(request.getParameter("limit"));
        } catch (NumberFormatException e) {
            limit = Integer.valueOf(PropertyManager.getValue("paging.limit"));
            LOGGER.error("GET parameter \"limit\" is empty, assigned value (" + limit + ")");
        }
        return limit;
    }

    public static RfidTagComparator.CompareType takeRfidTagComparator(HttpServletRequest request) {
        RfidTagComparator.CompareType rfidTagComparator;
        try {
            rfidTagComparator =
                    RfidTagComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e) {
            rfidTagComparator = RfidTagComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        } catch (IllegalArgumentException e) {
            rfidTagComparator = RfidTagComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        }
        return rfidTagComparator;
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
            LOGGER.debug("the field is optional - true");
            return true;
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
            LOGGER.debug("the field is optional - true");
            return true;
        }
        if (!DateStamp.isDateStampValid(expirationDate)) {
            request.setAttribute("expiration-date-error", "form.rfidtag.expiration-date.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //RFID TAG VALIDATION
    public static boolean isTypeValid(HttpServletRequest request) {
        LOGGER.debug("isTypeValid ...");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            request.setAttribute("type-error", "form.rfidtag.empty");
            LOGGER.debug("false");
            return false;
        }
        RfidType rfidType = RfidType.valueOf(type);
        if (rfidType == null) {
            request.setAttribute("type-error", "form.rfidtag.type.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //RFID PROTOCOL VALIDATION
    public static boolean isProtocolValid(HttpServletRequest request) {
        LOGGER.debug("isProtocolValid ...");
        String protocol = request.getParameter("protocol");
        if (protocol == null || protocol.isEmpty()) {
            request.setAttribute("protocol-error", "form.rfidtag.empty");
            LOGGER.debug("false");
            return false;
        }
        ProtocolType protocolType = ProtocolType.valueOf(protocol);
        if (protocolType == null) {
            request.setAttribute("protocol-error", "form.rfidtag.protocol.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }
}