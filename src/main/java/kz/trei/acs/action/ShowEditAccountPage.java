package kz.trei.acs.action;


import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowEditAccountPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditAccountPage.class);
    static {
        PropertyManager.load("configure.properties");
    }
        @Override
        public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
            response.setCharacterEncoding("UTF-8");
            long id = Long.valueOf(request.getParameter("id"));
            DaoFactory daoFactory = DaoFactory.getFactory();
            UserDao userDao = daoFactory.getUserDao();
            User user;
            try {
                user = userDao.findById(id);
            } catch (DaoException e) {
                LOGGER.error("Getting user list exception: " + e.getMessage());
                return new ActionResult(ActionType.FORWARD,"error");
            } catch (IllegalArgumentException e){
                LOGGER.error("Getting user list exception: " + e.getMessage());
                return new ActionResult(ActionType.FORWARD,"error");
            }
            HttpSession session = request.getSession();

            session.setAttribute("account",user);
            return new ActionResult(ActionType.FORWARD,"edit-account");
        }
}
