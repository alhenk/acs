package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditRfidTag implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditRfidTag.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        LOGGER.debug("...");
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        RfidTag rfidTag;
        if (isFormValid(request)) {
            try {
                rfidTag = RfidTagUtil.buildEditedRfidTagFromRequest(request);
                rfidTagDao.update(rfidTag);
                LOGGER.debug("rfidtag -> " + rfidTag);
            } catch (DaoException e) {
                request.setAttribute("status", "form.rfidtag.create.fail");
                LOGGER.error("SQL UPDATE RFIDTAGS exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "edit-rfidtag" + RfidTagUtil.fetchParameters(request));
            } catch (GetParameterException e) {
                RfidTagUtil.killFieldAttributes(request);
                request.setAttribute("status", "error.parameter.id.invalid");
                LOGGER.error(e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "error" + RfidTagUtil.fetchParameters(request));
            }
            RfidTagUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.rfidtag.edit.success");
            LOGGER.debug("Form rfidtag edit success");
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list" + RfidTagUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.rfidtag.incomplete");
        LOGGER.error("Form rfidtag incomplete");
        return new ActionResult(ActionType.REDIRECT, "edit-rfidtag" + RfidTagUtil.fetchParameters(request));
    }

    private boolean isFormValid(HttpServletRequest request) {
        LOGGER.debug("isFormValid...");
        return RfidTagUtil.isUidValid(request)
                & RfidTagUtil.isTypeValid(request)
                & RfidTagUtil.isProtocolValid(request)
                & RfidTagUtil.isIssueDateValid(request)
                & RfidTagUtil.isExpirationDateValid(request);
    }
}
