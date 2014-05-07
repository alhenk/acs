package kz.trei.acs.action;


import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.dao.RfidTagDao;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowEmployeeListPage implements Action  {
    private static final Logger LOGGER = Logger.getLogger(ShowEmployeeListPage.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        List<Person> employees;
        try {
            employees = employeeDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Getting employee list exception: " + e.getMessage());
            session.setAttribute("error","error.db.employee-list");
            return new ActionResult(ActionType.FORWARD,"error");
        }
        session.setAttribute("employees", employees);
        return new ActionResult(ActionType.FORWARD,"employee-list");
    }
}
