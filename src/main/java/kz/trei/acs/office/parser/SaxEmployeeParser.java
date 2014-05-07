package kz.trei.acs.office.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Person;
import kz.trei.acs.office.rfid.Issue;
import kz.trei.acs.office.rfid.ProtocolType;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidType;
import kz.trei.acs.office.rfid.RfidUID;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.office.util.DateStamp;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxEmployeeParser implements EmployeeParser {

	private static final Logger LOGGER = Logger
			.getLogger(SaxEmployeeParser.class);
	private List<Person> staff;

	@Override
	public List<Person> parse(String xmlfile, String xsdfile)
			throws XmlParserException {
		staff = new ArrayList<Person>();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			if (xsdfile != null) {
				SchemaFactory schemaFactory = SchemaFactory
						.newInstance(W3C_XML_SCHEMA);
				Schema schema = schemaFactory.newSchema(new File(xsdfile));
				factory.setValidating(false);// "true" for using DTD!
				factory.setSchema(schema);
				factory.setNamespaceAware(true);
			}
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new PersonHandler();
			saxParser.parse(xmlfile, handler);
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
			throw new XmlParserException(e);
		} catch (SAXException e) {
			LOGGER.error(e);
			throw new XmlParserException(e);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new XmlParserException(e);
		}
		return staff;
	}

	private class PersonHandler extends DefaultHandler {
		Employee.Builder employee;
		RfidTag.Builder tag;
		Issue.Builder issue;
		StringBuilder elementValue;

		public PersonHandler() {
		}

		public void startDocument() {
			elementValue = new StringBuilder();
		}

		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			elementValue.setLength(0);
			if (qName.equalsIgnoreCase("tns:EMPLOYEE")) {
				employee = new Employee.Builder();
				String value = attributes.getValue("id");
				if (value == null) {
					employee.setAccount1C1(Account1C.createRandomId());
				} else {
					try {
						employee.setAccount1C1(Account1C.createId(value));
					} catch (IllegalArgumentException e) {
						LOGGER.error(e);
					}
				}
			} else if (qName.equalsIgnoreCase("tns:RFIDTAG")) {
				tag = new RfidTag.Builder();
				String value = attributes.getValue("uid");
				if (value != null) {
					tag.uid(RfidUID.createUID(value));
				}
			} else if (qName.equalsIgnoreCase("tns:ISSUE")) {
				issue = new Issue.Builder();
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equalsIgnoreCase("tns:EMPLOYEE")) {
				staff.add(employee.build());
			} else if (qName.equalsIgnoreCase("tns:FIRSTNAME")) {
				employee.setFirstName(elementValue.toString().trim());
			} else if (qName.equalsIgnoreCase("tns:PATRONYM")) {
				employee.setPatronym(elementValue.toString().trim());
			} else if (qName.equalsIgnoreCase("tns:LASTNAME")) {
				employee.setLastName(elementValue.toString().trim());
			} else if (qName.equalsIgnoreCase("tns:BIRTHDAY")) {
				employee.setBirthday(DateStamp.create(elementValue.toString()
						.trim()));
			} else if (qName.equalsIgnoreCase("tns:POSITION")) {
				employee.addPosition(PositionType.valueOf(elementValue
						.toString().trim()));
			} else if (qName.equalsIgnoreCase("tns:DEPARTMENT")) {
				employee.setDepartment(DepartmentType.valueOf(elementValue
						.toString().trim()));
			} else if (qName.equalsIgnoreCase("tns:ROOM")) {
				employee.addRoom(RoomType.valueOf(elementValue
						.toString().trim()));
			} else if (qName.equalsIgnoreCase("tns:TABLEID")) {
				employee.setAccount1C1(Account1C.createId(elementValue.toString()
                        .trim()));
			} else if (qName.equalsIgnoreCase("tns:RFIDTAG")) {
				employee.setTag(tag.build());
			} else if (qName.equalsIgnoreCase("tns:TAGTYPE")) {
				tag.type(RfidType
                        .valueOf(elementValue.toString().trim()));
			} else if (qName.equalsIgnoreCase("tns:PROTOCOL")) {
				tag.protocol(ProtocolType.valueOf(elementValue.toString()
                        .trim()));
			} else if (qName.equalsIgnoreCase("tns:ISSUE")) {
				tag.issue(issue.build());
			} else if (qName.equalsIgnoreCase("tns:ISSUEDATE")) {
				issue.issueDate(DateStamp.create(elementValue.toString()
                        .trim()));
			} else if (qName.equalsIgnoreCase("tns:EXPIRATIONDATE")) {
				issue.expirationDate(DateStamp.create(elementValue
                        .toString().trim()));
			}
		}

		public void characters(char ch[], int start, int length) {
			elementValue.append(new String(ch, start, length));
		}

		public void endDocument() {
		}

		public void ignorableWhitespace(char[] cbuf, int start, int len) {
		}

		public void processingInstruction(String target, String data) {
		}

		public void warning(SAXParseException exception) {
			LOGGER.error("WARNING: line " + exception.getLineNumber() + ": "
					+ exception.getMessage());
		}

		public void error(SAXParseException exception) {
			LOGGER.error("ERROR: line " + exception.getLineNumber() + ": "
					+ exception.getMessage());
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			LOGGER.error("FATAL: line " + exception.getLineNumber() + ": "
					+ exception.getMessage());
			throw (exception);
		}
	}
}