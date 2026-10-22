package GraphCalculator.GraphState;
import GraphCalculator.GraphRemoteControl;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A graph with arbitrary vertex labels (Integer, String, ...).
 * Imported graphs keep their original labels; automorphisms are computed
 * label-agnostically (full automorphism group of the plain graph).
 */
public class CustomGraph<A> extends State<A> {
    private final Map<A, List<A>> adjMap;
    private final String displayName;

    public CustomGraph(GraphRemoteControl graph, Map<A, List<A>> adjacencyMap) {
        this(graph, adjacencyMap, "Custom Drawn Graph");
    }

    public CustomGraph(GraphRemoteControl graph, Map<A, List<A>> adjacencyMap, String displayName) {
        super(graph);
        this.adjMap = adjacencyMap;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    public int countVertex() {
        return adjMap.size();
    }

    @Override
    public int countEdge() {
        int total = 0;
        for (List<A> neighbors : adjMap.values()) total += neighbors.size();
        return total / 2;
    }

    @Override
    public Graph<A> buildGraph() {
        Graph<A> graph = new Graph<>();
        for (A key : adjMap.keySet()) graph.addNode(key);
        // undirected: add each edge exactly once (labels may not be ordered)
        Set<String> drawn = new HashSet<>();
        for (A from : adjMap.keySet()) {
            for (A to : adjMap.get(from)) {
                if (from.equals(to)) continue; // ignore self-loops
                String fromS = String.valueOf(from);
                String toS = String.valueOf(to);
                String pairKey = fromS.compareTo(toS) < 0 ? fromS + "|" + toS : toS + "|" + fromS;
                if (drawn.add(pairKey)) {
                    graph.addBidirectionalEdge(from, to);
                }
            }
        }
        return graph;
    }

    @Override
    public ArrayList<ArrayList<String>> automorphism() {
        // Enumerate all permutations of the vertex labels and keep those that
        // preserve adjacency. Works for Integer, String, or any label type.
        this.automorphisms.clear();
        this.sizeAuto = 0;

        ArrayList<A> vertices = new ArrayList<>(adjMap.keySet());
        this.sizeFrom = vertices.size();

        Map<A, Integer> pos = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) pos.put(vertices.get(i), i);

        ArrayList<A> perm = new ArrayList<>(vertices);
        permuteAndCheck(perm, 0, vertices, pos);
        return this.automorphisms;
    }

    private void permuteAndCheck(ArrayList<A> perm, int start,
                                 ArrayList<A> vertices, Map<A, Integer> pos) {
        if (start == perm.size()) {
            if (isAutomorphism(perm, vertices, pos)) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < perm.size(); i++) {
                    list.add("g(" + vertices.get(i) + ")=" + perm.get(i));
                }
                this.automorphisms.add(list);
                this.sizeAuto++;
            }
            return;
        }
        for (int i = start; i < perm.size(); i++) {
            Collections.swap(perm, start, i);
            permuteAndCheck(perm, start + 1, vertices, pos);
            Collections.swap(perm, start, i);
        }
    }

    /** π(vertices[i]) = perm[i]; check every edge maps to an edge. */
    private boolean isAutomorphism(List<A> perm, List<A> vertices, Map<A, Integer> pos) {
        for (int i = 0; i < perm.size(); i++) {
            A vi = vertices.get(i);
            A image = perm.get(i);
            for (A neighbor : adjMap.get(vi)) {
                A neighborImage = perm.get(pos.get(neighbor));
                if (!adjMap.get(image).contains(neighborImage)) {
                    return false;
                }
            }
        }
        return true;
    }
}
