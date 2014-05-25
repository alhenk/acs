package kz.trei.acs.user;

import org.apache.log4j.Logger;

import java.util.Comparator;


public class UserComparator implements Comparator<User> {
    private static final Logger LOGGER = Logger.getLogger(UserComparator.class);
    private CompareType type;

    public UserComparator(CompareType type) {
        this.type = type;
    }

    @Override
    public int compare(User u1, User u2) {
        if (u1 == null && u2 == null) return 0;
        if (u1 == null && u2 != null) return -1;
        if (u1 != null && u2 == null) return 1;
        switch (type) {
            case ID:
                return new Long(u1.getId()).compareTo(u2.getId());
            case USER_NAME:
                return u1.getUsername().toLowerCase().compareTo(u2.getUsername().toLowerCase());
            case EMAIL:
                return u1.getEmail().toLowerCase().compareTo(u2.getEmail().toLowerCase());
            case ROLE:
                return u1.getRole().compareTo(u2.getRole());
            case TABLE_ID:
                if (u1.getAccount1C() == null && u2.getAccount1C() == null) return 0;
                if (u1.getAccount1C() == null && u2.getAccount1C() != null) return -1;
                if (u1.getAccount1C() != null && u2.getAccount1C() == null) return 1;
                return u1.getAccount1C().getTableId().compareTo(u2.getAccount1C().getTableId());
        }
        return 0;
    }

    public enum CompareType {
        ID, USER_NAME, EMAIL, ROLE, TABLE_ID;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
