package kz.trei.acs.office.rfid;


import org.apache.log4j.Logger;

import java.util.Comparator;

public class RfidComparator implements Comparator<RfidTag> {
    private static final Logger LOGGER = Logger.getLogger(RfidComparator.class);
    private CompareType type;

    public RfidComparator(CompareType type) {
        this.type = type;
    }

    @Override
    public int compare(RfidTag r1, RfidTag r2) {
        switch (type) {
            case ID:
                return new Long(r1.getId()).compareTo(r2.getId());
            case UID:
                return r1.getUid().compareTo(r2.getUid());
            case TYPE:
                return r1.getType().compareTo(r2.getType());
            case PROTOCOL:
                return r1.getProtocol().compareTo(r2.getProtocol());
            case ISSUE_DATE:
                return r1.getIssue().getIssueDate().getDate().compareTo(r2.getIssue().getIssueDate().getDate());
            case EXPIRATION_DATE:
                return r1.getIssue().getExpirationDate().getDate().compareTo(r2.getIssue().getExpirationDate().getDate());
            default:
                return new Long(r1.getId()).compareTo(r2.getId());
        }
    }

    public enum CompareType {
        ID, UID, TYPE, PROTOCOL, ISSUE_DATE, EXPIRATION_DATE;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
