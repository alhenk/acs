package kz.trei.acs.action;


import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
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
        User user=null;
        UserDao userDAO = new UserDao();
        try {
            user = userDAO.find(username,password);
        } catch (Exception e) {
            session.setAttribute("error", "main.wrong.password");
            return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
//            return new ActionResult(ActionType.FORWARD,"errorPage");
        }
        if (user == null){
            session.setAttribute("error", "main.wrong.password");
            //session.setAttribute("error", PropertyManager.getValue("main.wrong.password"));
            return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
        }
        session.removeAttribute("error");
        session.setAttribute("user", user);
        return new ActionResult(ActionType.REDIRECT,"dashboard");
    }
}
