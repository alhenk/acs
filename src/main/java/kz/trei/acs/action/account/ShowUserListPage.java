package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
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
import java.util.List;


public class ShowUserListPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowUserListPage.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        long totalNumber;
        long offset = takeOffsetFromRequest(request);
        long length = takeLengthFromRequest(request);
        UserComparator.CompareType userComparator = takeUserComparatorFromRequest(request);
        List<User> users;
        try {
            users = userDao.findInRange(offset, length);
            Collections.sort(users, new UserComparator(userComparator));
            totalNumber = userDao.totalNumber();
        } catch (DaoException e) {
            killUserListAttributes(request);
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.user-list");
        } catch (RuntimeException e) {
            killUserListAttributes(request);
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.user-list");
        }
        session.setAttribute("users", users);
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        LOGGER.debug("..." + users);
        return new ActionResult(ActionType.FORWARD, "user-list");
    }

    private void killUserListAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("users");
        session.removeAttribute("total-number");
        session.removeAttribute("offset");
        session.removeAttribute("length");
        LOGGER.debug("Kill User list attributes");
    }

    private long takeLengthFromRequest(HttpServletRequest request) {
        long length;
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
            LOGGER.error("GET parameter length is empty, assigned configure value (" + length + ")");
        }
        return length;
    }

    private long takeOffsetFromRequest(HttpServletRequest request) {
        long offset;
        try {
            offset = Long.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
            offset = 0;
        }
        return offset;
    }

    private UserComparator.CompareType takeUserComparatorFromRequest(HttpServletRequest request) {
        UserComparator.CompareType userComparator;
        try {
            userComparator =
                    UserComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e) {
            userComparator = UserComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        } catch (IllegalArgumentException e) {
            userComparator = UserComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        }
        return userComparator;
    }
}
