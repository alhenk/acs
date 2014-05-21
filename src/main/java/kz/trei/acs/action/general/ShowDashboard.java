package kz.trei.acs.action.general;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.action.reports.AttendanceUtil;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.MonthType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowDashboard implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowDashboard.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        List<String> months = MonthType.getList();
        HttpSession session = request.getSession();
        session.setAttribute("months", months);
        String paramMonth = AttendanceUtil.takeYear(request);
        String attrMonth = (String) session.getAttribute("month");
        String month = attrMonth != null ? attrMonth : paramMonth;
        String paramYear =  AttendanceUtil.takeYear(request);
        String attrYear = (String) session.getAttribute("year");
        String year = attrYear != null ? attrYear : paramYear;
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        return new ActionResult(ActionType.FORWARD, "dashboard");
    }
}
