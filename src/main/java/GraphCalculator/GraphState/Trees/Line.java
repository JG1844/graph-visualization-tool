package GraphCalculator.GraphState.Trees;

import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.BST;
import GraphCalculator.GraphState.State;
import GraphCalculator.Comparator.IntIncreasing;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Line extends State {
    private int n;

    public Line(GraphRemoteControl graph, int n) {
        super(graph);
        this.n = n;
    }

    // Switch Patterns
    @Override
    public void line(int n) {
        return;
    }


    @Override
    public String toString() {
        return "Line_" + this.n;
    }

    @Override
    public int countVertex() {
        return n;
    }

    @Override
    public int countEdge() {
        return n - 1;
    }

    @Override
    public Graph<Integer> buildGraph() {
        BST<Integer> bst = new BST<Integer>(this.graph, new IntIncreasing());
        Graph<Integer> graph = new Graph<>();
        for (int x = 1; x <= n; x++) {
            bst.insert(x);
        }
        HashMap<Integer, ArrayList<Integer>> adjL = bst.bfs(bst.getRoot());
        graph.setAdjacencyList(adjL);
        return graph;
    }


}