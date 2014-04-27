package kz.trei.acs.office.rfid;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlValue;

import kz.trei.acs.office.util.PropertyManager;

public final class RfidUID implements Serializable, Comparable<RfidUID> {
	private static final long serialVersionUID = 2727289459571896461L;
	static {
		PropertyManager.load("configure.properties");
	}
	@XmlValue
	private String rfidUID;

	public RfidUID() {
	}

	private RfidUID(String value) {
		this.rfidUID = value;
	}
	
	public static RfidUID create (){
		return new RfidUID();
	}

	public static boolean isValid(String uid) {
		String uidRegex = PropertyManager.getValue("rfid.uidRegex");
		Pattern uidPattern = Pattern.compile(uidRegex,
				Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
		Matcher tableIDMatcher = uidPattern.matcher(uid);
		return tableIDMatcher.matches();
	}

	public static RfidUID createUID(String uid) {
		if (isValid(uid)) {
			return new RfidUID(uid);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public String getRfidUID() {
		return rfidUID;
	}
		
	public void setValue(String value) {
		if (isValid(value)) {
			this.rfidUID = value;
		} else{
			throw new IllegalArgumentException(value +" is not valid UID");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rfidUID == null) ? 0 : rfidUID.hashCode());
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
		RfidUID other = (RfidUID) obj;
		if (rfidUID == null) {
			if (other.rfidUID != null)
				return false;
		} else if (!rfidUID.equals(other.rfidUID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RfidUID [UID = " + rfidUID + "]";
	}

	@Override
	public int compareTo(RfidUID anotherUID) {
		return rfidUID.compareTo(anotherUID.getRfidUID());
	}

}
