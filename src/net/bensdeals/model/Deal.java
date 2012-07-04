package net.bensdeals.model;

import com.google.inject.internal.Lists;
import net.bensdeals.utils.ALog;
import net.bensdeals.utils.DateFormatter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static net.bensdeals.network.core.Xmls.getDocument;
import static net.bensdeals.network.core.Xmls.getDocumentFromStream;

public class Deal implements Serializable {
    private static final String prefix = "src=\"";
    public static final String DEAL_NODE_NAME = "item";
    String title;
    String description;
    String imageUrl;
    String link;
    Date date;

    public String getTitle() {
        return title;
    }

    public Deal setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Deal setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Deal setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Deal setLink(String link) {
        this.link = link;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Deal setDate(Date date) {
        this.date = date;
        return this;
    }

    public Deal parse(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            setValue(nodeList.item(i));
        }
        return this;
    }

    private void setValue(Node item) {
        String nodeName = item.getNodeName();
        if ("title".equals(nodeName)) {
            this.title = item.getChildNodes().item(0).getNodeValue();
        } else if ("description".equals(nodeName)) {
            setImageAndDescription(item.getChildNodes().item(0).getNodeValue());
        } else if ("link".equals(nodeName)) {
            this.link = item.getChildNodes().item(0).getNodeValue();
        } else if ("pubDate".equals(nodeName)) {
            this.date = DateFormatter.stringToDate(item.getChildNodes().item(0).getNodeValue());
        }
    }

    private void setImageAndDescription(String desc) {
        String tempUrl = desc.substring(0, desc.indexOf("/>"));
        imageUrl = tempUrl.substring(tempUrl.indexOf(prefix) + prefix.length(), tempUrl.lastIndexOf("\""));
        imageUrl = imageUrl.replaceFirst(".jpg", "l.jpg");
        this.description = desc.substring(tempUrl.length() + 2);
    }

    public synchronized static List<Deal> parseXml(String responseBody) throws IOException, SAXException, ParserConfigurationException {
        List<Deal> deals = Lists.newArrayList();
        try {
            getFromNode(deals, getDocument(responseBody).getElementsByTagName("channel").item(0).getChildNodes());
        } catch (Exception e) {
            ALog.e(e);
        }
        return deals;
    }

    public synchronized static List<Deal> parseXml(InputStream inputStream) throws Exception {
        List<Deal> deals = Lists.newArrayList();
        getFromNode(deals, getDocumentFromStream(inputStream).getElementsByTagName("channel").item(0).getChildNodes());
        return deals;
    }

    private static void getFromNode(List<Deal> deals, NodeList channel) {
        for (int i = 0; i < channel.getLength(); i++) {
            Node item = channel.item(i);
            if (!DEAL_NODE_NAME.equals(item.getNodeName())) continue;
            NodeList childNodes = item.getChildNodes();
            if (childNodes == null) continue;
            deals.add(new Deal().parse(childNodes));
        }
    }
}
