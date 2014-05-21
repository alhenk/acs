package kz.trei.acs.action.reports;


import kz.trei.acs.exception.DateStampException;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.MonthType;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String takeYear(HttpServletRequest request) {
        LOGGER.debug("takeYear ...");
        SimpleDateFormat format;
        String year;
        Date date;
        try {
            year = request.getParameter("year");
            format = new SimpleDateFormat("yyyy");
            format.setLenient(false);
            date = format.parse(year);
            year = format.format(date);
        } catch (ParseException e) {
            LOGGER.debug("Assigned current year due to illegal argument : " + e.getMessage());
            year = DateStamp.getCurrentYear();
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned current year due to illegal argument : " + e.getMessage());
            year = DateStamp.getCurrentYear();
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned current year due to null value : " + e.getMessage());
            year = DateStamp.getCurrentYear();
        }
        LOGGER.debug("... " + year);
        return year;
    }

    public static String takeMonth(HttpServletRequest request) {
        LOGGER.debug("takeMonth ...");
        MonthType month;
        try {
            month = MonthType.valueOf(request.getParameter("month"));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Assigned current month due to illegal argument : " + e.getMessage());
            month = MonthType.valueOf(DateStamp.getCurrentMonth());
        } catch (NullPointerException e) {
            LOGGER.debug("Assigned current month due to null value : " + e.getMessage());
            month = MonthType.valueOf(DateStamp.getCurrentMonth());
        }
        LOGGER.debug(month);
        return month.toString().toUpperCase();
    }

    public static DateStamp takeReportDate(HttpServletRequest request) {
            LOGGER.debug("takeReportDate ...");
            DateStamp date;
            try {
                date = DateStamp.create(request.getParameter("report-date"));
            } catch (DateStampException e) {
                date = new DateStamp();
                LOGGER.debug("Assigned current date due to exception: " + e.getMessage());
            }
            LOGGER.debug(date.getDate());
            return date;
    }
}
