package kz.trei.acs.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeStamp", namespace ="http://www.trei.kz/attendance/tns")
public class TimeStamp implements Serializable, Comparable<TimeStamp> {
	private static final long serialVersionUID = -2891638740457501189L;
	private static final Logger LOGGER = Logger.getLogger(TimeStamp.class);
	private static SimpleDateFormat format;
	static {
		PropertyManager.load("configure.properties");
	}

	private String time;

	public TimeStamp() {
		time = toTimeStampString(new Date());
	}

	private TimeStamp(String time) {
		this.time = time;
	}

	public static TimeStamp create(Date date) {
		return new TimeStamp(toTimeStampString(date));
	}

	public static TimeStamp create(String time) {
		if (isValid(time)) {
			return new TimeStamp(time);
		}
		LOGGER.error(time + " is not a valid timestamp");
		return null;
	}

	public static boolean isValid(String time) {
		String pattern = PropertyManager
				.getValue("util.datetime.timeStampFormat");
		try {
			format = new SimpleDateFormat(pattern);
			format.setLenient(false);
			format.parse(time);
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

	private static String toTimeStampString(Date date) {
		String pattern = PropertyManager
				.getValue("util.datetime.timeStampFormat");
		SimpleDateFormat timeStampFormat = new SimpleDateFormat(pattern);
		return timeStampFormat.format(date);
	}

	public String getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		TimeStamp other = (TimeStamp) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeStamp [time=" + time + "]";
	}

	@Override
	public int compareTo(TimeStamp anotherTime) {
		return time.compareTo(anotherTime.getTime());
	}

}
