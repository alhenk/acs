package kz.trei.acs.action.employee;

import kz.trei.acs.exception.DateStampException;
import kz.trei.acs.exception.GetParameterException;
import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.EmployeeComparator;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.UidFormatException;
import kz.trei.acs.office.structure.*;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for Create and Edit employee actions
 */
public final class EmployeeUtil {
    private static final Logger LOGGER = Logger.getLogger(EmployeeUtil.class);

    private EmployeeUtil() {
    }

    public static Person buildNewEmployee(HttpServletRequest request) {
        LOGGER.debug("buildNewEmployee ...");
        String firstName = request.getParameter("first-name");
        String patronym = request.getParameter("patronym");
        String lastName = request.getParameter("last-name");
        //patronym is optional
        if (patronym == null) {
            patronym = "";
        }
        DateStamp birthDate = takeBirthDate(request);
        PositionType position = takePosition(request);
        DepartmentType department = takeDepartment(request);
        RoomType room = takeRoom(request);
        Account1C account1C = buildAccount1C(request);
        RfidTag rfidTag = buildRfidTag(request);
        Person employee = new Employee.Builder()
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
        LOGGER.debug("Employee " + employee + " is built");
        return employee;
    }

    public static Person buildEditedEmployee(HttpServletRequest request) {
        LOGGER.debug("buildEditedEmployee ...");
        long id = takeIdFrom(request);
        String firstName = request.getParameter("first-name");
        String patronym = request.getParameter("patronym");
        String lastName = request.getParameter("last-name");
        //patronym is optional
        if (patronym == null) {
            patronym = "";
        }
        DateStamp birthDate = takeBirthDate(request);
        PositionType position = takePosition(request);
        DepartmentType department = takeDepartment(request);
        RoomType room = takeRoom(request);
        Account1C account1C = buildAccount1C(request);
        RfidTag rfidTag = buildRfidTag(request);
        Person employee = new Employee.Builder()
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
        LOGGER.debug("Employee " + employee + " is built");
        return employee;
    }

    public static Account1C buildAccount1C(HttpServletRequest request) {
        LOGGER.debug("buildAccount1C ...");
        Account1C account1C;
        String tableId = request.getParameter("table-id");
        try {
            account1C = Account1C.buildAccount1C(tableId);
        } catch (Account1CException e) {
            account1C = Account1C.defaultAccount1C();
            LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
        }
        LOGGER.debug(account1C.getTableId());
        return account1C;
    }

    public static RfidTag buildRfidTag(HttpServletRequest request) {
        LOGGER.debug("buildRfidTag ...");
        String uid = request.getParameter("uid");
        RfidTag rfidTag = new RfidTag();
        try {
            rfidTag.setUid(request.getParameter("uid"));
        } catch (UidFormatException e) {
            rfidTag.setEmptyUid();
            LOGGER.debug("Assigned empty UID \"00000000\" date due to exception: " + e.getMessage());
        }
        LOGGER.debug(rfidTag);
        return rfidTag;
    }

    public static String fetchParameters(HttpServletRequest request) {
        LOGGER.debug("fetchParameters ...");
        StringBuilder parameters = new StringBuilder("?");
        String id = request.getParameter("id");
        String firstNameError = (String) request.getAttribute("first-name-error");
        String patronymError = (String) request.getAttribute("patronym-error");
        String lastNameError = (String) request.getAttribute("last-name-error");
        String birthDateError = (String) request.getAttribute("birth-date-error");
        String positionError = (String) request.getAttribute("position-error");
        String departmentError = (String) request.getAttribute("department-error");
        String roomError = (String) request.getAttribute("room-error");
        String tableIdError = (String) request.getAttribute("table-id-error");
        String uidError = (String) request.getAttribute("uid-error");
        String status = (String) request.getAttribute("status");
        String error = (String) request.getAttribute("error");
        if (id != null) {
            parameters.append("&id=").append(id);
        }
        if (firstNameError != null) {
            parameters.append("&first-name-error=").append(firstNameError);
        }
        if (patronymError != null) {
            parameters.append("&patronym-error=").append(patronymError);
        }
        if (lastNameError != null) {
            parameters.append("&last-name-error=").append(lastNameError);
        }
        if (birthDateError != null) {
            parameters.append("&birth-date-error=").append(birthDateError);
        }
        if (positionError != null) {
            parameters.append("&position-error=").append(positionError);
        }
        if (departmentError != null) {
            parameters.append("&department-error=").append(departmentError);
        }
        if (roomError != null) {
            parameters.append("&room-error=").append(roomError);
        }
        if (tableIdError != null) {
            parameters.append("&table-id-error=").append(tableIdError);
        }
        if (uidError != null) {
            parameters.append("&uid-error=").append(uidError);
        }
        if (status != null) {
            parameters.append("&status=").append(status);
        }
        if (error != null) {
            parameters.append("&error=").append(error);
        }
        LOGGER.debug("Get parameters are fetched");
        return parameters.toString();
    }

