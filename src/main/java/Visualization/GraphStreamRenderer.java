package Visualization;

import DataStructure.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class GraphStreamRenderer{

    public <T> void visualize(Graph<T> sourceGraph) {
        // 关键：告诉 GraphStream 用 swing 作为 UI 渲染包
        System.setProperty("org.graphstream.ui", "swing");

        org.graphstream.graph.Graph displayGraph =
                new SingleGraph("display-graph");

        HashMap<T, ArrayList<T>> adjacencyList =
                sourceGraph.getAdjacencyList();

        // Maps any mathematical vertex type to a GraphStream String ID.
        Map<T, String> nodeIds = new HashMap<>();

        int nodeNumber = 0;

        // Step 1: create all nodes.
        for (T vertex : adjacencyList.keySet()) {
            String nodeId = "node-" + nodeNumber++;
            nodeIds.put(vertex, nodeId);

            Node displayNode = displayGraph.addNode(nodeId);

            // This is the text students see.
            displayNode.setAttribute("ui.label", vertex.toString());
        }

        // Remembers which undirected edges were already drawn.
        Set<String> drawnEdges = new HashSet<>();
        int edgeNumber = 0;

        // Step 2: create all edges.
        for (T from : adjacencyList.keySet()) {
            for (T to : adjacencyList.get(from)) {
                String fromId = nodeIds.get(from);
                String toId = nodeIds.get(to);

                // Same key for A--B and B--A.
                String pairKey = fromId.compareTo(toId) < 0
                        ? fromId + "|" + toId
                        : toId + "|" + fromId;

                // Add this undirected edge only once.
                if (drawnEdges.add(pairKey)) {
                    displayGraph.addEdge(
                            "edge-" + edgeNumber++,
                            fromId,
                            toId
                    );
                }
            }
        }

        displayGraph.display();
    }
}
