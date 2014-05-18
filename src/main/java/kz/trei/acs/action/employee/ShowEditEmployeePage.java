package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.action.account.UserUtil;
import kz.trei.acs.action.rfidtag.RfidTagUtil;
import kz.trei.acs.action.rfidtag.ShowEditRfidTagPage;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.util.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowEditEmployeePage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowEditRfidTagPage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        Person originalEmployee;
        try {
            id = EmployeeUtil.takeIdFromRequest(request);
            originalEmployee = employeeDao.findById(id);
        } catch (DaoException e) {
            EmployeeUtil.killFieldAttributes(request);
            request.setAttribute("error", "error.db.find-by-id");
            LOGGER.error("DAO find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
        }catch (GetParameterException e) {
            EmployeeUtil.killFieldAttributes(request);
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error" + EmployeeUtil.fetchParameters(request));
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
