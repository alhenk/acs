package kz.trei.acs.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MonthType {

    JANUARY(1, "01"),
    FEBRUARY(2, "02"),
    MARCH(3, "03"),
    APRIL(4, "04"),
    MAY(5, "05"),
    JUNE(6, "06"),
    JULY(7, "07"),
    AUGUST(8, "08"),
    SEPTEMBER(9, "09"),
    OCTOBER(10, "10"),
    NOVEMBER(11, "11"),
    DECEMBER(12, "12");
    private static Map<Integer, MonthType> codeToMonthTypeMapping;
    private int monthNumber;
    private String monthDoubleDigitString;

    private MonthType(int monthNumber, String monthDoubleDigitString) {
        this.monthNumber = monthNumber;
        this.monthDoubleDigitString = monthDoubleDigitString;
    }

    public static List<String> getList() {
        List<String> types = new ArrayList<String>();
        for (MonthType type : MonthType.values()) {
            types.add(type.toString());
        }
        return types;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthDoubleDigitString() {
        return monthDoubleDigitString;
    }

    public void setMonthDoubleDigitString(String monthDoubleDigitString) {
        this.monthDoubleDigitString = monthDoubleDigitString;
    }

    public static MonthType select(int monthNumber) {
        if (codeToMonthTypeMapping == null) {
            initMapping();
        }
        return codeToMonthTypeMapping.get(monthNumber);
    }

    private static void initMapping() {
        codeToMonthTypeMapping = new HashMap<Integer, MonthType>();
        for (MonthType e : values()) {
            codeToMonthTypeMapping.put(e.monthNumber, e);
        }
    }
}
