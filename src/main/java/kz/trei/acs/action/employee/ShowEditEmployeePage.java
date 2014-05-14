package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.action.rfidtag.ShowEditRfidTagPage;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ShowEditEmployeePage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);
    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id = Long.valueOf(request.getParameter("id"));
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        Person employee;
        try {
            employee = employeeDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        } catch (IllegalArgumentException e){
            LOGGER.error("Getting user list exception: " + e.getMessage());
            return new ActionResult(ActionType.FORWARD,"error");
        }
        HttpSession session = request.getSession();

        session.setAttribute("employee",employee);
        return new ActionResult(ActionType.FORWARD,"edit-employee");
    }
}
