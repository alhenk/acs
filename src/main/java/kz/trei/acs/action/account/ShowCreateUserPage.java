package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.user.RoleType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowCreateUserPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowCreateUserPage.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<String> roles = RoleType.getList();
        session.setAttribute("roles", roles);
        LOGGER.debug("...");
        return new ActionResult(ActionType.FORWARD, "create-user");
    }
}
