package kz.trei.acs.office.structure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "position", namespace ="http://www.trei.kz/attendance/tns")
@XmlEnum
public enum PositionType {
	@XmlEnumValue("GENERAL_DIRECTOR")
	GENERAL_DIRECTOR(1),
	@XmlEnumValue("COMMERCIAL_DIRECTOR")
	COMMERCIAL_DIRECTOR(2),
	@XmlEnumValue("DEPARTMENT_HEAD")
	DEPARTMENT_HEAD(3),
	@XmlEnumValue("SECRETARY_ADVISER")
	SECRETARY_ADVISER(4),
	@XmlEnumValue("TECHNICAL_DIRECTOR")
    TECHNICAL_DIRECTOR(2),
    @XmlEnumValue("DEPUTY_TECHNICAL_DIRECTOR")
    DEPUTY_TECHNICAL_DIRECTOR(3),
	@XmlEnumValue("SYSTEM_ADMINISTRATOR")
	SYSTEM_ADMINISTRATOR(4),
	@XmlEnumValue("CHIEF_ACCOUNTANT")
	CHIEF_ACCOUNTANT(2),
	@XmlEnumValue("DEPUTY_CHIEF_ACCOUNTANT")
	DEPUTY_CHIEF_ACCOUNTANT(3),
	@XmlEnumValue("ACCOUNTANT_MATERIALISTS")
	ACCOUNTANT_MATERIALISTS(4),
	@XmlEnumValue("HR_ADMINISTRATION")
	HR_ADMINISTRATION(4),
	@XmlEnumValue("WAREHOUSE_MANAGER")
	WAREHOUSE_MANAGER(4),
	@XmlEnumValue("EQUIPMENT_ENGINEER")
	EQUIPMENT_ENGINEER(4),
	@XmlEnumValue("ADMINISTRATIVE_SERVICE_UTILITY_MANAGER")
	ADMINISTRATIVE_SERVICE_UTILITY_MANAGER(3),
	@XmlEnumValue("DRIVER")
	DRIVER(4),
	@XmlEnumValue("SECURITY_GUARD")
	SECURITY_GUARD(4),
	@XmlEnumValue("JANITOR")
	JANITOR(5),
	@XmlEnumValue("ENGINEER")
	ENGINEER(5),
	@XmlEnumValue("SOFTWARE_ENGINEER")
	SOFTWARE_ENGINEER(5),
	@XmlEnumValue("SENIOR_SOFTWARE_ENGINEER")
	SENIOR_SOFTWARE_ENGINEER(5),
	@XmlEnumValue("LEAD_SOFTWARE_ENGINEER")
	LEAD_SOFTWARE_ENGINEER(4),
	@XmlEnumValue("COMMISSIONING_ENGINEER")
	COMMISSIONING_ENGINEER(5),
	@XmlEnumValue("APCS_ENGINEER")
	APCS_ENGINEER(5),
	@XmlEnumValue("LEAD_APCS_ENGINEER")
	LEAD_APCS_ENGINEER(5),
	@XmlEnumValue("LEAD_DESIGNER")
	LEAD_DESIGNER(4),
	@XmlEnumValue("DESIGN_ENGINEER")
	DESIGN_ENGINEER(5),
	@XmlEnumValue("ELECTRONICS_ENGINEER")
	ELECTRONICS_ENGINEER(5),
	@XmlEnumValue("SENIOR_ELECTRONICS_ENGINEER")
	SENIOR_ELECTRONICS_ENGINEER(5),
	@XmlEnumValue("CHIEF_METROLOGIST")
	CHIEF_METROLOGIST(3),
	@XmlEnumValue("ENGINEER_METROLOGIST")
	ENGINEER_METROLOGIST(5),
	@XmlEnumValue("CONSTRUCTION_ELECTRICIAN")
	CONSTRUCTION_ELECTRICIAN(5),
    @XmlEnumValue("DEFAULT")
	DEFAULT(5);

    private int hierarchy;

    private PositionType(int hierarchy){
        this.hierarchy=hierarchy;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }
}
