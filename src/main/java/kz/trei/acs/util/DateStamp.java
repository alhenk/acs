package kz.trei.acs.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateStamp", namespace ="http://www.trei.kz/attendance/tns")
public class DateStamp implements Serializable, Comparable<DateStamp> {
	private static final long serialVersionUID = -8961625269572879384L;
	private static final Logger LOGGER = Logger.getLogger(DateStamp.class);
	private static SimpleDateFormat format;
	static {
		PropertyManager.load("configure.properties");
	}
	@XmlValue
	private String date;

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
		if (isValid(date)) {
			return new DateStamp(date);
		}
		LOGGER.error(date + " is not a valid timestamp");
		return null;
	}

	/**
	 * Default constructor gets the current Date
	 */
	public DateStamp() {
		date = toDateStampString(new Date());
	}

	private DateStamp(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public static boolean isValid(String date) {
		String pattern = PropertyManager
				.getValue("util.datetime.dateStampFormat");
		try {
			format = new SimpleDateFormat(pattern);
			format.setLenient(false);
			format.parse(date);
		} catch (ParseException e) {
			LOGGER.error(e);
			return false;
		} catch (IllegalArgumentException e) {
			LOGGER.error(e);
			return false;
		} catch (NullPointerException e) {
			LOGGER.error(e);
			return false;
		}
		return true;
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
