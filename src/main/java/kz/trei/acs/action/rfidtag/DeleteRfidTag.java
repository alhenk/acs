package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteRfidTag implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteRfidTag.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("Wrong id parameter " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list?status=id.parameter.error");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        try {
            rfidTagDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list?status=delete.rfidtag.fail");
        }
        LOGGER.debug("The RFID tag is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "rfidtag-list?status=delete.rfidtag.success");
    }
}
