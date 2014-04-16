package kaz.trei.asc.action;


import kaz.trei.asc.dao.UserDao;
import kaz.trei.asc.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Signin implements Action{
    private static final Logger LOGGER = Logger.getLogger(Signin.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        UserDao userDAO = new UserDao();
        try {
            user = userDAO.find(username,password);
            session.setAttribute("user", user);
            return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
        } catch (Exception e) {
            return new ActionResult(ActionType.FORWARD,"/WEB-INF/jsp/errorPage.jsp");
        }
    }
}
