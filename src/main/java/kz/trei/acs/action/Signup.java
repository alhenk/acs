package kz.trei.acs.action;

import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Signup implements Action{
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = request.getParameter("user-role");
        String tableID = request.getParameter("table-id");
        session.setAttribute("username", username);
        session.setAttribute("user-role", userRole);
        session.setAttribute("table-id", tableID);
        session.removeAttribute("status");

        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        if(isFormComplete(request)){
            User user = new User(username, password, tableID, RoleType.valueOf(userRole.toUpperCase()));
            userDao.create(user);
            session.removeAttribute("username");
            session.removeAttribute("user-role");
            session.removeAttribute("table-id");
            session.setAttribute("status","status.create.account.success");
            return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
        }
        session.setAttribute("error", "form.incomplete");
        return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
    }

    private boolean isFormComplete(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = request.getParameter("user-role");
        String tableID = request.getParameter("table-id");
        if(username==null || username.isEmpty()) return false;
        if(password==null || password.isEmpty()) return false;
        if(userRole==null || userRole.isEmpty()) return false;
        if(tableID==null || tableID.isEmpty()) return false;
        return true;
    }
}
