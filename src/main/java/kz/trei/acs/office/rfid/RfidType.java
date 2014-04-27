package kz.trei.acs.office.rfid;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "rfidType", namespace ="http://www.trei.kz/attendance/tns")
@XmlEnum
public enum RfidType {
	@XmlEnumValue("CARD")
	CARD,
	@XmlEnumValue("KEYFOB")
	KEYFOB,
	@XmlEnumValue("STICKER")
	STICKER,
	@XmlEnumValue("DEFAULT")
	DEFAULT;
}
