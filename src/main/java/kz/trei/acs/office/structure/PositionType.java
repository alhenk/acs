package kz.trei.acs.office.structure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * List of job positions
 * hierarchy - a non-strict list order
 */
@XmlType(name = "position", namespace = "http://www.trei.kz/attendance/tns")
@XmlEnum
public enum PositionType {
    @XmlEnumValue("GENERAL_DIRECTOR")
    GENERAL_DIRECTOR(1, "GENERAL_DIRECTOR"),
    @XmlEnumValue("COMMERCIAL_DIRECTOR")
    COMMERCIAL_DIRECTOR(2, "COMMERCIAL_DIRECTOR"),
    @XmlEnumValue("CHIEF_ACCOUNTANT")
    CHIEF_ACCOUNTANT(3, "CHIEF_ACCOUNTANT"),
    @XmlEnumValue("TECHNICAL_DIRECTOR")
    TECHNICAL_DIRECTOR(4, "TECHNICAL_DIRECTOR"),
    @XmlEnumValue("DEPUTY_TECHNICAL_DIRECTOR")
    DEPUTY_TECHNICAL_DIRECTOR(5, "DEPUTY_TECHNICAL_DIRECTOR"),
    @XmlEnumValue("DEPUTY_CHIEF_ACCOUNTANT")
    DEPUTY_CHIEF_ACCOUNTANT(5, "DEPUTY_CHIEF_ACCOUNTANT"),
    @XmlEnumValue("CHIEF_METROLOGIST")
    CHIEF_METROLOGIST(5, "CHIEF_METROLOGIST"),
    @XmlEnumValue("DEPARTMENT_HEAD")
    DEPARTMENT_HEAD(6, "DEPARTMENT_HEAD"),
    @XmlEnumValue("SYSTEM_ADMINISTRATOR")
    SYSTEM_ADMINISTRATOR(7, "SYSTEM_ADMINISTRATOR"),
    @XmlEnumValue("SECRETARY_ADVISER")
    SECRETARY_ADVISER(7, "SECRETARY_ADVISER"),
    @XmlEnumValue("DRIVER")
    DRIVER(8, "DRIVER"),
    @XmlEnumValue("SECURITY_GUARD")
    SECURITY_GUARD(8, "SECURITY_GUARD"),
    @XmlEnumValue("ACCOUNTANT_MATERIALISTS")
    ACCOUNTANT_MATERIALISTS(9, "ACCOUNTANT_MATERIALISTS"),
    @XmlEnumValue("HR_ADMINISTRATION")
    HR_ADMINISTRATION(9, "HR_ADMINISTRATION"),
    @XmlEnumValue("ADMINISTRATIVE_SERVICE_UTILITY_MANAGER")
    ADMINISTRATIVE_SERVICE_UTILITY_MANAGER(9, "ADMINISTRATIVE_SERVICE_UTILITY_MANAGER"),
    @XmlEnumValue("WAREHOUSE_MANAGER")
    WAREHOUSE_MANAGER(9, "WAREHOUSE_MANAGER"),
    @XmlEnumValue("EQUIPMENT_ENGINEER")
    EQUIPMENT_ENGINEER(12, "EQUIPMENT_ENGINEER"),
    @XmlEnumValue("LEAD_APCS_ENGINEER")
    LEAD_APCS_ENGINEER(10, ""),
    @XmlEnumValue("LEAD_DESIGNER")
    LEAD_DESIGNER(10, "LEAD_DESIGNER"),
    @XmlEnumValue("LEAD_SOFTWARE_ENGINEER")
    LEAD_SOFTWARE_ENGINEER(10, "LEAD_SOFTWARE_ENGINEER"),
    @XmlEnumValue("SENIOR_SOFTWARE_ENGINEER")
    SENIOR_SOFTWARE_ENGINEER(11, "SENIOR_SOFTWARE_ENGINEER"),
    @XmlEnumValue("SENIOR_ELECTRONICS_ENGINEER")
    SENIOR_ELECTRONICS_ENGINEER(11, "SENIOR_ELECTRONICS_ENGINEER"),
    @XmlEnumValue("ENGINEER")
    ENGINEER(12, "ENGINEER"),
    @XmlEnumValue("SOFTWARE_ENGINEER")
    SOFTWARE_ENGINEER(12, "SOFTWARE_ENGINEER"),
    @XmlEnumValue("APCS_ENGINEER")
    APCS_ENGINEER(12, "APCS_ENGINEER"),
    @XmlEnumValue("COMMISSIONING_ENGINEER")
    COMMISSIONING_ENGINEER(12, "COMMISSIONING_ENGINEER"),
    @XmlEnumValue("DESIGN_ENGINEER")
    DESIGN_ENGINEER(12, "DESIGN_ENGINEER"),
    @XmlEnumValue("ELECTRONICS_ENGINEER")
    ELECTRONICS_ENGINEER(12, "ELECTRONICS_ENGINEER"),
    @XmlEnumValue("ENGINEER_METROLOGIST")
    ENGINEER_METROLOGIST(12, "ENGINEER_METROLOGIST"),
    @XmlEnumValue("CONSTRUCTION_ELECTRICIAN")
    CONSTRUCTION_ELECTRICIAN(13, "CONSTRUCTION_ELECTRICIAN"),
    @XmlEnumValue("JANITOR")
    JANITOR(14, "JANITOR"),
    @XmlEnumValue("DEFAULT")
    DEFAULT(15, "DEFAULT");
    private int hierarchy;
    private String position;

    private PositionType(int hierarchy, String position) {
        this.hierarchy = hierarchy;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public String toString() {
        return position.toString().trim();
    }
}
