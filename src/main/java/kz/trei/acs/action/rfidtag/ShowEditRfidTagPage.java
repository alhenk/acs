package kz.trei.acs.action.rfidtag;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidType;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowEditRfidTagPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
            if (id < 0) {
                throw new NumberFormatException("negative id = " + id);
            }
        } catch (NumberFormatException e) {
            LOGGER.error("GET parameter \"id\" is not valid : " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?status=error.parameter.id.invalid");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        RfidTag originalRfidTag;
        try {
            originalRfidTag = rfidTagDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD, "error");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD, "error");
        }
        HttpSession session = request.getSession();
        List<String> protocols = ProtocolType.getList();
        List<String> types = RfidType.getList();
        session.setAttribute("protocols", protocols);
        session.setAttribute("types", types);
        session.setAttribute("original-rfidtag", originalRfidTag);
        LOGGER.debug("... rfidtag = " + originalRfidTag);
        return new ActionResult(ActionType.FORWARD, "edit-rfidtag");
    }
}
