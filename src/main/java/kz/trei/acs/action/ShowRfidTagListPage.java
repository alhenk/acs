package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.RfidComparator;
import kz.trei.acs.office.rfid.RfidTag;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;


public class ShowRfidTagListPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowRfidTagListPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
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
            rfidtags = rfidTagDao.findAll();
            Collections.sort(rfidtags, new RfidComparator(compareType));
//            LOGGER.debug("RFIDTAGS " + rfidtags);
        } catch (DaoException e) {
            LOGGER.error("Getting rfidtag list exception: " + e.getMessage());
            session.setAttribute("error", "error.db.rfidtag-list");
            return new ActionResult(ActionType.FORWARD, "error");
        }
        session.setAttribute("rfidtags", rfidtags);
        return new ActionResult(ActionType.FORWARD, "rfidtag-list");
    }
}
