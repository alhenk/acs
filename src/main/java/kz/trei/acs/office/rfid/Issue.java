package kz.trei.acs.office.rfid;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import kz.trei.acs.office.util.DateStamp;
import kz.trei.acs.office.util.PropertyManager;

//@XmlRootElement(name = "issue", namespace ="http://www.trei.kz/attendance/tns")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "issueDate", "expirationDate"})
public class Issue implements Serializable, Comparable<Issue> {
	private static final long serialVersionUID = -7410469239152467286L;
	static {
		PropertyManager.load("configure.properties");
	}
	@XmlElement(required = true)
	private DateStamp issueDate;
	@XmlElement(required = true)
	private DateStamp expirationDate;

	/**
	 * Default constructor sets issue to current date and expiration to default
	 * value in configure properties
	 */
	public Issue() {
		Integer defaultExpirationInMonths = new Integer(
				PropertyManager.getValue("issue.defaultExpirationInMonths"));
		Calendar calendar = Calendar.getInstance();
		Date currentDate = new Date();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MONTH, defaultExpirationInMonths);
		this.issueDate = DateStamp.create(currentDate);
		this.expirationDate = DateStamp.create(calendar.getTime());
	}

	public Issue(DateStamp issueDate, DateStamp expirationDate) {
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
	}

	public static class Builder {
		private DateStamp issueDate;
		private DateStamp expirationDate;

		public Builder setIssueDate(DateStamp issueDate) {
			this.issueDate = issueDate;
			return this;
		}

		public Builder setExpirationDate(DateStamp experationDate) {
			this.expirationDate = experationDate;
			return this;
		}

		public Issue build() {
			return new Issue(issueDate, expirationDate);
		}
	}

	public DateStamp getIssueDate() {
		return issueDate;
	}

	
	public void setIssueDate(DateStamp issueDate) {
		this.issueDate = issueDate;
	}

	public DateStamp getExpirationDate() {
		return expirationDate;
	}

	
	public void setExpirationDate(DateStamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result
				+ ((issueDate == null) ? 0 : issueDate.hashCode());
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
		Issue other = (Issue) obj;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (issueDate == null) {
			if (other.issueDate != null)
				return false;
		} else if (!issueDate.equals(other.issueDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Issue [issueDate = " + issueDate.getDate() + ", expirationDate = "
				+ expirationDate.getDate() + "]";
	}

	@Override
	public int compareTo(Issue anotherIssue) {
		return issueDate.compareTo(anotherIssue.getIssueDate());
	}

}
