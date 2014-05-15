package kz.trei.acs.action.employee;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
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
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        int length;
        int offset = 0;
        long totalNumber = 0;
        try {
            offset = Integer.valueOf(request.getParameter("offset"));
        } catch (NumberFormatException e1) {
                offset = 0;
        }
        try {
            length = Integer.valueOf(request.getParameter("length"));
        } catch (NumberFormatException e) {
            LOGGER.error("GET parameter length is empty, assigned default value (20)");
            length = Integer.valueOf(PropertyManager.getValue("paging.length"));
        }
        EmployeeComparator.CompareType compareType;
        try {
            compareType =
                    EmployeeComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e) {
            compareType = EmployeeComparator.CompareType.ID;
        } catch (IllegalArgumentException e) {
            compareType = EmployeeComparator.CompareType.ID;
        }
        List<Person> employees;
        try {
            employees = employeeDao.findInRange(offset, length);
            Collections.sort(employees, new EmployeeComparator(compareType));
            totalNumber = employeeDao.totalNumber();
        } catch (DaoException e) {
            LOGGER.error("Getting employee list exception: " + e.getMessage());
            session.setAttribute("error", "error.db.employee-list");
            return new ActionResult(ActionType.FORWARD, "error");
        }
        session.setAttribute("employees", employees);
        session.setAttribute("total-number", totalNumber);
        session.setAttribute("offset", offset);
        session.setAttribute("length", length);
        return new ActionResult(ActionType.FORWARD, "employee-list");
    }
}
