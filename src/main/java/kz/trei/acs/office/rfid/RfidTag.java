package kz.trei.acs.office.rfid;


import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class RfidTag implements Serializable, Comparable<RfidTag> {
    private static final long serialVersionUID = -1380395087317256237L;
    private static final Logger LOGGER = Logger.getLogger(RfidTag.class);

    static {
        PropertyManager.load("configure.properties");
    }

    private long id;
    private String uid;
    private RfidType type;
    private ProtocolType protocol;
    private Issue issue;

    public RfidTag() {
        this.id = 1L;
        this.uid = "00000000";
        this.type = RfidType.DEFAULT;
        this.protocol = ProtocolType.DEFAULT;
    }

    private RfidTag(String uid, RfidType type, ProtocolType protocol,
                    Issue issue) {
        this.id = 1L;
        this.uid = uid;
        this.type = type;
        this.protocol = protocol;
        this.issue = issue;
    }

    private RfidTag(Builder builder) {
        this.id = builder.id;
        this.uid = builder.uid;
        this.type = builder.type;
        this.protocol = builder.protocol;
        this.issue = builder.issue;
    }

    public static boolean isUidValid(String uid) {
        if(uid==null || uid.isEmpty()){
            return false;
        }
        String uidRegex = PropertyManager.getValue("rfid.uidRegex");
        Pattern uidPattern;
        Matcher uidMatcher;
        try {
            uidPattern = Pattern.compile(uidRegex, Pattern.CASE_INSENSITIVE);
            uidMatcher = uidPattern.matcher(uid);
        } catch (PatternSyntaxException e) {
            LOGGER.error("UID compile exception");
            return false;
        } catch (IllegalArgumentException e){
            LOGGER.error("UID matcher exception");
            return false;
        }
        return uidMatcher.matches();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        if (isUidValid(uid)) {
            this.uid = uid;
        } else {
            throw new UidFormatException("UID format is not valid");
        }
    }

    public RfidType getType() {
        return type;
    }

    public void setType(RfidType type) {
        this.type = type;
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void setEmptyUid() {
        this.uid = "00000000";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RfidTag)) return false;

        RfidTag rfidTag = (RfidTag) o;

        if (id != rfidTag.id) return false;
        if (!issue.equals(rfidTag.issue)) return false;
        if (protocol != rfidTag.protocol) return false;
        if (type != rfidTag.type) return false;
        if (!uid.equals(rfidTag.uid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + uid.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + protocol.hashCode();
        result = 31 * result + issue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RfidTag [uid=" + uid + ", type=" + type + "]";
    }

    @Override
    public int compareTo(RfidTag anotherTag) {
        String anotherUid = anotherTag.uid;
        String thisUid = this.uid;
        return thisUid.compareTo(anotherUid);
    }

    public static class Builder {
        private long id;
        private String uid;
        private RfidType type;
        private ProtocolType protocol;
        private Issue issue;

        public Builder() {
            this.id = 1L;
        }

        public Builder(String uid) {
            this.uid = uid;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder uid(String uid) {
            if (isUidValid(uid)) {
                this.uid = uid;
            } else {
                throw new UidFormatException("UID format is not valid");
            }
            return this;
        }

        public Builder type(RfidType type) {
            this.type = type;
            return this;
        }

        public Builder protocol(ProtocolType protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder issue(Issue issue) {
            this.issue = issue;
            return this;
        }

        public RfidTag build() {
            return new RfidTag(this);
        }
    }
}