    /**
     * Create employee form field attributes
     * for keeping filled in data
     */
    public static void createFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("createFieldAttributes ...");
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

    public static void killFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("killFieldAttributes ...");
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
        session.removeAttribute("positions");
        session.removeAttribute("departments");
        session.removeAttribute("rooms");
        session.removeAttribute("original-employee");
    }

    public static long takeIdFrom(HttpServletRequest request) {
        LOGGER.debug("takeId ...");
        long id;
        try {
            id = Long.valueOf(request.getParameter("id"));
            if (id < 0) {
                throw new NumberFormatException("negative id = " + id);
            }
        } catch (NumberFormatException e) {
            throw new GetParameterException("GET parameter \"id\" is not valid : " + e.getMessage());
        }
        LOGGER.debug(id);
        return id;
    }

    public static RoomType takeRoom(HttpServletRequest request) {
        LOGGER.debug("takeRoom ...");
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
        LOGGER.debug(room.getRoomNumber() + " - " + room.getRoomName());
        return room;
    }

    public static DepartmentType takeDepartment(HttpServletRequest request) {
        LOGGER.debug("takeDepartment ...");
        DepartmentType department;
        try {
            department = DepartmentType.valueOf(request.getParameter("department"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default department due to illegal argument : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default department due to null value : " + e.getMessage());
            department = DepartmentType.DEFAULT;
        }
        LOGGER.debug(department);
        return department;
    }

    public static PositionType takePosition(HttpServletRequest request) {
        LOGGER.debug(" takePosition ...");
        PositionType position;
        try {
            position = PositionType.valueOf(request.getParameter("position"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default position due to illegal argument : " + e.getMessage());
            position = PositionType.DEFAULT;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default position due to null value : " + e.getMessage());
            position = PositionType.DEFAULT;
        }
        LOGGER.debug(position);
        return position;
    }

    public static DateStamp takeBirthDate(HttpServletRequest request) {
        LOGGER.debug("takeBirthDate ...");
        DateStamp birthDate;
        try {
            birthDate = DateStamp.create(request.getParameter("birth-date"));
        } catch (DateStampException e) {
            birthDate = DateStamp.createEmptyDate();
            LOGGER.debug("Assigned empty birth date due to exception: " + e.getMessage());
        }
        LOGGER.debug(birthDate.getDate());
        return birthDate;
    }

    public static void killEmployeeListAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("employees");
        session.removeAttribute("page");
        session.removeAttribute("num-pages");
    }

    public static long takePage(HttpServletRequest request) {
        long page;
        try {
            page = Integer.valueOf(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1L;
            LOGGER.error("GET parameter \"page\" is empty, assigned default value 1");
        }
        return page;
    }

    public static long takeLimit(HttpServletRequest request) {
        long limit;
        try {
            limit = Integer.valueOf(request.getParameter("limit"));
        } catch (NumberFormatException e) {
            limit = Integer.valueOf(PropertyManager.getValue("paging.limit"));
            LOGGER.error("GET parameter \"limit\" is empty, assigned value (" + limit + ")");
        }
        return limit;
    }

    public static EmployeeComparator.CompareType takeEmployeeComparator(HttpServletRequest request) {
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

    //FIRST NAME VALIDATION
    public static boolean isFirstNameValid(HttpServletRequest request) {
        LOGGER.debug("isFirstNameValid ...");
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
            if (!(isFirstNameValid = firstNameMatcher.matches())) {
                request.setAttribute("first-name-error", "form.employee.first-name.malformed");
            }
        }
        LOGGER.debug("... valid -> " + isFirstNameValid);
        return isFirstNameValid;
    }

    //PATRONYM VALIDATION
    public static boolean isPatronymValid(HttpServletRequest request) {
        LOGGER.debug("isPatronymValid ...");
        String patronym = request.getParameter("patronym");
        boolean isPatronymValid;
        Matcher patronymMatcher;
        if (patronym == null || patronym.isEmpty()) {
            //patronym is optional
            isPatronymValid = true;
        } else {
            String patronymRegex = PropertyManager.getValue("form.employee.patronym.regex");
            Pattern patronymPattern = Pattern.compile(patronymRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            patronymMatcher = patronymPattern.matcher(patronym);
            if (!(isPatronymValid = patronymMatcher.matches())) {
                request.setAttribute("patronym-error", "form.employee.patronym.malformed");
            }
        }
        LOGGER.debug("... valid -> " + isPatronymValid);
        return isPatronymValid;
    }

    //LAST NAME VALIDATION
    public static boolean isLastNameValid(HttpServletRequest request) {
        LOGGER.debug("isLastNameValid ...");
        String lastName = request.getParameter("last-name");
        boolean isLastNameValid;
        Matcher lastNameMatcher;
        if (lastName == null || lastName.isEmpty()) {
            isLastNameValid = false;
            request.setAttribute("last-name-error", "form.employee.empty");
        } else {
            String lastNameRegex = PropertyManager.getValue("form.employee.last-name.regex");
            Pattern lastNamePattern = Pattern.compile(lastNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            lastNameMatcher = lastNamePattern.matcher(lastName);
            if (!(isLastNameValid = lastNameMatcher.matches())) {
                request.setAttribute("last-name-error", "form.employee.last-name.malformed");
            }
        }
        LOGGER.debug("... valid -> " + isLastNameValid);
        return isLastNameValid;
    }

    //BIRTH DATE VALIDATION
    public static boolean isBirthDateValid(HttpServletRequest request) {
        LOGGER.debug("isBirthDateValid ...");
        String birthDate = request.getParameter("birth-date");
        if (birthDate == null || birthDate.isEmpty()) {
            request.setAttribute("birth-date-error", "form.employee.empty");
            LOGGER.debug("false");
            return false;
        }
        if (!DateStamp.isDateStampValid(birthDate)) {
            request.setAttribute("birth-date-error", "form.employee.birth-date.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //POSITION VALIDATION
    public static boolean isPositionValid(HttpServletRequest request) {
        LOGGER.debug("isPositionValid ...");
        String position = request.getParameter("position");
        if (position == null || position.isEmpty()) {
            request.setAttribute("position-error", "form.employee.empty");
            LOGGER.debug("false");
            return false;
        }
        PositionType positionType = PositionType.valueOf(position);
        if (positionType == null) {
            request.setAttribute("position-error", "form.employee.position.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //DEPARTMENT VALIDATION
    public static boolean isDepartmentValid(HttpServletRequest request) {
        LOGGER.debug("isDepartmentValid ...");
        String department = request.getParameter("department");
        if (department == null || department.isEmpty()) {
            request.setAttribute("department-error", "form.employee.empty");
            LOGGER.debug("false");
            return false;
        }
        DepartmentType departmentType = DepartmentType.valueOf(department);
        if (departmentType == null) {
            request.setAttribute("department-error", "form.employee.department.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //ROOM VALIDATION
    public static boolean isRoomValid(HttpServletRequest request) {
        LOGGER.debug("isRoomValid ...");
        String room = request.getParameter("room");
        if (room == null || room.isEmpty()) {
            request.setAttribute("room-error", "form.employee.empty");
            LOGGER.debug("false");
            return false;
        }
        RoomType roomType = RoomType.valueOf(room);
        if (roomType == null) {
            request.setAttribute("room-error", "form.employee.room.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }

    //TABLE_ID MATCHER
    public static boolean isTableIdValid(HttpServletRequest request) {
        LOGGER.debug("isTableIdValid ...");
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid;
        Matcher tableIdMatcher;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            request.setAttribute("table-id-error", "form.employee.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.employee.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (!(isTableIdValid = tableIdMatcher.matches())) {
                request.setAttribute("table-id-error", "form.employee.table-id.malformed");
            }
        }
        LOGGER.debug("... valid -> " + isTableIdValid);
        return isTableIdValid;
    }

    //UID VALIDATION
    public static boolean isUidValid(HttpServletRequest request) {
        LOGGER.debug("isUidValid ...");
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            request.setAttribute("uid-error", "form.employee.empty");
            LOGGER.debug("false");
            return false;
        }
        if (!RfidTag.isUidValid(uid)) {
            request.setAttribute("uid-error", "form.employee.uid.malformed");
            LOGGER.debug("false");
            return false;
        }
        LOGGER.debug("true");
        return true;
    }
}
