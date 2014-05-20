package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.exception.GetParameterException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteUser.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("execute ...");
        response.setCharacterEncoding("UTF-8");
        long id;
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        try {
            id = UserUtil.takeId(request);
            if (id == 0 || id == 1) {
                throw new GetParameterException("trying to delete the read-only record");
            }
            userDao.delete(id);
        } catch (DaoException e) {
            request.setAttribute("status", "delete.user.fail");
            LOGGER.error("... DAO delete error " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "user-list" + UserUtil.fetchParameters(request));
        } catch (GetParameterException e) {
            request.setAttribute("status", "error.parameter.id.invalid");
            LOGGER.error(e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "user-list" + UserUtil.fetchParameters(request));
        }
        request.setAttribute("status", "delete.user.success");
        LOGGER.debug("... the user is deleted successfully");
        return new ActionResult(ActionType.REDIRECT, "user-list" + UserUtil.fetchParameters(request));
    }
}
