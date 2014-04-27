package kz.trei.acs.office.parser;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.hr.Staff;
import kz.trei.acs.office.rfid.Issue;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidType;
import kz.trei.acs.office.rfid.RfidUID;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.office.structure.Table1C;
import kz.trei.acs.office.util.DateStamp;

public class JaxbEmployeeParser implements EmployeeParser {
	private static final Logger LOGGER = Logger
			.getLogger(JaxbEmployeeParser.class);

	@Override
	public List<Person> parse(String xmlfile, String xsdfile)
			throws XmlParserException {
		return parseStaff(xmlfile, xsdfile).getPersonnel();
	}

	public Staff parseStaff(String xmlfile, String xsdfile)
			throws XmlParserException {
		Staff staff = null;
		try {
			File file = new File(xmlfile);
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {
					Staff.class, Employee.class, RfidTag.class,
					DepartmentType.class, PositionType.class, RoomType.class,
					RfidUID.class, Table1C.class, DateStamp.class });
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			if (xsdfile != null) {
				SchemaFactory sf = SchemaFactory
						.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = sf.newSchema(new File(xsdfile));
				jaxbUnmarshaller.setSchema(schema);
			}
			staff = (Staff) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			LOGGER.error(e);
			throw new XmlParserException("ParserStaff JAXB Exception");
		} catch (SAXException e) {
			LOGGER.error(e);
			throw new XmlParserException(
					"ParserStaff Validation Schema JAXB Exception");
		}
		return staff;
	}

	public void createStaffXml(Staff staff, String fileName) {
		try {
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {
					Staff.class, Employee.class });
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(staff, file);
			//jaxbMarshaller.marshal(staff, System.out);
		} catch (JAXBException e) {
			LOGGER.error(e);
		}
	}

	public static Staff createTestStaff() {
		Person person = new Employee(Table1C.createID("КК00000007"));
		Staff staff = new Staff();
		person.setBirthday(DateStamp.create("1967-06-10"));
		person.setFirstName("Alexandr");
		person.setPatronym("G.");
		person.setLastName("Koryagin");
		((Employee) person)
				.setDepartment(DepartmentType.RESEARCH_AND_DEVELOPMENT);
		((Employee) person).addPosition(PositionType.DEPARTMENT_HEAD);
		((Employee) person).addPosition(PositionType.CHIEF_METROLOGIST);
		((Employee) person).addRoom(RoomType.ROOM107);
		((Employee) person).addRoom(RoomType.ROOM204);
		Issue issue = new Issue();
		RfidTag rfidTag = new RfidTag.Builder()
				.setProtocol(ProtocolType.ISO14443A)
				.setRfidUID(RfidUID.createUID("E0040100594737350000"))
				.setIssue(issue).setRfidType(RfidType.CARD).build();

		((Employee) person).setTag(rfidTag);
		staff.addEmployee(person);
		person = new Employee(Table1C.createID("КК00000012"));
		person.setBirthday(DateStamp.create("1991-05-21"));
		person.setFirstName("Oleg");
		person.setPatronym("I.");
		person.setLastName("Krugov");
		((Employee) person)
				.setDepartment(DepartmentType.ADMINISTRATIVE_SERVICE_UTILITY);
		((Employee) person).addPosition(PositionType.SECURITY_GUARD);
		((Employee) person).addRoom(RoomType.ROOM101);
		issue = new Issue();
		rfidTag = new RfidTag.Builder().setProtocol(ProtocolType.ISO15693)
				.setRfidUID(RfidUID.createUID("3A08265B")).setIssue(issue)
				.setRfidType(RfidType.KEYFOB).build();

		((Employee) person).setTag(rfidTag);
		staff.addEmployee(person);
		return staff;
	}
}
