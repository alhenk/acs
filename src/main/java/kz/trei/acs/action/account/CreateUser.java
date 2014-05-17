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
import kz.trei.acs.util.SecurePasswordException;
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
        UserUtil.createFieldAttributes(request);
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User user;
        if (isFormValid(request)) {
            try {
                user =  UserUtil.buildNewUserFromRequest(request);
                userDao.create(user);
            } catch (DaoException e) {
                request.setAttribute("status", "form.user.create.fail");
                LOGGER.error("SQL INSERT statement exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "create-user" + UserUtil.fetchParameters(request));
            } catch (SecurePasswordException e) {
                UserUtil.killFieldAttributes(request);
                LOGGER.error(e);
                return new ActionResult(ActionType.REDIRECT, "error?status=error.password-hash.fail");
            }
            UserUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.user.create.success");
            LOGGER.debug("Create user success");
            return new ActionResult(ActionType.REDIRECT, "user-list" + UserUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.user.incomplete");
        LOGGER.debug("Create user fail");
        return new ActionResult(ActionType.REDIRECT, "create-user" + UserUtil.fetchParameters(request));
    }

    private boolean isFormValid(HttpServletRequest request) {
        return UserUtil.isUserNameValid(request)
                & UserUtil.isPasswordValid(request)
                & UserUtil.isEmailValid(request)
                & UserUtil.isTableIdValid(request)
                & UserUtil.isRoleValid(request);
    }
}
