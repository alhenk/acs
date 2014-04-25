package kz.trei.acs.action;


import kz.trei.acs.dao.UserDao;
import kz.trei.acs.dao.UserDaoH2;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Signin implements Action {
    private static final Logger LOGGER = Logger.getLogger(Signin.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = null;
        UserDao userDAO = new UserDaoH2();
        try {
            user = userDAO.find(username, password);
        } catch (Exception e) {
            session.setAttribute("error", "form.wrong.password");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        if (user == null) {
            session.setAttribute("error", "form.wrong.password");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        session.removeAttribute("error");
        session.setAttribute("user", user);
        return new ActionResult(ActionType.REDIRECT, "dashboard");
    }
}
