package kz.trei.acs.action.reports;


import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AttendanceUtil {
    private static final Logger LOGGER = Logger.getLogger(AttendanceUtil.class);
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
}
