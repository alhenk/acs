package kz.trei.acs.office.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import kz.trei.acs.office.structure.Account1C;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.Issue;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidType;
import kz.trei.acs.office.rfid.RfidUID;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.office.util.DateStamp;

public class StaxEmployeeParser implements EmployeeParser {
	private static final Logger LOGGER = Logger
			.getLogger(StaxEmployeeParser.class);

	public static boolean isValid(String xmlfile, String xsdfile) {
		XMLStreamReader xmlReader = null;
		try {
			xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(
					new FileInputStream(xmlfile));
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdfile));
			Validator validator = schema.newValidator();
			validator.validate(new StAXSource(xmlReader));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return false;
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				xmlReader.close();
			} catch (XMLStreamException e) {
				LOGGER.error(e);
			}
		}
		// no exception thrown, so valid
		LOGGER.info("Document (" + xmlfile + ") is valid");
		return true;
	}

	@Override
	public List<Person> parse(String xmlfile, String xsdfile)
			throws XmlParserException {

		if (xsdfile != null) {
			if (!isValid(xmlfile, xsdfile)) {
				throw new XmlParserException("Document (" + xmlfile + ") is valid");
			}
		}
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader = null;
		// XMLStreamReader schemaReader;
		String elementName = "";
		String elementText = "";
		List<Person> staff = new ArrayList<Person>();
		Employee.Builder employee = new Employee.Builder();
		RfidTag.Builder tag = new RfidTag.Builder();
		Issue.Builder issue = new Issue.Builder();
		try {
			xmlReader = inputFactory.createXMLStreamReader(new FileInputStream(
					xmlfile));
			while (xmlReader.hasNext()) {
				int type = xmlReader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					elementName = xmlReader.getLocalName();
					if (elementName.equalsIgnoreCase("EMPLOYEE")) {
						employee = new Employee.Builder();
						String value = xmlReader.getAttributeValue(null,
								"tableId");
						if (value == null) {
							employee.setAccount1C1(Account1C.createRandomId());
						} else {
							try {
								employee.setAccount1C1(Account1C.createId(value));
							} catch (IllegalArgumentException e) {
								LOGGER.error(e);
							}
						}
					} else if (elementName.equalsIgnoreCase("RFIDTAG")) {
						tag = new RfidTag.Builder();
						String value = xmlReader.getAttributeValue(null, "uid");
						if (value != null) {
							tag.setRfidUID(RfidUID.createUID(value));
						}
					} else if (elementName.equalsIgnoreCase("ISSUE")) {
						issue = new Issue.Builder();
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					elementText = xmlReader.getText().trim();
					break;
				case XMLStreamConstants.END_ELEMENT:
					elementName = xmlReader.getLocalName();
					if (elementName.equalsIgnoreCase("EMPLOYEE")) {
						staff.add(employee.build());
					} else if (elementName.equalsIgnoreCase("FIRSTNAME")) {
						employee.setFirstName(elementText);
					} else if (elementName.equalsIgnoreCase("PATRONYM")) {
						employee.setPatronym(elementText);
					} else if (elementName.equalsIgnoreCase("LASTNAME")) {
						employee.setLastName(elementText);
					} else if (elementName.equalsIgnoreCase("BIRTHDAY")) {
						employee.setBirthday(DateStamp.create(elementText));
					} else if (elementName.equalsIgnoreCase("POSITION")) {
						employee.addPosition(PositionType.valueOf(elementText));
					} else if (elementName.equalsIgnoreCase("DEPARTMENT")) {
						employee.setDepartment(DepartmentType
								.valueOf(elementText));
					} else if (elementName.equalsIgnoreCase("ROOM")) {
						employee.addRoom(RoomType.valueOf(elementText));
					} else if (elementName.equalsIgnoreCase("TABLEID")) {
						employee.setAccount1C1(Account1C.createId(elementText));
					} else if (elementName.equalsIgnoreCase("RFIDTAG")) {
						employee.setTag(tag.build());
					} else if (elementName.equalsIgnoreCase("TAGTYPE")) {
						tag.setRfidType(RfidType.valueOf(elementText));
					} else if (elementName.equalsIgnoreCase("PROTOCOL")) {
						tag.setProtocol(ProtocolType.valueOf(elementText));
					} else if (elementName.equalsIgnoreCase("ISSUE")) {
						tag.setIssue(issue.build());
					} else if (elementName.equalsIgnoreCase("ISSUEDATE")) {
						issue.setIssueDate(DateStamp.create(elementText));
					} else if (elementName.equalsIgnoreCase("EXPIRATIONDATE")) {
						issue.setExpirationDate(DateStamp.create(elementText));
					}
					break;
				default:
					break;
				}
			}
		} catch (XMLStreamException e) {
			LOGGER.error(e);
			return null;
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
			return null;
		} finally {
			try {
				xmlReader.close();
			} catch (XMLStreamException e) {
				LOGGER.error(e);
			}
		}
		return staff;
	}
}
