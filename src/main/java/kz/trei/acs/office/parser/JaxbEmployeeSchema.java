package kz.trei.acs.office.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

import kz.trei.acs.office.hr.Employee;
import kz.trei.acs.office.hr.Staff;
import kz.trei.acs.office.rfid.RfidTag;
import kz.trei.acs.office.rfid.RfidUID;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import kz.trei.acs.office.structure.Table1C;
import kz.trei.acs.office.util.DateStamp;

public class JaxbEmployeeSchema {
	private static final Logger LOGGER = Logger.getLogger(JaxbEmployeeSchema.class);
	//private static String _fileName;

	public static void generateXsd(String fileName) {
		//_fileName = fileName;
		JAXBContext jaxbContext;
		try {
			SchemaOutputResolver sor = new MySchemaOutputResolver();
			jaxbContext = JAXBContext.newInstance(Staff.class, Employee.class,
					RfidTag.class, DepartmentType.class, PositionType.class,
					RoomType.class, RfidUID.class, Table1C.class,
					DateStamp.class);
			jaxbContext.generateSchema(sor);
		} catch (JAXBException e) {
			LOGGER.info(e);
		} catch (IOException e) {
			LOGGER.info(e);
		}
	}

	public static class MySchemaOutputResolver extends SchemaOutputResolver {

		public Result createOutput(String namespaceURI, String suggestedFileName)
				throws IOException {
			File file = new File(suggestedFileName);
			//File file = new File(_fileName);
			StreamResult result = new StreamResult(file);
			result.setSystemId(file.toURI().toURL().toString());
			return result;
		}

	}
}
