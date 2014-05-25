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
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        long numTuples;
        long page = UserUtil.takePage(request);
        long limit = UserUtil.takeLimit(request);
        long offset;
        long numPages;
        UserComparator.CompareType userComparator = UserUtil.takeUserComparator(request);
        List<User> users;
        try {
            numTuples = userDao.numberOfTuples();
            numPages = (long) (Math.ceil((1.0 * numTuples) / limit));
            offset = (page - 1) < 0 || page > numPages ? 0 : (page - 1) * limit;
            users = userDao.findInRange(offset, limit);
            Collections.sort(users, new UserComparator(userComparator));
        } catch (DaoException e) {
            UserUtil.killUserListAttributes(request);
            LOGGER.error("... getting user list exception: " + e.getMessage());
            request.setAttribute("error", "error.db.user-list");
            return new ActionResult(ActionType.REDIRECT, "error" + UserUtil.fetchParameters(request));
        } catch (RuntimeException e) {
            UserUtil.killUserListAttributes(request);
            request.setAttribute("error", "error.db.user-list");
            LOGGER.error("... user sort list error");
            return new ActionResult(ActionType.REDIRECT, "error" + UserUtil.fetchParameters(request));
        }
        session.setAttribute("users", users);
        session.setAttribute("num-pages", numPages);
        session.setAttribute("page", page);
        LOGGER.debug("..." + users);
        return new ActionResult(ActionType.FORWARD, "user-list");
    }


}
