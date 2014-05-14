package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeleteRfidTag implements Action{
    private static final Logger LOGGER = Logger.getLogger(DeleteRfidTag.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        long id = Long.valueOf(request.getParameter("id"));
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        if (id==1){
            session.setAttribute("status", "delete.user.fail");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        try {
            rfidTagDao.delete(id);
        } catch (DaoException e) {
            session.setAttribute("status", "delete.rfidtag.fail");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
//        session.setAttribute("status", "delete.rfidtag.success");
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }
}
