package kz.trei.acs.office.structure;


import kz.trei.acs.util.PropertyManager;

import javax.xml.bind.annotation.XmlValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Table ID assigned to employee accordingly to 1C account data base
 *
 * @throws IllegalArgumentException
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
    public static boolean isValid(String id) {
        String tableIDregex = PropertyManager
                .getValue("structure.tableIDRegex");
        Pattern tableIDPattern = Pattern.compile(tableIDregex,
                Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher tableIDMatcher = tableIDPattern.matcher(id);
        return tableIDMatcher.matches();
    }

    /**
     * Static fabric method with random ID
     */
    public static Account1C createRandomId() {
        StringBuilder id = new StringBuilder();
        long number = 1L + (long) (Math.random() * 9999L);
        id.append(PropertyManager.getValue("structure.tablePrefix"));
        return new Account1C(id.append(String.format("%08d", number)).toString());
    }

    /**
     * Static fabric method with regex verification
     *
     * @throws IllegalArgumentException
     */
    public static Account1C createId(String id) {
        if (isValid(id)) {
            return new Account1C(id);
        } else {
            throw new Account1CException("does not match tableIDRegex ");
        }
    }

    /**
     * Used for assign default table ID in case of Account1CException
     *
     */
    public static Account1C defaultId(){
        return new Account1C(DEFAULT_TABLE_ID);
    }

    /**
     * Getter
     */

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
