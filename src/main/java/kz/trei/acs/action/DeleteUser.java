package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUser implements Action{
    private static final Logger LOGGER = Logger.getLogger(DeleteUser.class);

    static {
        PropertyManager.load("configure.properties");
    }
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        long id = Long.valueOf(request.getParameter("id"));
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        if (id==1){
            session.setAttribute("status", "delete.user.fail");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            session.setAttribute("status", "delete.user.fail");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        session.setAttribute("status", "delete.user.success");
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }
}
