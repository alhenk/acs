package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;


public class ShowRfidTagList implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowRfidTagList.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        List<RfidTag> rfidtags;
        try {
            rfidtags = rfidTagDao.findAll();
            LOGGER.debug("RFIDTAGS "+ rfidtags);
        } catch (DaoException e) {
            LOGGER.error("Getting rfidtag list exception: " + e.getMessage());
            session.setAttribute("error","error.db.rfidtag-list");
            return new ActionResult(ActionType.FORWARD,"error");
        }
        session.setAttribute("rfidtags", rfidtags);
        return new ActionResult(ActionType.FORWARD,"rfidtag-list");
    }
}
