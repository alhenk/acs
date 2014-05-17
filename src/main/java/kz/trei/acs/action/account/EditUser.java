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
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EditUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditUser.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
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
            User user = UserUtil.buildEditedUserFromRequest(request);
            try {
                userDao.update(user);
                LOGGER.debug("user ->" + user);
            } catch (DaoException e) {
                LOGGER.error("SQL UPDATE USERS exception : " + e.getMessage());
                request.setAttribute("status", "form.user.create.fail");
                return new ActionResult(ActionType.REDIRECT, "edit-user" + UserUtil.fetchParameters(request));
            }
            UserUtil.killFieldAttributes(request);
            request.setAttribute("status", "form.user.edit.success");
            LOGGER.debug("Form user edit success");
            return new ActionResult(ActionType.REDIRECT, "user-list" + UserUtil.fetchParameters(request));
        }
        request.setAttribute("error", "form.user.incomplete");
        LOGGER.error("Form user incomplete");
        return new ActionResult(ActionType.REDIRECT, "edit-user" + UserUtil.fetchParameters(request));
    }

    private boolean isFormValid(HttpServletRequest request) {
        return UserUtil.isUserNameValid(request)
                & UserUtil.isEmailValid(request)
                & UserUtil.isTableIdValid(request)
                & UserUtil.isRoleValid(request);
    }
}
