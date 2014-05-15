package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class ShowCreateRfidTagPage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<String> protocols = new ArrayList<String>();
        for (ProtocolType type :ProtocolType.values()) {
            protocols.add(type.toString());
        }
        List<String> types = new ArrayList<String>();
        for (RfidType type : RfidType.values()) {
            types.add(type.toString());
        }
        session.setAttribute("protocols", protocols);
        session.setAttribute("types", types);
        return new ActionResult(ActionType.FORWARD, "create-rfidtag");
    }
}