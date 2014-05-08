package kz.trei.acs.action;


import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.EmployeeComparator;
import kz.trei.acs.office.hr.Person;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

public class ShowEmployeeListPage implements Action  {
    private static final Logger LOGGER = Logger.getLogger(ShowEmployeeListPage.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        EmployeeComparator.CompareType compareType;
        try{
        compareType =
                EmployeeComparator.CompareType.valueOf(request.getParameter("sort").toUpperCase());
        } catch (NullPointerException e){
            compareType = EmployeeComparator.CompareType.ID;
        } catch (IllegalArgumentException e){
            compareType = EmployeeComparator.CompareType.ID;
        }
        List<Person> employees;
        try {
            employees = employeeDao.findAll();
            Collections.sort(employees, new EmployeeComparator(compareType)  );
        } catch (DaoException e) {
            LOGGER.error("Getting employee list exception: " + e.getMessage());
            session.setAttribute("error","error.db.employee-list");
            return new ActionResult(ActionType.FORWARD,"error");
        }
        session.setAttribute("employees", employees);
        return new ActionResult(ActionType.FORWARD,"employee-list");
    }
}
