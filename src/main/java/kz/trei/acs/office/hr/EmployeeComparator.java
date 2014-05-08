package kz.trei.acs.office.hr;

import java.util.Comparator;

/**
 * Created by Admin on 08.05.14.
 */
public class EmployeeComparator implements Comparator<Person> {
    private CompareType type;

    public EmployeeComparator(CompareType type) {
        this.type = type;
    }

    @Override
    public int compare(Person e1, Person e2) {
        switch (type) {
            case ID:
                return new Long(((Employee) e1).getId()).compareTo(((Employee) e2).getId());
            case FIRST_NAME:
                return e1.getFirstName().compareTo(e2.getFirstName());
            case LAST_NAME:
                return e1.getLastName().compareTo(e2.getLastName());
            case BIRTH_DATE:
                return e1.getBirthDate().compareTo(e2.getBirthDate());
            case ROOM:
                return ((Employee) e1).getRoom().compareTo(((Employee) e2).getRoom());
            case TABLE_ID:
                return ((Employee) e1).getTableIdValue().compareTo(((Employee) e2).getTableIdValue());
            case UID:
                return ((Employee) e1).getAccount1C().getTableId().compareTo(((Employee) e2).getAccount1C().getTableId());
            default:
                return new Long(((Employee) e1).getId()).compareTo(((Employee) e2).getId());
        }
    }

    public enum CompareType {
        ID, FIRST_NAME, LAST_NAME,BIRTH_DATE, ROOM, TABLE_ID, UID;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
