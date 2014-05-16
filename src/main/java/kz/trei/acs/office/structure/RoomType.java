package kz.trei.acs.office.structure;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "room", namespace ="http://www.trei.kz/attendance/tns")
@XmlEnum
public enum RoomType {
	@XmlEnumValue("ROOM101")
	ROOM101(101, "SECURITY"),
	@XmlEnumValue("ROOM102")
	ROOM102(102, "ASSEMBLY"),
	@XmlEnumValue("ROOM103")
	ROOM103(103, "WAREHOUSE"),
	@XmlEnumValue("ROOM104")
	ROOM104(104, "SWITCHBOARD_PREMISES"),
	@XmlEnumValue("ROOM105")
	ROOM105(105, "METROLOGICAL_SERVICE"),
	@XmlEnumValue("ROOM106")
	ROOM106(106, "OFFICE_CANTEEN"),
	@XmlEnumValue("ROOM107")
	ROOM107(107, "RESEARCH_AND_DEVELOPMENT"),
	@XmlEnumValue("ROOM108")
	ROOM108(108, "REST_ROOM"),
	@XmlEnumValue("ROOM109")
	ROOM109(109, "BOILER"),
	@XmlEnumValue("ROOM201")
	ROOM201(201, "RECEPTION"),
	@XmlEnumValue("ROOM202")
	ROOM202(202, "DIRECTOR"),
	@XmlEnumValue("ROOM203")
	ROOM203(203, "DEPUTIES"),
	@XmlEnumValue("ROOM204")
	ROOM204(204, "ENGINEERING"),
	@XmlEnumValue("ROOM205")
	ROOM205(205, "ACCOUNTANCY"),
	@XmlEnumValue("ROOM206")
	ROOM206(206, "SERVER"),
	@XmlEnumValue("ROOM207")
	ROOM207(207, "PRODUCTION"),
    @XmlEnumValue("DEFAULT")
    DEFAULT(0,"DEFAULT");

	private int roomNumber;
	private String roomName;

	private RoomType(int roomNumber, String roomName) {
		this.setRoomName(roomName);
		this.roomNumber = roomNumber;
	}

	private static Map<Integer, RoomType> codeToRoomTypeMapping;

	public RoomType select(int roomNumber) {
		if (codeToRoomTypeMapping == null) {
			initMapping();
		}
		return codeToRoomTypeMapping.get(roomNumber);
	}

	private void initMapping() {
		codeToRoomTypeMapping = new HashMap<Integer, RoomType>();
		for (RoomType e : values()) {
			codeToRoomTypeMapping.put(e.roomNumber, e);
		}
	}

	public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
