package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelEditUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowUserListPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("roles");
        session.removeAttribute("original-user");
        return new ActionResult(ActionType.FORWARD, "user-list");
    }
}
