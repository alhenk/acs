package kaz.trei.asc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response);
}
