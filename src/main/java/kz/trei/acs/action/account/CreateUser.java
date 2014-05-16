package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.Account1CException;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PasswordHash;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(CreateUser.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        createFieldAttributes(request);
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User user;
        if (isFormValid(request)) {
            try {
                user = buildUser(request);
                userDao.create(user);
            } catch (DaoException e) {
                request.setAttribute("status", "form.user.create.fail");
                LOGGER.error("SQL INSERT statement exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "create-user" + fetchParameters(request));
            } catch (NoSuchAlgorithmException e) {
                killFieldAttributes(request);
                LOGGER.error("Creating secure password hash fail : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "error?status=error.password-hash.fail");
            } catch (InvalidKeySpecException e) {
                killFieldAttributes(request);
                LOGGER.error("Creating secure password hash fail  : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "error?status=error.password-hash.fail");
            }
            killFieldAttributes(request);
            request.setAttribute("status", "form.user.create.success");
            LOGGER.debug("Create user success");
            return new ActionResult(ActionType.REDIRECT, "user-list" + fetchParameters(request));
        }
        request.setAttribute("error", "form.user.incomplete");
        LOGGER.debug("Create user fail");
        return new ActionResult(ActionType.REDIRECT, "create-user" + fetchParameters(request));
    }

    private User buildUser(HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String tableId = request.getParameter("table-id");
        String securePassword = PasswordHash.createHash(password);
        RoleType role = null;
        try {
            role = RoleType.valueOf(request.getParameter("role"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default user role due to illegal argument : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default user role due to null value : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        }
        Account1C account1C;
        try {
            account1C = Account1C.createId(tableId);
        } catch (Account1CException e) {
            account1C = Account1C.defaultId();
            LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
        }
        return new User.Builder(username, securePassword)
                .email(email)
                .tableId(account1C)
                .role(role)
                .build();
    }

    /**
     * Create user form field attributes
     * for keeping filled in data
     *
     * @param request
     */
    private void createFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("username", request.getParameter("username"));
        session.setAttribute("password", request.getParameter("password"));
        session.setAttribute("confirm-password", request.getParameter("confirm-password"));
        session.setAttribute("email", request.getParameter("email"));
        session.setAttribute("role", request.getParameter("role"));
        session.setAttribute("table-id", request.getParameter("table-id"));
    }

    private void killFieldAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("email");
        session.removeAttribute("confirm-password");
        session.removeAttribute("user-role");
        session.removeAttribute("table-id");
    }

    private String fetchParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder("?");
        String userNameError = (String) request.getAttribute("username-error");
        String passwordError = (String) request.getAttribute("password-error");
        String confirmPasswordError = (String) request.getAttribute("confirm-password-error");
        String emailError = (String) request.getAttribute("email-error");
        String roleError = (String) request.getAttribute("role-error");
        String tableIdError = (String) request.getAttribute("table-id-error");
        String error = (String) request.getAttribute("error");
        String status = (String) request.getAttribute("status");
        if (userNameError != null) {
            parameters.append("&username-error=" + userNameError);
        }
        if (passwordError != null) {
            parameters.append("&password-error=" + passwordError);
        }
        if (confirmPasswordError != null) {
            parameters.append("&confirm-password-error=" + confirmPasswordError);
        }
        if (emailError != null) {
            parameters.append("&email-error=" + emailError);
        }
        if (roleError != null) {
            parameters.append("&role-error=" + roleError);
        }
        if (tableIdError != null) {
            parameters.append("&table-id-error=" + tableIdError);
        }
        if (status != null) {
            parameters.append("&status=" + status);
        }
        if (error != null) {
            parameters.append("&error=" + error);
        }
        LOGGER.debug("Parameters for GET request are prepared");
        return parameters.toString();
    }

    private boolean isFormValid(HttpServletRequest request) {
        return isUserNameValid(request)
                & isPasswordValid(request)
                & isEmailValid(request)
                & isTableIdValid(request)
                & isRoleValid(request);
    }

    //USER NAME VALIDATION
    private boolean isUserNameValid(HttpServletRequest request) {
        String username = request.getParameter("username");
        boolean isUserNameValid = true;
        Matcher userNameMatcher = null;
        if (username == null || username.isEmpty()) {
            isUserNameValid = false;
            request.setAttribute("username-error", "form.user.empty");
        } else {
            String userNameRegex = PropertyManager.getValue("form.user.name.regex");
            Pattern userNamePattern = Pattern.compile(userNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            userNameMatcher = userNamePattern.matcher(username);
            if (userNameMatcher != null && userNameMatcher.matches()) {
                isUserNameValid = true;
            } else {
                isUserNameValid = false;
                request.setAttribute("username-error", "form.user.name.malformed");
            }
        }
        LOGGER.debug("Is user name valid - " + isUserNameValid);
        return isUserNameValid;
    }

    //PASSWORD VALIDATION
    private boolean isPasswordValid(HttpServletRequest request) {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        boolean isPasswordValid = false;
        Matcher passwordMatcher = null;
        if (password == null || password.isEmpty()) {
            isPasswordValid = false;
            request.setAttribute("password-error", "form.user.empty");
        } else {
            String passwordRegex = PropertyManager.getValue("form.user.password.regex");
            Pattern passwordPattern = Pattern.compile(passwordRegex,
                    Pattern.UNICODE_CHARACTER_CLASS);
            passwordMatcher = passwordPattern.matcher(password);
            if (passwordMatcher != null && passwordMatcher.matches()) {
                isPasswordValid = true;
            } else {
                isPasswordValid = false;
                request.setAttribute("password-error", "form.user.password.malformed");
            }
            if (isPasswordValid && !password.equals(confirmPassword)) {
                request.setAttribute("confirm-password-error", "form.user.password.not-confirmed");
                isPasswordValid = false;
            }
        }
        LOGGER.debug("Is user password valid - " + isPasswordValid);
        return isPasswordValid;
    }

    //EMAIL VALIDATION
    private boolean isEmailValid(HttpServletRequest request) {
        String email = request.getParameter("email");
        boolean isEmailValid = false;
        Matcher emailMatcher = null;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
            request.setAttribute("email-error", "form.user.empty");
        } else {
            String emailRegex = PropertyManager.getValue("form.user.email.regex");
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (emailMatcher != null && emailMatcher.matches()) {
                isEmailValid = true;
            } else {
                isEmailValid = false;
                request.setAttribute("email-error", "form.user.email.malformed");
            }
        }
        LOGGER.debug("Is user e-mail valid - " + isEmailValid);
        return isEmailValid;
    }

    //ROLE MATCHER
    private boolean isRoleValid(HttpServletRequest request) {
        String role = request.getParameter("role");
        boolean isRoleValid = false;
        Matcher roleMatcher = null;
        if (role == null || role.isEmpty()) {
            isRoleValid = false;
            request.setAttribute("role-error", "form.user.empty");
        } else {
            String roleRegex = PropertyManager.getValue("form.user.role.regex");
            Pattern rolePattern = Pattern.compile(roleRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            roleMatcher = rolePattern.matcher(role);
            if (roleMatcher != null && roleMatcher.matches()) {
                isRoleValid = true;
            } else {
                isRoleValid = false;
                request.setAttribute("role-error", "form.user.role.malformed");
            }
        }
        LOGGER.debug("Is user role valid - " + isRoleValid);
        return isRoleValid;
    }

    //TABLE_ID MATCHER
    private boolean isTableIdValid(HttpServletRequest request) {
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid = false;
        Matcher tableIdMatcher = null;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            request.setAttribute("table-id-error", "form.user.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.user.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (tableIdMatcher != null && tableIdMatcher.matches()) {
                isTableIdValid = true;
            } else {
                isTableIdValid = false;
                request.setAttribute("table-id-error", "form.user.table-id.malformed");
            }
        }
        LOGGER.debug("Is user table ID valid - " + isTableIdValid);
        return isTableIdValid;
    }
}
