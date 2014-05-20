package kz.trei.acs.action.general;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowDashboard implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowDashboard.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        List<String> months = new ArrayList<String>();
        months.add("JANUARY");
        months.add("FEBRUARY");
        months.add("MARCH");
        months.add("APRIL");
        months.add("MAY");
        months.add("JUNE");
        HttpSession session = request.getSession();
        session.setAttribute("months", months);
        session.setAttribute("year","2013");
        return new ActionResult(ActionType.FORWARD, "dashboard");
    }
}
