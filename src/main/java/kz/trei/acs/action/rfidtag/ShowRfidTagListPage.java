package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidTagComparator;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;


public class ShowRfidTagListPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowRfidTagListPage.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        long totalNumber;
        long offset = takeOffsetFromRequest(request);
        long length = takeLengthFromRequest(request);
        RfidTagComparator.CompareType compareType =
                takeRfidTagComparatorFromRequest(request);
        List<RfidTag> rfidtags;
        try {
            rfidtags = rfidTagDao.findInRange(offset, length);
            Collections.sort(rfidtags, new RfidTagComparator(compareType));
            totalNumber = rfidTagDao.totalNumber();
        } catch (DaoException e) {
            killRfidTagListAttribute(request);
            LOGGER.error("Getting rfidtag list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.rfidtag-list");
        } catch (RuntimeException e) {
            killRfidTagListAttribute(request);
            LOGGER.error("Getting rfidtag list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.rfidtag-list");
        }
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        session.setAttribute("rfidtags", rfidtags);
        LOGGER.debug("..." + rfidtags);
        return new ActionResult(ActionType.FORWARD, "rfidtag-list");
    }

    private void killRfidTagListAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("total-number");
        session.removeAttribute("offset");
        session.removeAttribute("length");
        session.removeAttribute("rfidtags");
    }

    private RfidTagComparator.CompareType takeRfidTagComparatorFromRequest(HttpServletRequest request) {
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

    private long takeLengthFromRequest(HttpServletRequest request) {
        long length;
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
            LOGGER.error("GET parameter length is empty, assigned configure value (" + length + ")");
        }
        return length;
    }

    private long takeOffsetFromRequest(HttpServletRequest request) {
        long offset;
        try {
            offset = Long.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
            offset = 0;
        }
        return offset;
    }
}
