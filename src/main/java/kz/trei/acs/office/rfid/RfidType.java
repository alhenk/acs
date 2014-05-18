package kz.trei.acs.office.rfid;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "rfidType", namespace = "http://www.trei.kz/attendance/tns")
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

    public static List<String> getList() {
        List<String> types = new ArrayList<String>();
        for (RfidType type : RfidType.values()) {
            types.add(type.toString());
        }
        return types;
    }
}
