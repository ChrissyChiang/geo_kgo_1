package gov.kcg.kgo.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Xml 工具.
 */
public class XmlUtil {
	
	/**
	 * 解析KcgService xml 內容.
	 *
	 * @param xmlStr the xml str
	 * @return the document
	 * @throws Exception the exception
	 */
	public static String loadKcgServiceXMLStr(String xmlStr) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
        
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//string");
        String rsStr = (String) expr.evaluate(doc, XPathConstants.STRING);
        return rsStr;
	}
}
