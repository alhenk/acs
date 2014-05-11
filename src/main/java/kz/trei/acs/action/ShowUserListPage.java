package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.user.UserComparator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ShowUserListPage implements Action{
    private static final Logger LOGGER = Logger.getLogger(ShowUserListPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        UserComparator.CompareType compareType;
        try{
            compareType = UserComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e){
            compareType = UserComparator.CompareType.ID;
        } catch (IllegalArgumentException e){
            compareType = UserComparator.CompareType.ID;
        }
        List<User> users = new LinkedList<User>();
        try {
            users = userDao.findAll();
            Collections.sort(users, new UserComparator(compareType));
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        } catch (IllegalArgumentException e){
            LOGGER.error("Getting user list exception: " + e.getMessage());
            session.setAttribute("error","error.db.user-list");
            return new ActionResult(ActionType.FORWARD,"error");
        }
        session.setAttribute("users",users);
        return new ActionResult(ActionType.FORWARD,"user-list");
    }
}
