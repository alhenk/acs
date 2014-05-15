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
import java.util.ArrayList;
import java.util.List;

public class ShowEditRfidTagPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);
    static {
        PropertyManager.load("configure.properties");
    }
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id = Long.valueOf(request.getParameter("id"));
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        RfidTag rfidTag;
        try {
             rfidTag = rfidTagDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        } catch (IllegalArgumentException e){
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        }
        HttpSession session = request.getSession();
        List<String> protocols = new ArrayList<String>();
        for (ProtocolType type : ProtocolType.values()) {
            protocols.add(type.toString());
        }
        List<String> types = new ArrayList<String>();
        for (RfidType type : RfidType.values()) {
            types.add(type.toString());
        }
        session.setAttribute("protocols", protocols);
        session.setAttribute("types", types);
        session.setAttribute("rfidtag",rfidTag);
        return new ActionResult(ActionType.FORWARD,"edit-rfidtag");
    }
}
