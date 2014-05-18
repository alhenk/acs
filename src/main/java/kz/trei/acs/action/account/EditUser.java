package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditUser.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User user;
        if (isFormValid(request)) {
            try {
                user = UserUtil.buildEditedUser(request);
                userDao.update(user);
                LOGGER.debug("user ->" + user);
            } catch (DaoException e) {
                request.setAttribute("status", "form.user.create.fail");
                LOGGER.error("SQL UPDATE USERS exception : " + e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "edit-user" + UserUtil.fetchParameters(request));
            } catch (GetParameterException e){
                UserUtil.killFieldAttributes(request);
                request.setAttribute("status", "error.parameter.id.invalid");
                LOGGER.error(e.getMessage());
                return new ActionResult(ActionType.REDIRECT, "error" + UserUtil.fetchParameters(request));
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
        LOGGER.debug("isFormValid ...");
        return UserUtil.isUserNameValid(request)
                & UserUtil.isEmailValid(request)
                & UserUtil.isTableIdValid(request)
                & UserUtil.isRoleValid(request);
    }
}
