package kz.trei.acs.action.account;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowEditUserPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditUserPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User originalUser;
        try {
            id = UserUtil.takeIdFromRequest(request);
            originalUser = userDao.findById(id);
        } catch (DaoException e) {
            UserUtil.killFieldAttributes(request);
            request.setAttribute("error", "error.db.find-by-id");
            LOGGER.error("DAO find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + UserUtil.fetchParameters(request));
        } catch (GetParameterException e) {
            UserUtil.killFieldAttributes(request);
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + UserUtil.fetchParameters(request));
        }
        List<String> roles = RoleType.getList();
        HttpSession session = request.getSession();
        session.setAttribute("roles", roles);
        session.setAttribute("original-user", originalUser);
        LOGGER.debug("... user = " + originalUser);
        return new ActionResult(ActionType.FORWARD, "edit-user");
    }
}
