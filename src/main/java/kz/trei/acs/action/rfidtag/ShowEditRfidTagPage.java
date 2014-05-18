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
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowEditRfidTagPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        RfidTag originalRfidTag;
        try {
            id = RfidTagUtil.takeIdFromRequest(request);
            originalRfidTag = rfidTagDao.findById(id);
        } catch (DaoException e) {
            RfidTagUtil.killFieldAttributes(request);
            request.setAttribute("error", "error.db.find-by-id");
            LOGGER.error("DAO find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + RfidTagUtil.fetchParameters(request));
        } catch (GetParameterException e) {
            RfidTagUtil.killFieldAttributes(request);
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + RfidTagUtil.fetchParameters(request));
        }
        List<String> protocols = ProtocolType.getList();
        List<String> types = RfidType.getList();
        HttpSession session = request.getSession();
        session.setAttribute("protocols", protocols);
        session.setAttribute("types", types);
        session.setAttribute("original-rfidtag", originalRfidTag);
        LOGGER.debug("... rfidtag = " + originalRfidTag);
        return new ActionResult(ActionType.FORWARD, "edit-rfidtag");
    }
}
