package kz.trei.acs.action.account;


import kz.trei.acs.exception.GetParameterException;
import kz.trei.acs.exception.SecurePasswordException;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.Account1CException;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PasswordHash;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserUtil {
    private static final Logger LOGGER = Logger.getLogger(UserUtil.class);

    private UserUtil() {
    }

    public static User buildNewUser(HttpServletRequest request) throws SecurePasswordException {
        LOGGER.debug("buildNewUser ...");
        String password = request.getParameter("password");
        String securePassword;
        try {
            securePassword = PasswordHash.createHash(password);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Creating secure password hash fail : " + e.getMessage());
            throw new SecurePasswordException("Creating secure password hash fail : " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Creating secure password hash fail : " + e.getMessage());
            throw new SecurePasswordException("Creating secure password hash fail : " + e.getMessage());
        }
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        RoleType role = takeRole(request);
        Account1C account1C = buildAccount1C(request);
        User user = new User.Builder(username, securePassword)
                .email(email)
                .account1C(account1C)
                .role(role)
                .build();
        LOGGER.debug("... " + user + " is built");
        return user;
    }

    public static User buildEditedUser(HttpServletRequest request) {
        LOGGER.debug("buildEditedUser ...");
        HttpSession session = request.getSession();
        User originalUser = (User) session.getAttribute("original-user");
        String securePassword = originalUser.getPassword();
        long id = takeId(request);
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        RoleType role = takeRole(request);
        Account1C account1C = buildAccount1C(request);
        User user = new User.Builder(username, securePassword)
                .id(id)
                .email(email)
                .account1C(account1C)
                .role(role)
                .build();
        LOGGER.debug("... " + user + " is built");
        return user;
    }

    private static Account1C buildAccount1C(HttpServletRequest request) {
        LOGGER.debug("buildAccount1C ...");
        Account1C account1C;
        String tableId = request.getParameter("table-id");
        try {
            account1C = Account1C.buildAccount1C(tableId);
        } catch (Account1CException e) {
            account1C = Account1C.defaultAccount1C();
            LOGGER.error("Assigned default table ID due to exception: " + e.getMessage());
        }
        LOGGER.debug("... " + account1C.getTableId());
        return account1C;
    }

    public static String fetchParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder("?");
        String id = request.getParameter("id");
        String userNameError = (String) request.getAttribute("username-error");
        String passwordError = (String) request.getAttribute("password-error");
        String confirmPasswordError = (String) request.getAttribute("confirm-password-error");
        String emailError = (String) request.getAttribute("email-error");
        String roleError = (String) request.getAttribute("role-error");
        String tableIdError = (String) request.getAttribute("table-id-error");
        String status = (String) request.getAttribute("status");
        String error = (String) request.getAttribute("error");
        if (id != null) {
            parameters.append("&id=").append(id);
        }
        if (userNameError != null) {
            parameters.append("&username-error=").append(userNameError);
        }
        if (passwordError != null) {
            parameters.append("&password-error=").append(passwordError);
        }
        if (confirmPasswordError != null) {
            parameters.append("&confirm-password-error=").append(confirmPasswordError);
        }
        if (emailError != null) {
            parameters.append("&email-error=").append(emailError);
        }
        if (roleError != null) {
            parameters.append("&role-error=").append(roleError);
        }
        if (tableIdError != null) {
            parameters.append("&table-id-error=").append(tableIdError);
        }
        if (status != null) {
            parameters.append("&status=").append(status);
        }
        if (error != null) {
            parameters.append("&error=").append(error);
        }
        LOGGER.debug("GET-parameters are fetched");
        return parameters.toString();
    }

    /**
     * Create user form field attributes
     * for keeping filled in data
     */
    public static void createFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("createFieldAttributes ...");
        HttpSession session = request.getSession();
        session.setAttribute("username", request.getParameter("username"));
        session.setAttribute("password", request.getParameter("password"));
        session.setAttribute("confirm-password", request.getParameter("confirm-password"));
        session.setAttribute("email", request.getParameter("email"));
        session.setAttribute("role", request.getParameter("role"));
        session.setAttribute("table-id", request.getParameter("table-id"));
    }

    public static void killFieldAttributes(HttpServletRequest request) {
        LOGGER.debug("killFieldAttributes ...");
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("email");
        session.removeAttribute("confirm-password");
        session.removeAttribute("user-role");
        session.removeAttribute("table-id");
        session.removeAttribute("roles");
        session.removeAttribute("original-user");
    }

    public static long takeId(HttpServletRequest request) {
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
        LOGGER.debug("... " + id);
        return id;
    }

    private static RoleType takeRole(HttpServletRequest request) {
        LOGGER.debug("takeRole ...");
        RoleType role;
        try {
            role = RoleType.valueOf(request.getParameter("role"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned default user role due to illegal argument : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned default user role due to null value : " + e.getMessage());
            role = RoleType.UNREGISTERED;
        }
        LOGGER.debug("... " + role);
        return role;
    }

    //USER NAME VALIDATION
    public static boolean isUserNameValid(HttpServletRequest request) {
        LOGGER.debug("isUserNameValid ...");
        String username = request.getParameter("username");
        boolean isUserNameValid;
        Matcher userNameMatcher;
        if (username == null || username.isEmpty()) {
            isUserNameValid = false;
            request.setAttribute("username-error", "form.user.empty");
        } else {
            String userNameRegex = PropertyManager.getValue("form.user.name.regex");
            Pattern userNamePattern = Pattern.compile(userNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            userNameMatcher = userNamePattern.matcher(username);
            if (!(isUserNameValid = userNameMatcher.matches())) {
                request.setAttribute("username-error", "form.user.name.malformed");
            }
        }
        LOGGER.debug("... " + isUserNameValid);
        return isUserNameValid;
    }

    //PASSWORD VALIDATION
    public static boolean isPasswordValid(HttpServletRequest request) {
        LOGGER.debug("isPasswordValid ...");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        boolean isPasswordValid;
        Matcher passwordMatcher;
        if (password == null || password.isEmpty()) {
            isPasswordValid = false;
            request.setAttribute("password-error", "form.user.empty");
        } else {
            String passwordRegex = PropertyManager.getValue("form.user.password.regex");
            Pattern passwordPattern = Pattern.compile(passwordRegex,
                    Pattern.UNICODE_CHARACTER_CLASS);
            passwordMatcher = passwordPattern.matcher(password);
            if (!(isPasswordValid = passwordMatcher.matches())) {
                request.setAttribute("password-error", "form.user.password.malformed");
            }
            if (isPasswordValid && !password.equals(confirmPassword)) {
                request.setAttribute("confirm-password-error", "form.user.password.not-confirmed");
                isPasswordValid = false;
            }
        }
        LOGGER.debug("... " + isPasswordValid);
        return isPasswordValid;
    }

    //EMAIL VALIDATION
    public static boolean isEmailValid(HttpServletRequest request) {
        LOGGER.debug("isEmailValid ...");
        String email = request.getParameter("email");
        boolean isEmailValid;
        Matcher emailMatcher;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
            request.setAttribute("email-error", "form.user.empty");
        } else {
            String emailRegex = PropertyManager.getValue("form.user.email.regex");
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (!(isEmailValid = emailMatcher.matches())) {
                request.setAttribute("email-error", "form.user.email.malformed");
            }
        }
        LOGGER.debug("... " + isEmailValid);
        return isEmailValid;
    }

    //ROLE MATCHER
    public static boolean isRoleValid(HttpServletRequest request) {
        LOGGER.debug("isRoleValid ...");
        String role = request.getParameter("role");
        boolean isRoleValid;
        Matcher roleMatcher;
        if (role == null || role.isEmpty()) {
            isRoleValid = false;
            request.setAttribute("role-error", "form.user.empty");
        } else {
            String roleRegex = PropertyManager.getValue("form.user.role.regex");
            Pattern rolePattern = Pattern.compile(roleRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            roleMatcher = rolePattern.matcher(role);
            if (!(isRoleValid = roleMatcher.matches())) {
                request.setAttribute("role-error", "form.user.role.malformed");
            }
        }
        LOGGER.debug("... " + isRoleValid);
        return isRoleValid;
    }

    /**
     * TABLE_ID MATCHER
     */
    public static boolean isTableIdValid(HttpServletRequest request) {
        LOGGER.debug("isTableIdValid ...");
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid;
        Matcher tableIdMatcher;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            request.setAttribute("table-id-error", "form.user.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.user.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (!(isTableIdValid = tableIdMatcher.matches())) {
                request.setAttribute("table-id-error", "form.user.table-id.malformed");
            }
        }
        LOGGER.debug("... " + isTableIdValid);
        return isTableIdValid;
    }
}
