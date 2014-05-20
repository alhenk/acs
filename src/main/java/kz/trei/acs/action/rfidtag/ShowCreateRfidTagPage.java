package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowCreateRfidTagPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowCreateRfidTagPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<String> protocols = ProtocolType.getList();
        List<String> types = RfidType.getList();
        session.setAttribute("protocols", protocols);
        session.setAttribute("types", types);
        return new ActionResult(ActionType.FORWARD, "create-rfidtag");
    }
}