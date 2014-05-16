package kz.trei.acs.action.account;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowEditUserPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditUserPage.class);

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
                throw new NullPointerException("negative ID");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("GET parameter \"id\" is not valid : " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?status=error.parameter.id.invalid");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User originalUser;
        try {
            originalUser = userDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("DAO find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.find-by-id");
        }
        List<String> roles = RoleType.getList();
        HttpSession session = request.getSession();
        session.setAttribute("original-user", originalUser);
        session.setAttribute("roles", roles);
        return new ActionResult(ActionType.FORWARD, "edit-user");
    }
}
