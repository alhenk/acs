package kz.trei.acs.office.structure;


import kz.trei.acs.util.PropertyManager;

import javax.xml.bind.annotation.XmlValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Table ID assigned to employee accordingly to 1C account data base
 *
 * @throws Account1CException
 */
//@XmlType(name = "tableId", namespace ="http://www.trei.kz/attendance/tns")
public final class Account1C {
    public static final String DEFAULT_TABLE_ID = "XX00000000";

    static {
        PropertyManager.load("configure.properties");
    }

    /**
     * Table ID number (example: KK00000001)
     */
    @XmlValue
    private String tableId;

    /**
     * Default constructor
     */
    public Account1C() {
    }

    /**
     * Constructor with direct assignment.
     */
    private Account1C(String id) {
        this.tableId = id;
    }

    /**
     * regex id verification
     */
    public static boolean isValid(String tableId) {
        boolean isValid;
        Matcher tableIdMatcher;
        Pattern tableIdPattern;
        if (tableId == null || tableId.isEmpty()) {
            isValid = false;
        } else {
            String tableIdregex = PropertyManager.getValue("structure.tableIDRegex");
            tableIdPattern = Pattern.compile(tableIdregex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            isValid = tableIdMatcher.matches();
        }
        return isValid;
    }

    /**
     * Static fabric method with random ID
     */
    public static Account1C buildAccount1CWithRandomId() {
        StringBuilder id = new StringBuilder();
        long number = 1L + (long) (Math.random() * 9999L);
        id.append(PropertyManager.getValue("structure.tablePrefix"));
        return new Account1C(id.append(String.format("%08d", number)).toString());
    }

    /**
     * Static fabric method with regex verification
     *
     * @throws Account1CException
     */
    public static Account1C buildAccount1C(String tableId) {
        boolean isIdValid;
        try {
            isIdValid = isValid(tableId);
        } catch (PatternSyntaxException e) {
            throw new Account1CException("Pattern expression syntax is invalid");
        } catch (IllegalArgumentException e) {
            throw new Account1CException("Regex pattern flags doesn't corresponds to the defined ones");
        }
        if (isIdValid) {
            return new Account1C(tableId);
        } else {
            throw new Account1CException("Does not match tableIDRegex ");
        }
    }

    /**
     * Used for assign default table ID in case of Account1CException
     */
    public static Account1C defaultAccount1C() {
        return new Account1C(DEFAULT_TABLE_ID);
    }

    public String getTableId() {
        return tableId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
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
        Account1C other = (Account1C) obj;
        if (tableId == null) {
            if (other.tableId != null)
                return false;
        } else if (!tableId.equals(other.tableId))
            return false;
        return true;
    }

}
