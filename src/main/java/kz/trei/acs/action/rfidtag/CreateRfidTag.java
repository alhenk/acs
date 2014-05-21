package kz.trei.acs.action.rfidtag;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.rfid.RfidTag;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRfidTag implements Action {
    private static final Logger LOGGER = Logger.getLogger(CreateRfidTag.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        RfidTagUtil.createFieldAttributes(request);
        RfidTag rfidTag;
        if (isFormValid(request)) {
            LOGGER.debug("Form is valid, ready to build RFID tag");
            rfidTag = RfidTagUtil.buildNewRfidTag(request);
            DaoFactory daoFactory = DaoFactory.getFactory();
            RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
            try {
                rfidTagDao.create(rfidTag);
            } catch (DaoException e) {
                request.setAttribute("error", e.getMessage());
                request.setAttribute("status", "form.rfidtag.create.fail");
                LOGGER.error("... SQL INSERT statement exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "create-rfidtag" + RfidTagUtil.fetchParameters(request));
            }
            RfidTagUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.rfidtag.create.success");
            LOGGER.debug("... create rfidtag success");
            return new ActionResult(ActionType.REDIRECT, "rfidtag-list" + RfidTagUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.rfidtag.incomplete");
        LOGGER.debug("... create rfidtag fail");
        return new ActionResult(ActionType.REDIRECT, "create-rfidtag" + RfidTagUtil.fetchParameters(request));
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

