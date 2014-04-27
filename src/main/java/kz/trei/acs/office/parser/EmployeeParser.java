package kz.trei.acs.office.parser;

import java.util.List;

import kz.trei.acs.office.hr.Person;


public interface EmployeeParser {
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	List<Person> parse(String xmlfile, String xsdfile) throws XmlParserException;
}
