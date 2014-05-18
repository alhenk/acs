package kz.trei.acs.action.account;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Signin implements Action {
    private static final Logger LOGGER = Logger.getLogger(Signin.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = null;
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        try {
            user = userDao.find(username, password);
        } catch (DaoException e) {
            request.setAttribute("status", "form.sign-in.fail");
            request.setAttribute("error", "form.sign-in.wrong-password");
            LOGGER.error("DAO find user \"" + username + "\" exception");
            return new ActionResult(ActionType.REDIRECT, "main" + UserUtil.fetchParameters(request));
        }
        if (user == null) {
            request.setAttribute("status", "form.sign-in.fail");
            request.setAttribute("error", "form.sign-in.wrong-password");
            LOGGER.debug("User name or password error");
            return new ActionResult(ActionType.REDIRECT, "main" + UserUtil.fetchParameters(request));
        }
        session.setAttribute("user", user);
        request.setAttribute("status", "form.sign-in.success");
        LOGGER.debug("The user logged successfully");
        return new ActionResult(ActionType.REDIRECT, "dashboard" + UserUtil.fetchParameters(request));
    }
}
