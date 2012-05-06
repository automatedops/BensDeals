package net.bensdeals.model;

import com.google.inject.internal.Lists;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.List;

import static net.bensdeals.network.core.Xmls.getDocument;

public class Deal implements Serializable{
    private static final String prefix = "src=\"";
    public static final String DEAL_NODE_NAME = "item";
    String title;
    String description;
    String imageUrl;
    String link;
    String data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
            this.data = item.getChildNodes().item(0).getNodeValue();
        }
    }

    private void setImageAndDescription(String desc) {
        String tempUrl = desc.substring(0, desc.indexOf("/>"));
        imageUrl = tempUrl.substring(tempUrl.indexOf(prefix) + prefix.length(), tempUrl.lastIndexOf("\""));
        imageUrl = imageUrl.replaceFirst(".jpg", "l.jpg");
        this.description = desc.substring(tempUrl.length() + 2);
    }

    public static List<Deal> parseXml(String responseBody) {
        List<Deal> deals = Lists.newArrayList();
        try {
            NodeList channel = getDocument(responseBody).getElementsByTagName("channel").item(0).getChildNodes();
            for (int i = 0; i < channel.getLength(); i++) {
                Node item = channel.item(i);
                if (!DEAL_NODE_NAME.equals(item.getNodeName())) continue;
                NodeList childNodes = item.getChildNodes();
                if (childNodes == null) continue;
                deals.add(new Deal().parse(childNodes));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return deals;
    }
}
