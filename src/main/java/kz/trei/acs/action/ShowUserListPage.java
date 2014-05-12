package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.user.UserComparator;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ShowUserListPage implements Action{
    private static final Logger LOGGER = Logger.getLogger(ShowUserListPage.class);
    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        int length;
        int offset = 0;
        long totalNumber = 0;
        try {
            offset = Integer.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
            offset = 0;
        }
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            LOGGER.error("Length is not valid");
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
        }

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
            users = userDao.findInRange(offset, length);
            Collections.sort(users, new UserComparator(compareType));
            totalNumber = userDao.totalNumber();
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        } catch (IllegalArgumentException e){
            LOGGER.error("Getting user list exception: " + e.getMessage());
            session.setAttribute("error","error.db.user-list");
            return new ActionResult(ActionType.FORWARD,"error");
        }
        session.setAttribute("users",users);
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        return new ActionResult(ActionType.FORWARD,"user-list");
    }
}
