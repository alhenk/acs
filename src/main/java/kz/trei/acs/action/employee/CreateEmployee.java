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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateEmployee implements Action {
    private static final Logger LOGGER = Logger.getLogger(CreateEmployee.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        createFieldAttributes(request);
        if (isFormValid(request)) {
            Person employee = buildEmployee(request);
            DaoFactory daoFactory = DaoFactory.getFactory();
            EmployeeDao employeeDao = daoFactory.getEmployeeDao();
            try {
                employeeDao.create(employee);
            } catch (DaoException e) {
                LOGGER.error("SQL statement exception execute: " + e.getMessage());
                request.setAttribute("error", e.getMessage());
                request.setAttribute("status", "form.employee.create.fail");
                return new ActionResult(ActionType.REDIRECT, "create-employee" + fetchParameters(request));
            }
            killFieldAttributes(request);
            request.setAttribute("status", "form.employee.create.success");
            return new ActionResult(ActionType.REDIRECT, "employee-list" + fetchParameters(request));
        }
        request.setAttribute("error", "form.employee.incomplete");
        return new ActionResult(ActionType.REDIRECT, "create-employee" + fetchParameters(request));
    }

    private Person buildEmployee(HttpServletRequest request) {
        String firstName = request.getParameter("first-name");
        String patronym = request.getParameter("patronym");
        String lastName = request.getParameter("last-name");
        String tableId = request.getParameter("table-id");
        String uid = request.getParameter("uid");
        //patronym is optional
        if(patronym == null){patronym="";}
        DateStamp birthDate;
        try {
            birthDate = DateStamp.create(request.getParameter("birth-date"));
        } catch (DateStampException e) {
            birthDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty birth date due to exception: " + e.getMessage());
        }
        PositionType position = null;
        try {
            position = PositionType.valueOf(request.getParameter("position"));
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
        RfidTag rfidTag = new RfidTag.Builder().build();
        try {
            rfidTag.setUid(uid);
        } catch (UidFormatException e) {
            rfidTag.setEmptyUid();
            LOGGER.debug("Assigned empty UID \"00000000\" due to exception: " + e.getMessage());
        }
        return new Employee.Builder()
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

    /**
     * Create employee form field attributes
     * for keeping filled in data
     *
     * @param request
     */
    private void createFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("first-name", request.getParameter("first-name"));
        session.setAttribute("patronym", request.getParameter("patronym"));
        session.setAttribute("last-name", request.getParameter("last-name"));
        session.setAttribute("birth-date", request.getParameter("birth-date"));
        session.setAttribute("position", request.getParameter("position"));
        session.setAttribute("department", request.getParameter("department"));
        session.setAttribute("room", request.getParameter("room"));
        session.setAttribute("table-id", request.getParameter("table-id"));
        session.setAttribute("uid", request.getParameter("uid"));
    }

    private void killFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("first-name");
        session.removeAttribute("patronym");
        session.removeAttribute("last-name");
        session.removeAttribute("birth-date");
        session.removeAttribute("position");
        session.removeAttribute("department");
        session.removeAttribute("room");
        session.removeAttribute("table-id");
        session.removeAttribute("uid");
    }

    private String fetchParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder("?");
        String firstNameError = (String) request.getAttribute("first-name-error");
        String patronymError = (String) request.getAttribute("patronym-error");
        String lastNameError = (String) request.getAttribute("last-name-error");
        String birthDateError = (String) request.getAttribute("birth-date-error");
        String positionError = (String) request.getAttribute("position-error");
        String departmentError = (String) request.getAttribute("department-error");
        String roomError = (String) request.getAttribute("room-error");
        String tableIdError = (String) request.getAttribute("table-id-error");
        String uidError = (String) request.getAttribute("uid-error");
        String error = (String) request.getAttribute("error");
        String status = (String) request.getAttribute("status");
        if (firstNameError != null) {
            parameters.append("&first-name-error=" + firstNameError);
        }
        if (patronymError != null) {
            parameters.append("&patronym-error=" + patronymError);
        }
        if (lastNameError != null) {
            parameters.append("&last-name-error=" + lastNameError);
        }
        if (birthDateError != null) {
            parameters.append("&birth-date-error=" + birthDateError);
        }
        if (positionError != null) {
            parameters.append("&position-error=" + positionError);
        }

        if (departmentError != null) {
            parameters.append("&department-error=" + departmentError);
        }
        if (roomError != null) {
            parameters.append("&room-error=" + roomError);
        }
        if (tableIdError != null) {
            parameters.append("&table-id-error=" + tableIdError);
        }
        if (uidError != null) {
            parameters.append("&uid-error=" + uidError);
        }
        if (status != null) {
            parameters.append("&status=" + status);
        }
        if (error != null) {
            parameters.append("&error=" + error);
        }
        return parameters.toString();
    }

    private boolean isFormValid(HttpServletRequest request) {
        return isFirstNameValid(request)
                & isPatronymValid(request)
                & isLastNameValid(request)
                & isBirthDateValid(request)
                & isPositionValid(request)
                & isDepartmentValid(request)
                & isRoomValid(request)
                & isTableIdValid(request)
                & isUidValid(request);
    }

    //FIRST NAME VALIDATION
    private boolean isFirstNameValid(HttpServletRequest request) {
        String firstName = request.getParameter("first-name");
        boolean isFirstNameValid = true;
        Matcher firstNameMatcher = null;
        if (firstName == null || firstName.isEmpty()) {
            isFirstNameValid = false;
            request.setAttribute("first-name-error", "form.employee.empty");
        } else {
            String firstNameRegex = PropertyManager.getValue("form.employee.first-name.regex");
            Pattern firstNamePattern = Pattern.compile(firstNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            firstNameMatcher = firstNamePattern.matcher(firstName);
            if (firstNameMatcher != null && firstNameMatcher.matches()) {
                isFirstNameValid = true;
            } else {
                isFirstNameValid = false;
                request.setAttribute("first-name-error", "form.employee.first-name.malformed");
            }
        }
        LOGGER.debug("Is first name valid " + isFirstNameValid);
        return isFirstNameValid;
    }

    //PATRONYM VALIDATION
    private boolean isPatronymValid(HttpServletRequest request) {
        String patronym = request.getParameter("patronym");
        boolean isPatronymValid = true;
        Matcher patronymMatcher = null;
        if (patronym == null || patronym.isEmpty()) {
            //patronym is optional
            isPatronymValid = true;
        } else {
            String patronymRegex = PropertyManager.getValue("form.employee.patronym.regex");
            Pattern patronymPattern = Pattern.compile(patronymRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            patronymMatcher = patronymPattern.matcher(patronym);
            if (patronymMatcher != null && patronymMatcher.matches()) {
                isPatronymValid = true;
            } else {
                isPatronymValid = false;
                request.setAttribute("patronym-error", "form.employee.patronym.malformed");
            }
        }
        LOGGER.debug("Is patronym valid " + isPatronymValid);
        return isPatronymValid;
    }

    //LAST NAME VALIDATION
    private boolean isLastNameValid(HttpServletRequest request) {
        String lastName = request.getParameter("last-name");
        boolean isLastNameValid = true;
        Matcher lastNameMatcher = null;
        if (lastName == null || lastName.isEmpty()) {
            isLastNameValid = false;
            request.setAttribute("last-name-error", "form.employee.empty");
        } else {
            String lastNameRegex = PropertyManager.getValue("form.employee.last-name.regex");
            Pattern lastNamePattern = Pattern.compile(lastNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            lastNameMatcher = lastNamePattern.matcher(lastName);
            if (lastNameMatcher != null && lastNameMatcher.matches()) {
                isLastNameValid = true;
            } else {
                isLastNameValid = false;
                request.setAttribute("last-name-error", "form.employee.last-name.malformed");
            }
        }
        LOGGER.debug("Is last name valid " + isLastNameValid);
        return isLastNameValid;
    }

    //BIRTH DATE VALIDATION
    private boolean isBirthDateValid(HttpServletRequest request) {
        String birthDate = request.getParameter("birth-date");
        if (birthDate == null || birthDate.isEmpty()){
            request.setAttribute("birth-date-error", "form.employee.empty");
            LOGGER.debug("Birth date is invalid ");
            return false;
        }
        if (!DateStamp.isDateStampValid(birthDate)) {
            request.setAttribute("birth-date-error", "form.employee.birth-date.malformed");
            LOGGER.debug("Birth date is invalid ");
            return false;
        }
        LOGGER.debug("Birth date is valid ");
        return true;
    }

    //POSITION VALIDATION
    private boolean isPositionValid(HttpServletRequest request) {
        String position = request.getParameter("position");
        if (position == null || position.isEmpty()){
            request.setAttribute("position-error", "form.employee.empty");
            LOGGER.debug("Position is invalid ");
            return false;
        }
        PositionType positionType = PositionType.valueOf(position);
        if (positionType == null) {
            request.setAttribute("position-error", "form.employee.position.malformed");
            LOGGER.debug("Position is invalid ");
            return false;
        }
        LOGGER.debug("Position is valid ");
        return true;
    }

    //DEPARTMENT VALIDATION
    private boolean isDepartmentValid(HttpServletRequest request) {
        String department = request.getParameter("department");
        if (department == null || department.isEmpty()){
            request.setAttribute("department-error", "form.employee.empty");
            LOGGER.debug("Department is invalid ");
            return false;
        }
        DepartmentType departmentType = DepartmentType.valueOf(department);
        if (departmentType == null) {
            request.setAttribute("department-error", "form.employee.department.malformed");
            LOGGER.debug("Department is invalid ");
            return false;
        }
        LOGGER.debug("Department is valid ");
        return true;
    }

    //ROOM VALIDATION
    private boolean isRoomValid(HttpServletRequest request) {
        String room = request.getParameter("room");
        if (room == null || room.isEmpty()){
            request.setAttribute("room-error", "form.employee.empty");
            LOGGER.debug("Room is invalid ");
            return false;
        }
        RoomType roomType = RoomType.valueOf(room);
        if (roomType == null) {
            request.setAttribute("room-error", "form.employee.room.malformed");
            LOGGER.debug("Room is invalid ");
            return false;
        }
        LOGGER.debug("Room is valid ");
        return true;
    }

    //TABLE_ID MATCHER
    private boolean isTableIdValid(HttpServletRequest request) {
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid = false;
        Matcher tableIdMatcher = null;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            request.setAttribute("table-id-error", "form.employee.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.employee.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (tableIdMatcher != null && tableIdMatcher.matches()) {
                isTableIdValid = true;
            } else {
                isTableIdValid = false;
                request.setAttribute("table-id-error", "form.employee.table-id.malformed");
            }
        }
        LOGGER.debug("Is table ID valid " + isTableIdValid);
        return isTableIdValid;
    }

    private boolean isUidValid(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()){
            request.setAttribute("uid-error", "form.employee.empty");
            LOGGER.debug("UID is invalid ");
            return false;
        }
        if (!RfidTag.isUidValid(uid)) {
            request.setAttribute("uid-error", "form.employee.uid.malformed");
            LOGGER.debug("UID is invalid ");
            return false;
        }
        LOGGER.debug("UID is valid ");
        return true;
    }

}
