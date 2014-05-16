package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.action.rfidtag.ShowEditRfidTagPage;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowEditEmployeePage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
            if (id < 0) {
                throw new NumberFormatException("negative id = " + id);
            }
        } catch (NumberFormatException e) {
            LOGGER.error("GET parameter \"id\" is not valid : " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?status=error.parameter.id.invalid");
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        Person originalEmployee;
        try {
            originalEmployee = employeeDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("DAO find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.find-by-id");
        }
        HttpSession session = request.getSession();
        List<String> positions = PositionType.getList();
        List<String> departments = DepartmentType.getList();
        List<String> rooms = RoomType.getList();
        session.setAttribute("positions", positions);
        session.setAttribute("departments", departments);
        session.setAttribute("rooms", rooms);
        session.setAttribute("original-employee", originalEmployee);
        LOGGER.debug("... employee = " + originalEmployee);
        return new ActionResult(ActionType.FORWARD, "edit-employee");
    }
}
