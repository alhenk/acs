package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.office.structure.Table1C;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Signup implements Action{
    private static final Logger LOGGER = Logger.getLogger(Signup.class);

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
            try {
                User user = new User.Builder(username, password)
                        .tableId(Table1C.createId(tableID))
                        .role(RoleType.valueOf(userRole.toUpperCase())).build();
                userDao.create(user);
            } catch (DaoException e) {
                LOGGER.error("SQL statement exception execute: " + e.getMessage());
                session.setAttribute("status","status.create.account.fail");
                return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
            } catch (IllegalArgumentException e){
                LOGGER.error("Table ID is not valid: " + e.getMessage());
                session.setAttribute("table-id-error", "form.table-id-error");
                session.setAttribute("status","status.create.account.fail");
                return new ActionResult(ActionType.REDIRECT,request.getHeader("referer"));
            }
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
