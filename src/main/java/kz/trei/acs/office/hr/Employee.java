package kz.trei.acs.office.hr;

import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.office.util.DateStamp;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "employee", namespace = "http://www.trei.kz/attendance/tns")
@XmlType(propOrder = {"position", "department", "room", "rfidTag"})
public class Employee extends Person {
    private static final long serialVersionUID = -8363247132437924285L;
    private long id;
    @XmlElement(name = "position", required = true)
    @XmlElementWrapper(name = "positions", required = true)
    private Set<PositionType> position;
    @XmlElement(name = "department", required = true)
    private DepartmentType department;
    @XmlElement(name = "room", required = true)
    @XmlElementWrapper(name = "rooms")
    private Set<RoomType> room;
    @XmlAttribute(name = "id", required = true)
    private Account1C account1C;
    @XmlElement(name = "rfidTag", required = true)
    private RfidTag rfidTag;

    public Employee() {
        this.room = new HashSet<RoomType>();
        this.position = new HashSet<PositionType>();
    }

    public Employee(Account1C account1C) {
        this.account1C = account1C;
        this.room = new HashSet<RoomType>();
        this.position = new HashSet<PositionType>();
    }

    public Employee(Set<PositionType> position, DepartmentType department,
                    Set<RoomType> room, Account1C account1C, RfidTag tag) {
        this();
        this.position = position;
        this.department = department;
        this.room = room;
        this.account1C = account1C;
        this.rfidTag = tag;
    }

    public Employee(Builder builder) {
        this.id = builder.id;
        this.position = builder.position;
        this.department = builder.department;
        this.room = builder.room;
        this.account1C = builder.account1C;
        this.rfidTag = builder.rfidTag;
        this.setFirstName(builder.firstName);
        this.setLastName(builder.lastName);
        this.setPatronym(builder.patronym);
        this.setBirthday(builder.birthDate);
    }

    public Set<PositionType> getPosition() {
        return position;
    }

    public void setPosition(Set<PositionType> position) {
        this.position = position;
    }

    public void addRoom(RoomType room) {
        this.room.add(room);
    }

    public void removeRoom(RoomType room) {
        this.room.remove(room);
    }

    public void addPosition(PositionType position) {
        this.position.add(position);
    }

    public void removePosition(PositionType position) {
        this.position.remove(position);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DepartmentType getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentType department) {
        this.department = department;
    }

    public Set<RoomType> getRoom() {
        return room;
    }

    public void setRoom(Set<RoomType> room) {
        this.room = room;
    }

    public Account1C getAccount1C() {
        return account1C;
    }

    public void setAccount1C(Account1C account1C) {
        this.account1C = account1C;
    }

    public String getTableIdValue() {
        return account1C.getTableId();
    }

    public void setTableId(String tableId) {
        this.account1C = Account1C.createId(tableId);
    }

    public RfidTag getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(RfidTag tag) {
        this.rfidTag = tag;
    }

    @Override
    public String toString() {
        return "Employee ["
                + this.getLastName()
                + ", uid = "
                + ((rfidTag != null && rfidTag.getRfidUid() != null) ? rfidTag
                .getRfidUid().getValue() : "null") + ", tableID = "
                + ((this.account1C != null) ? this.account1C.getTableId() : "null")
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((department == null) ? 0 : department.hashCode());
        result = prime * result
                + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((account1C == null) ? 0 : account1C.hashCode());
        result = prime * result + ((rfidTag == null) ? 0 : rfidTag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (department != other.department)
            return false;
        if (position != other.position)
            return false;
        if (room != other.room)
            return false;
        if (account1C == null) {
            if (other.account1C != null)
                return false;
        } else if (!account1C.equals(other.account1C))
            return false;
        if (rfidTag == null) {
            if (other.rfidTag != null)
                return false;
        } else if (!rfidTag.equals(other.rfidTag))
            return false;
        return true;
    }

    @Override
    public int compareTo(Person person) {
        return super.getLastName().toLowerCase()
                .compareTo(person.getLastName().toLowerCase());
    }

    public static class Builder {
        private long id;
        private Account1C account1C;
        private String firstName;
        private String patronym;
        private String lastName;
        private DateStamp birthDate;
        private Set<PositionType> position;
        private DepartmentType department;
        private Set<RoomType> room;
        private RfidTag rfidTag;

        public Builder() {
            position = new HashSet<PositionType>();
            room = new HashSet<RoomType>();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder account1C(Account1C account1C) {
            this.account1C = account1C;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder patronym(String patronym) {
            this.patronym = patronym;
            return this;
        }

        public Builder addRoom(RoomType room) {
            this.room.add(room);
            return this;
        }

        public Builder addPosition(PositionType position) {
            this.position.add(position);
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(DateStamp birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder position(Set<PositionType> position) {
            this.position = position;
            return this;
        }

        public Builder department(DepartmentType department) {
            this.department = department;
            return this;
        }

        public Builder room(Set<RoomType> room) {
            this.room = room;
            return this;
        }

        public Builder rfidTag(RfidTag rfidTag) {
            this.rfidTag = rfidTag;
            return this;
        }

        public Person build() {
            return new Employee(this);
        }
    }
}
