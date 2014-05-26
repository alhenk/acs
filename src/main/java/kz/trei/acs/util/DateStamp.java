package kz.trei.acs.util;

import kz.trei.acs.exception.DateStampException;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStamp implements Serializable, Comparable<DateStamp> {
    private static final long serialVersionUID = -8961625269572879384L;
    private static final Logger LOGGER = Logger.getLogger(DateStamp.class);
    private static SimpleDateFormat dateFormat;

    static {
        PropertyManager.load("configure.properties");
    }

    private String date;

    /**
     * Default constructor gets the current Date
     */
    public DateStamp() {
        date = toDateStampString(new Date());
    }

    private DateStamp(String date) {
        this.date = date;
    }

    private static String toDateStampString(Date date) {
        String pattern = PropertyManager
                .getValue("util.datetime.dateStampFormat");
        SimpleDateFormat dateStampFormat = new SimpleDateFormat(pattern);
        return dateStampFormat.format(date);
    }

    public static DateStamp create(Date date) {
        return new DateStamp(toDateStampString(date));
    }

    public static DateStamp create(String date) {
        if (isDateStampValid(date)) {
            return new DateStamp(date);
        }
        LOGGER.error(date + " is not a valid date stamp");
        throw new DateStampException(date + " is not a valid date stamp");
    }

    public static DateStamp createEmptyDate() {
        return new DateStamp("");
    }

    public static String getCurrentYear() {
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        return year.format(date);
    }

    public static String getCurrentMonth() {
        Date date = new Date();
        SimpleDateFormat month = new SimpleDateFormat("MM");
        return MonthType.select(Integer.valueOf(month.format(date))).toString().toUpperCase();
    }

    public static boolean isDateStampValid(String date) {
        String pattern = PropertyManager
                .getValue("util.datetime.dateStampFormat");
        if (date == null || date.isEmpty()) {
            return false;
        }
        try {
            dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(true);
            Date parsedDate=dateFormat.parse(date);
            dateFormat = new SimpleDateFormat(pattern);
            String stringDate = "";
            stringDate = dateFormat.format(parsedDate);
            if (!stringDate.equals(date)) {
                LOGGER.error("Not a date");
                return false;
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            return false;
        } catch (NullPointerException e) {
            LOGGER.error(e);
            return false;
        } catch (ParseException e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (isDateStampValid(date)) {
            this.date = date;
        }
        LOGGER.error(date + " is not a valid date stamp");
        throw new DateStampException(date + " is not a valid date stamp");
    }

    @Override
    public String toString() {
        return "DateStamp [" + date + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateStamp other = (DateStamp) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    @Override
    public int compareTo(DateStamp anotherDate) {
        return date.compareTo(anotherDate.getDate());
    }
}
