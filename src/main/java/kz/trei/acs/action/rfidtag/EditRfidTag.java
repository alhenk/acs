package kz.trei.acs.action.rfidtag;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditRfidTag implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }
}
