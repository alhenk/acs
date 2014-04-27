package kz.trei.acs.office.hr;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "staff", namespace ="http://www.trei.kz/attendance/tns")
public class Staff {
	@XmlElement(name = "employee", required = true, namespace ="http://www.trei.kz/attendance/tns" )
	@XmlElementWrapper(name = "employees")
	private List<Person> employees;

	public Staff() {
		setPersonnel(new ArrayList<Person>());
	}

	public List<Person> getPersonnel() {
		return employees;
	}

	public void setPersonnel(List<Person> personnel) {
		this.employees = personnel;
	}

	public void addEmployee(Person employee) {
		this.employees.add(employee);
	}

	public void removeEmployee(Person employee) {
		this.employees.remove(employee);
	}

	@Override
	public String toString() {
		StringBuilder printout = new StringBuilder();
		for (Person person : employees) {
			printout.append(person.getLastName() + " ").append(
					((Employee) person).getTableIdValue() + " ");
		}
		return printout.toString();
	}

}
