package kz.trei.acs.action;

import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alhen on 4/25/14.
 */
public class ShowUserList implements Action
{
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        List<User> users = new LinkedList<User>();
        try {
            users = userDao.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("users",users);
        return new ActionResult(ActionType.FORWARD,"user-list");
    }
}
