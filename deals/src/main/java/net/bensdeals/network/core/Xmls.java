package net.bensdeals.network.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.11.1
 * User: Wei W.
 * Date: 03/29/2012
 * Time: 21:54
 */
public class Xmls {

    public static Document getDocument(String xmlString) throws Exception {
        return getDocumentFromStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static Document getDocumentFromStream(InputStream inputStream) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        document.normalize();
        return document;
    }

    public static Element getElement(Document document, String tagName, int index) {
        return (Element) document.getElementsByTagName(tagName).item(index);
    }

    public static String getTextContentOfChild(Element element, String childTagName) {
        return getText(element.getElementsByTagName(childTagName));
    }

    public static String getTextContentOfChild(Document document, String childTagName) {
        return getText(document.getElementsByTagName(childTagName));
    }

    private static String getText(NodeList nodeList) {
        return nodeList.item(0).getFirstChild().getNodeValue();
    }
}
