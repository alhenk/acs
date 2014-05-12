package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.RfidComparator;
import kz.trei.acs.office.rfid.RfidTag;
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
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        int length;
        int offset = 0;
        long totalNumber = 0;
        try {
            offset = Integer.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
            offset = 0;
        }
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            LOGGER.error("Length is not valid");
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
        }
        RfidComparator.CompareType compareType;
        try {
            compareType =
                    RfidComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e) {
            compareType = RfidComparator.CompareType.ID;
        } catch (IllegalArgumentException e) {
            compareType = RfidComparator.CompareType.ID;
        }
        List<RfidTag> rfidtags=null;
        try {
            rfidtags = rfidTagDao.findInRange(offset, length);;
            Collections.sort(rfidtags, new RfidComparator(compareType));
            totalNumber = rfidTagDao.totalNumber();
        } catch (DaoException e) {
            LOGGER.error("Getting rfidtag list exception: " + e.getMessage());
            session.setAttribute("error", "error.db.rfidtag-list");
            return new ActionResult(ActionType.FORWARD, "error");
        }
        session.setAttribute("rfidtags", rfidtags);
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        return new ActionResult(ActionType.FORWARD, "rfidtag-list");
    }
}
