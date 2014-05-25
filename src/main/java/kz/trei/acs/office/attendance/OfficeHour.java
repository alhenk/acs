package kz.trei.acs.office.attendance;

import kz.trei.acs.office.hr.Person;
import kz.trei.acs.util.DateStamp;
import kz.trei.acs.util.TimeStamp;

public class OfficeHour {
    private static final long serialVersionUID = 1L;
    private Person employee;
    private DateStamp workingDay;
    private TimeStamp arriving;
    private TimeStamp leaving;
    private TimeStamp total;

    public OfficeHour() {
    }

    public OfficeHour(Builder builder) {
        this.employee = builder.employee;
        this.workingDay = builder.workingDay;
        this.arriving = builder.arriving;
        this.leaving = builder.leaving;
        this.total = builder.total;
    }

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    public DateStamp getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(DateStamp workingDay) {
        this.workingDay = workingDay;
    }

    public TimeStamp getArriving() {
        return arriving;
    }

    public void setArriving(TimeStamp arriving) {
        this.arriving = arriving;
    }

    public TimeStamp getLeaving() {
        return leaving;
    }

    public void setLeaving(TimeStamp leaving) {
        this.leaving = leaving;
    }

    public TimeStamp getTotal() {
        return total;
    }

    public void setTotal(TimeStamp total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeHour)) return false;
        OfficeHour that = (OfficeHour) o;
        if (!workingDay.equals(that.workingDay)) return false;
        if (!employee.equals(that.employee)) return false;
        if (!total.equals(that.total)) return false;
        if (!arriving.equals(that.arriving)) return false;
        if (!leaving.equals(that.leaving)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = employee.hashCode();
        result = 31 * result + workingDay.hashCode();
        result = 31 * result + arriving.hashCode();
        result = 31 * result + leaving.hashCode();
        result = 31 * result + total.hashCode();
        return result;
    }

    public static class Builder {
        private Person employee;
        private DateStamp workingDay;
        private TimeStamp arriving;
        private TimeStamp leaving;
        private TimeStamp total;

        public Builder() {
            super();
        }

        public Builder employee(Person employee) {
            this.employee = employee;
            return this;
        }

        public Builder workingDay(DateStamp workingDay) {
            this.workingDay = workingDay;
            return this;
        }

        public Builder arriving(TimeStamp arriving) {
            this.arriving = arriving;
            return this;
        }

        public Builder leaving(TimeStamp leaving) {
            this.leaving = leaving;
            return this;
        }

        public Builder total(TimeStamp total) {
            this.total = total;
            return this;
        }

        public OfficeHour build() {
            return new OfficeHour(this);
        }
    }
}
