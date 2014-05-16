package kz.trei.acs.office.structure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "department", namespace = "http://www.trei.kz/attendance/tns")
@XmlEnum
public enum DepartmentType {
    @XmlEnumValue("ACCOUNTANCY")
    ACCOUNTANCY,
    @XmlEnumValue("RESEARCH_AND_DEVELOPMENT")
    RESEARCH_AND_DEVELOPMENT,
    @XmlEnumValue("COMMERCIAL")
    COMMERCIAL,
    @XmlEnumValue("WAREHOUSE")
    WAREHOUSE,
    @XmlEnumValue("ASSEMBLY")
    ASSEMBLY,
    @XmlEnumValue("PRODUCTION")
    PRODUCTION,
    @XmlEnumValue("ENGINEERING")
    ENGINEERING,
    @XmlEnumValue("METROLOGICAL_SERVICE")
    METROLOGICAL_SERVICE,
    @XmlEnumValue("VERIFICATION_LABORATORY")
    VERIFICATION_LABORATORY,
    @XmlEnumValue("ADMINISTRATIVE_SERVICE_UTILITY")
    ADMINISTRATIVE_SERVICE_UTILITY,
    @XmlEnumValue("DEFAULT")
    DEFAULT;

    public static List<String> getList() {
        List<String> types = new ArrayList<String>();
        for (DepartmentType type : DepartmentType.values()) {
            types.add(type.toString());
        }
        return types;
    }
}
