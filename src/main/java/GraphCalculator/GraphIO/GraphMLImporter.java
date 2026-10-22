package GraphCalculator.GraphIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads a GraphML file into an undirected adjacency map keyed by the
 * original vertex labels. Parsing is locked down against external entities
 * (XXE-safe) and needs no third-party dependency (JDK XML only).
 */
public class GraphMLImporter {

    public static Map<String, List<String>> importGraph(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // secure XML parsing
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        Document doc = factory.newDocumentBuilder().parse(file);

        Map<String, List<String>> adj = new HashMap<>();

        NodeList nodes = doc.getElementsByTagName("node");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            String id = node.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                adj.computeIfAbsent(id, k -> new ArrayList<>());
            }
        }

        NodeList edges = doc.getElementsByTagName("edge");
        for (int i = 0; i < edges.getLength(); i++) {
            Element edge = (Element) edges.item(i);
            String source = edge.getAttribute("source");
            String target = edge.getAttribute("target");
            if (source == null || source.isEmpty() || target == null || target.isEmpty()) continue;
            adj.computeIfAbsent(source, k -> new ArrayList<>()).add(target);
            if (!source.equals(target)) {
                adj.computeIfAbsent(target, k -> new ArrayList<>()).add(source);
            }
        }
        return adj;
    }
}
