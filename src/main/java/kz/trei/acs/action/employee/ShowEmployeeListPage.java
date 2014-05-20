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
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        long numTuples;
        long page = EmployeeUtil.takePage(request);
        long limit = EmployeeUtil.takeLimit(request);
        long offset;
        long numPages;
        EmployeeComparator.CompareType employeeComparator
                = EmployeeUtil.takeEmployeeComparator(request);
        List<Person> employees;
        try {
            numTuples = employeeDao.numberOfTuples();
            numPages = (long) (Math.ceil((1.0 * numTuples) / limit));
            offset = (page - 1) < 0 || page > numPages ? 0 : (page - 1) * limit;
            employees = employeeDao.findInRange(offset, limit);
            Collections.sort(employees, new EmployeeComparator(employeeComparator));

        } catch (DaoException e) {
            EmployeeUtil.killEmployeeListAttributes(request);
            request.setAttribute("error","error.db.employee-list");
            LOGGER.error("... getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
        } catch (RuntimeException e) {
            EmployeeUtil.killEmployeeListAttributes(request);
            request.setAttribute("error","error.db.employee-list");
            LOGGER.error("... getting employee list exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
        }
        session.setAttribute("employees", employees);
        session.setAttribute("num-pages", numPages);
        session.setAttribute("page", page);
        LOGGER.debug("... " + employees);
        return new ActionResult(ActionType.FORWARD, "employee-list");
    }
}
