package kz.trei.acs.action.reports;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.AttendanceDao;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.office.attendance.Attendance;
import kz.trei.acs.office.attendance.OfficeHour;
import kz.trei.acs.util.DateStamp;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowLateArrivalReportPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowLateArrivalReportPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        AttendanceDao attendanceDao = daoFactory.getAttendanceDao();
        String year  = AttendanceUtil.takeYear(request);
        String month = AttendanceUtil.takeMonth(request);
        List<OfficeHour> officeHourList;
        try {
            officeHourList = attendanceDao.lateArrivalReportMonthly(year, month);
        } catch (DaoException e) {
            request.setAttribute("error", "error.db.employee-list");
            LOGGER.error("... getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error");
        } catch (RuntimeException e) {
            request.setAttribute("error", "error.db.employee-list");
            LOGGER.error("... getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error");
        }
        session.setAttribute("office-hour-list", officeHourList);
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        LOGGER.debug("... " + officeHourList.size());
        return new ActionResult(ActionType.FORWARD, "report-list");
    }
}
