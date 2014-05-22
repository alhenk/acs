package kz.trei.acs.action.reports;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.AttendanceDao;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.office.attendance.OfficeHour;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowIndividualMonthlyReportPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowIndividualMonthlyReportPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        AttendanceDao attendanceDao = daoFactory.getAttendanceDao();
        String year = AttendanceUtil.takeYear(request);
        String month = AttendanceUtil.takeMonth(request);
        User user = (User) session.getAttribute("user");
        Account1C account1C = user.getAccount1C();
        List<String> sorts = AttendanceUtil.takeSorts(request);
        List<OfficeHour> officeHourList;
        try {
            officeHourList = attendanceDao.individualMonthlyReport(year, month, account1C, sorts);
        } catch (DaoException e) {
            request.setAttribute("error", "error.db.report-list");
            LOGGER.error("... getting monthly report list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error");
        } catch (RuntimeException e) {
            request.setAttribute("error", "error.db.report-list");
            LOGGER.error("... getting monthly report list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error");
        }
        long numTuples = officeHourList.size();
        long page = AttendanceUtil.takePage(request);
        long limit = AttendanceUtil.takeLimit(request);
        long numPages = (long) (Math.ceil((1.0 * numTuples) / limit));
        long offset = (page - 1) < 0 || page > numPages ? 0 : (page - 1) * limit;
        List<OfficeHour> officeHours =
                officeHourList.subList((int) Math.max(0, offset), (int) Math.min(numTuples, offset + limit));
        session.setAttribute("num-pages", numPages);
        session.setAttribute("page", page);
        session.setAttribute("office-hour-list", officeHours);
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        session.setAttribute("report-action", "individual-monthly-report");
        LOGGER.debug("... " + officeHourList.size());
        return new ActionResult(ActionType.FORWARD, "report-list");
    }
}
