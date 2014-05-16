package kz.trei.acs.action.employee;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.EmployeeDao;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.UidFormatException;
import kz.trei.acs.office.structure.*;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.DateStampException;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditEmployee.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        DaoFactory daoFactory = DaoFactory.getFactory();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
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
        if (isFormValid(request)) {
            Person employee = buildEmployee(request);
            try {
                employeeDao.update(employee);
                LOGGER.debug("employee -> " + employee);
            } catch (DaoException e) {
                LOGGER.error("SQL UPDATE EMPLOYEES exception : " + e.getMessage());
                request.setAttribute("status", "form.employee.create.fail");
                return new ActionResult(ActionType.REDIRECT, "edit-employee" + fetchParameters(request));
            }
            killFieldAttributes(request);
            request.setAttribute("status", "form.employee.edit.success");
            LOGGER.debug("Form employee edit success");
            return new ActionResult(ActionType.REDIRECT, "employee-list" + fetchParameters(request));
        }
        request.setAttribute("error", "form.employee.incomplete");
        LOGGER.error("Form employee incomplete");
        return new ActionResult(ActionType.REDIRECT, "edit-employee" + fetchParameters(request));
    }

    private void killFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("positions");
        session.removeAttribute("departments");
        session.removeAttribute("rooms");
        session.removeAttribute("original-employee");
    }

    private Person buildEmployee(HttpServletRequest request) {
        long id = Long.valueOf((String) request.getParameter("id"));
        String firstName = (String) request.getParameter("firstName");
        String patronym = (String) request.getParameter("patronym");
        String lastName = (String) request.getParameter("lastName");
        String tableId = (String) request.getParameter("tableId");
        DateStamp birthDate;
        try {
            birthDate = DateStamp.create((String) request.getParameter("birth-date"));
        } catch (DateStampException e) {
            birthDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty birth date due to exception: " + e.getMessage());
        }
        PositionType position = null;
        try {
            position = PositionType.valueOf((String) request.getParameter("position"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default position due to illegal argument : " + e.getMessage());
            position = PositionType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default position due to null value : " + e.getMessage());
            position = PositionType.DEFAULT;
        }
        DepartmentType department = null;
        try {
            department = DepartmentType.valueOf(request.getParameter("department"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default department due to illegal argument : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default department due to null value : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        }
        RoomType room;
        try {
            room = RoomType.valueOf(request.getParameter("room"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default room due to illegal argument : " + e.getMessage());
            room = RoomType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default department due to null value : " + e.getMessage());
            room = RoomType.DEFAULT;
        }
        Account1C account1C;
        try {
            account1C = Account1C.createId(tableId);
        } catch (Account1CException e) {
            account1C = Account1C.defaultId();
            LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
        }
        RfidTag rfidTag = new RfidTag();
        try {
            rfidTag.setUid(request.getParameter("uid"));
        } catch (UidFormatException e) {
            rfidTag.setEmptyUid();
            LOGGER.debug("Assigned empty UID \"00000000\" date due to exception: " + e.getMessage());
        }
        LOGGER.debug("Employee " + firstName + " is almost created");
        return new Employee.Builder()
                .id(id)
                .firstName(firstName)
                .patronym(patronym)
                .lastName(lastName)
                .birthDate(birthDate)
                .position(position)
                .department(department)
                .room(room)
                .account1C(account1C)
                .rfidTag(rfidTag)
                .build();
    }

    private String fetchParameters(HttpServletRequest request) {
        return "";
    }

    private boolean isFormValid(HttpServletRequest request) {
        return true;
    }
}
