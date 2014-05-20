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
        LOGGER.debug("execute ... ");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        long numTuples;
        long page = RfidTagUtil.takePage(request);
        long limit = RfidTagUtil.takeLimit(request);
        long offset;
        long numPages;
        RfidTagComparator.CompareType compareType =
                RfidTagUtil.takeRfidTagComparator(request);
        List<RfidTag> rfidtags;
        try {
            numTuples = rfidTagDao.numberOfTuples();
            numPages = (long) (Math.ceil((1.0 * numTuples) / limit));
            offset = (page - 1) < 0 || page > numPages ? 0 : (page - 1) * limit;
            rfidtags = rfidTagDao.findInRange(offset, limit);
            Collections.sort(rfidtags, new RfidTagComparator(compareType));
        } catch (DaoException e) {
            killRfidTagListAttribute(request);
            request.setAttribute("error", "error.db.rfidtag-list");
            LOGGER.error("... getting rfidtag list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + RfidTagUtil.fetchParameters(request));
        } catch (RuntimeException e) {
            killRfidTagListAttribute(request);
            request.setAttribute("error", "error.db.rfidtag-list");
            LOGGER.error("... getting rfidtag list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + RfidTagUtil.fetchParameters(request));
        }
        session.setAttribute("rfidtags", rfidtags);
        session.setAttribute("num-pages", numPages);
        session.setAttribute("page", page);
        LOGGER.debug("..." + rfidtags);
        return new ActionResult(ActionType.FORWARD, "rfidtag-list");
    }

    private void killRfidTagListAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("offset");
        session.removeAttribute("length");
        session.removeAttribute("total-number");
        session.removeAttribute("rfidtags");
    }
}
