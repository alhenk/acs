package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.util.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteRfidTag implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteRfidTag.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        try {
            id = RfidTagUtil.takeIdFromRequest(request);
            if (id == 0 || id == 1) {
                throw new GetParameterException("trying to delete the read-only record");
            }
            rfidTagDao.delete(id);
        } catch (DaoException e) {
            request.setAttribute("status", "delete.rfidtag.fail");
            LOGGER.error("DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list" + RfidTagUtil.fetchParameters(request));
        } catch (GetParameterException e) {
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list" + RfidTagUtil.fetchParameters(request));
        }
        request.setAttribute("status", "delete.rfidtag.success");
        LOGGER.debug("The rfidtag is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "rfidtag-list" + RfidTagUtil.fetchParameters(request));
    }
}
