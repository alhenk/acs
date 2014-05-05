package kz.trei.acs.action;


import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.user.User;
import kz.trei.acs.util.Administer;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signin implements Action {
    private static final Logger LOGGER = Logger.getLogger(Signin.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String securePassword;
        String salt;
        try {
            salt=Administer.getSalt();
            securePassword = Administer.getSecurePassword(password,salt);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to get secure password:" + e.getMessage());
//          session.setAttribute("error", "form.sign-in.wrong-password");
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
