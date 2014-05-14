package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteUser.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("Wrong id parameter " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "user-list?status=id.parameter.error");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        if (id == 1) {
            LOGGER.debug("was trying to delete the read-only record id==1");
            return new ActionResult(ActionType.REDIRECT, "user-list?status=delete.user.fail");
        }
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "user-list?status=delete.user.fail");
        }
        LOGGER.debug("The user is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "user-list?status=delete.user.success");
    }
}
