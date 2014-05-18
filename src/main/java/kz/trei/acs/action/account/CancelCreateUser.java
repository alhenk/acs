package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelCreateUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(CancelCreateUser.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        UserUtil.killFieldAttributes(request);
        LOGGER.debug("Create user canceled");
        return new ActionResult(ActionType.FORWARD, "user-list");
    }
}
