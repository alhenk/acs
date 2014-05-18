package kz.trei.acs.action.employee;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.action.account.UserUtil;
import kz.trei.acs.action.rfidtag.RfidTagUtil;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.EmployeeComparator;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

public class ShowEmployeeListPage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEmployeeListPage.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        long totalNumber;
        long offset = takeOffsetFromRequest(request);
        long length = takeLengthFromRequest(request);
        EmployeeComparator.CompareType employeeComparator
                = takeEmployeeComparatorFromRequest(request);
        List<Person> employees;
        try {
            employees = employeeDao.findInRange(offset, length);
            Collections.sort(employees, new EmployeeComparator(employeeComparator));
            totalNumber = employeeDao.totalNumber();
        } catch (DaoException e) {
            killEmployeeListAttributes(request);
            request.setAttribute("error","error.db.employee-list");
            LOGGER.error("Getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
        } catch (RuntimeException e) {
            killEmployeeListAttributes(request);
            request.setAttribute("error","error.db.employee-list");
            LOGGER.error("Getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
        }
        session.setAttribute("employees", employees);
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        LOGGER.debug("..." + employees);
        return new ActionResult(ActionType.FORWARD, "employee-list");
    }

    private void killEmployeeListAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("employees");
        session.removeAttribute("total-number");
        session.removeAttribute("offset");
        session.removeAttribute("length");
    }

    private EmployeeComparator.CompareType takeEmployeeComparatorFromRequest(HttpServletRequest request) {
        EmployeeComparator.CompareType employeeComparator;
        try {
            employeeComparator =
                    EmployeeComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e) {
            employeeComparator = EmployeeComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        } catch (IllegalArgumentException e) {
            employeeComparator = EmployeeComparator.CompareType.ID;
            LOGGER.debug("Assigned default comparator by ID");
        }
        return employeeComparator;
    }

    private long takeLengthFromRequest(HttpServletRequest request) {
        long length;
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
            LOGGER.error("GET parameter length is empty, assigned configure value (" + length + ")");
        }
        return length;
    }

    private long takeOffsetFromRequest(HttpServletRequest request) {
        long offset;
        try {
            offset = Long.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
            offset = 0;
        }
        return offset;
    }
}
