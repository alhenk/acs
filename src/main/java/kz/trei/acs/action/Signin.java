package kz.trei.acs.action;


import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PasswordHash;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Signin implements Action {
    private static final Logger LOGGER = Logger.getLogger(Signin.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String securePassword;
        String hash ="1000:241c4899116ae9c374d4d78711a27e49643196a2b9d70c87:2e9b4d7a67e449fb45777e8a214a1fbc2fcbff731c20cb01";
        try {
            securePassword = PasswordHash.createHash(password);
            boolean isPasswordValid = PasswordHash.validatePassword(password,hash);
            LOGGER.debug("Secure Password :" + securePassword);
            LOGGER.debug("Is the password valid? :" + isPasswordValid);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to get secure password:" + e.getMessage());
            session.setAttribute("message", "form.sign-in.secure-password-error");
            return new ActionResult(ActionType.FORWARD, "exception");
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Unable to get secure password:" + e.getMessage());
            session.setAttribute("message", "form.sign-in.secure-password-error");
            return new ActionResult(ActionType.FORWARD, "exception");
        }
        User user = null;
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        try {
            user = userDao.find(username, password);
        } catch (Exception e) {
            session.setAttribute("error", "form.sign-in.wrong-password");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        if (user == null) {
            session.setAttribute("error", "form.sign-in.wrong-password");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        session.setAttribute("user", user);
        return new ActionResult(ActionType.REDIRECT, "main");
    }
}
