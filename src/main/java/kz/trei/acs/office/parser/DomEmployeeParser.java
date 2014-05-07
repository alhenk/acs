package kz.trei.acs.office.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import kz.trei.acs.office.structure.Account1C;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

public class DomEmployeeParser implements EmployeeParser {
	private static final Logger LOGGER = Logger.getLogger(DomEmployeeParser.class);

	@Override
	public List<Person> parse(String xmlfile, String xsdfile)
			throws XmlParserException {
		Employee.Builder employee;
		RfidTag.Builder rfidTag;
		List<Person> staff = new ArrayList<Person>();

		try {
			File file = new File(xmlfile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			
			dbFactory.setValidating(false);
			dbFactory.setNamespaceAware(true);
			SchemaFactory schFactory = SchemaFactory
					.newInstance(W3C_XML_SCHEMA);
			Schema sch = schFactory.newSchema(new File(xsdfile));
			dbFactory.setSchema(sch);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getElementsByTagName("tns:employee");
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				employee = new Employee.Builder();
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					employee.account1C(Account1C.createId(eElement
                            .getAttribute("id")));
					employee.firstName(eElement
                            .getElementsByTagName("tns:firstName").item(0)
                            .getTextContent());
					String patronym = eElement.getElementsByTagName("tns:patronym")
							.item(0).getTextContent();
					if (patronym != null) {
						employee.patronym(patronym);
					}
					employee.lastName(eElement
                            .getElementsByTagName("tns:lastName").item(0)
                            .getTextContent());
					employee.birthDate(DateStamp.create(eElement
                            .getElementsByTagName("tns:birthday").item(0)
                            .getTextContent()));
					for (int i = 0; i < eElement.getElementsByTagName(
							"tns:position").getLength(); i++) {
						employee.addPosition(PositionType.valueOf(eElement
								.getElementsByTagName("tns:position").item(i)
								.getTextContent()));
					}
					employee.department(DepartmentType.valueOf(eElement
                            .getElementsByTagName("tns:department").item(0)
                            .getTextContent()));
					for (int i = 0; i < eElement.getElementsByTagName("tns:room")
							.getLength(); i++) {
						employee.addRoom(RoomType.valueOf(eElement
								.getElementsByTagName("tns:room").item(0)
								.getTextContent()));
					}
					rfidTag = new RfidTag.Builder();
					NodeList rfidNodes = eElement
							.getElementsByTagName("tns:rfidTag");
					Element rfidElement = (Element) rfidNodes.item(0);
					rfidTag.uid(RfidUID.createUID(rfidElement
                            .getAttribute("uid")));
					rfidTag.protocol(ProtocolType.valueOf(rfidElement
                            .getElementsByTagName("tns:protocol").item(0)
                            .getTextContent()));
					rfidTag.type(RfidType.valueOf(rfidElement
                            .getElementsByTagName("tns:tagtype").item(0)
                            .getTextContent()));
					Issue.Builder issue = new Issue.Builder();
					rfidNodes = eElement.getElementsByTagName("tns:issue");
					rfidElement = (Element) rfidNodes.item(0);
					issue.issueDate(DateStamp.create(rfidElement
                            .getElementsByTagName("tns:issueDate").item(0)
                            .getTextContent()));
					issue.issueDate(DateStamp.create(rfidElement
                            .getElementsByTagName("tns:expirationDate").item(0)
                            .getTextContent()));
					rfidTag.issue(issue.build());
					employee.rfidTag(rfidTag.build());
					staff.add(employee.build());
				}
			}
		} catch (SAXException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		} catch (ParserConfigurationException e){
			LOGGER.error(e);
		}
		return staff;
	}
}
