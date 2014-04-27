package kz.trei.acs.office.parser;

public class XmlParserException extends Exception{

	private static final long serialVersionUID = -5147386262734243643L;

	public XmlParserException() {
		super();
	}

	public XmlParserException(String message) {
		super(message);
	}

	public XmlParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlParserException(Throwable cause) {
		super(cause);
	}

	protected XmlParserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
