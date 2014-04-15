package kaz.trei.asc.action;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class SetLanguage implements Action  {
    private static final Logger LOGGER = Logger.getLogger(SetLanguage.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        String country="";
        String language = request.getParameter("language");
        if(language.equalsIgnoreCase("en")){
            country="US";
        } else if (language.equalsIgnoreCase("ru")){
            country="RU";
        } else {
            language ="en";
            country = "US";
        }

        Locale locale = new Locale(language,country);
        request.getSession().setAttribute("locale", locale);
        LOGGER.debug("language = " +language +"; country = " + country);
        LOGGER.debug("locale = " +locale);
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }
}