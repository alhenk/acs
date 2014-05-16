package kz.trei.acs.office.rfid;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "protocol", namespace ="http://www.trei.kz/attendance/tns")
@XmlEnum
public enum ProtocolType {
	@XmlEnumValue("ISO14443A")
	ISO14443A,
	@XmlEnumValue("ISO14443B")
	ISO14443B,
	@XmlEnumValue("ISO15693")
	ISO15693, 
	@XmlEnumValue("DEFAULT")
	DEFAULT;

    public static List<String> getList() {
        List<String> types = new ArrayList<String>();
        for (ProtocolType type : ProtocolType.values()) {
            types.add(type.toString());
        }
        return types;
    }
}
