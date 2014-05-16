package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelCreateRfidTag implements Action {
    private static final Logger LOGGER = Logger.getLogger(CancelCreateRfidTag.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("protocols");
        session.removeAttribute("types");
        session.removeAttribute("rfidtag");
        LOGGER.debug("Create RFID tag canceled");
        return new ActionResult(ActionType.FORWARD, "rfidtag-list");
    }
}
