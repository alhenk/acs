package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class ShowUserList implements Action{
    private static final Logger LOGGER = Logger.getLogger(ShowUserList.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        List<User> users = new LinkedList<User>();
        try {
            users = userDao.list();
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        } catch (IllegalArgumentException e){
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        }
        HttpSession session = request.getSession();
        session.setAttribute("users",users);
        return new ActionResult(ActionType.FORWARD,"user-list");
    }
}
