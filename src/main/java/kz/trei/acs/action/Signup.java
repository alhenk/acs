package kz.trei.acs.action;

import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by alhen on 4/23/14.
 */
public class Signup implements Action{
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = request.getParameter("user-role");
        String tableID = request.getParameter("table-id");

        UserDao userDAO = new UserDao();
        if(isFormComplete(request)){
            User user = new User(username, password, tableID, RoleType.EMPLOYEE);
            userDAO.create(user);
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
