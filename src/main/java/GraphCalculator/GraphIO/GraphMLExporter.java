package GraphCalculator.GraphIO;

import DataStructure.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Writes a DataStructure.Graph to a GraphML file.
 * Vertex ids are the labels themselves (1, 2, ..., m1, n7, ...), so a
 * round-trip export -> import preserves every label.
 * Graph-level metadata (family name, vertex/edge counts) is stored as
 * GraphML graph data.
 */
public class GraphMLExporter {

    public static <A> void export(Graph<A> graph, File file, String family) throws IOException {
        Map<A, ArrayList<A>> adj = graph.getAdjacencyList();

        int edgeCount = 0;
        for (ArrayList<A> list : adj.values()) edgeCount += list.size();
        edgeCount /= 2;

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n");
        sb.append("  <key id=\"family\" for=\"graph\" attr.name=\"family\" attr.type=\"string\"/>\n");
        sb.append("  <key id=\"vertices\" for=\"graph\" attr.name=\"vertices\" attr.type=\"int\"/>\n");
        sb.append("  <key id=\"edges\" for=\"graph\" attr.name=\"edges\" attr.type=\"int\"/>\n");
        sb.append("  <graph id=\"G\" edgedefault=\"undirected\">\n");
        if (family != null && !family.isEmpty()) {
            sb.append("    <data key=\"family\">").append(escapeXml(family)).append("</data>\n");
        }
        sb.append("    <data key=\"vertices\">").append(adj.size()).append("</data>\n");
        sb.append("    <data key=\"edges\">").append(edgeCount).append("</data>\n");

        for (A node : adj.keySet()) {
            sb.append("    <node id=\"").append(escapeXml(String.valueOf(node))).append("\"/>\n");
        }

        // undirected: write each edge once
        Set<String> drawn = new HashSet<>();
        for (A from : adj.keySet()) {
            for (A to : adj.get(from)) {
                String fromS = String.valueOf(from);
                String toS = String.valueOf(to);
                if (fromS.equals(toS)) continue; // skip self-loops
                String pairKey = fromS.compareTo(toS) < 0 ? fromS + "|" + toS : toS + "|" + fromS;
                if (drawn.add(pairKey)) {
                    sb.append("    <edge source=\"").append(escapeXml(fromS))
                            .append("\" target=\"").append(escapeXml(toS)).append("\"/>\n");
                }
            }
        }

        sb.append("  </graph>\n");
        sb.append("</graphml>\n");
        Files.write(file.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static String escapeXml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }
}
