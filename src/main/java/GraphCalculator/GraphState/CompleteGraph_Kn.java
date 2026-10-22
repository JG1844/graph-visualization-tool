package GraphCalculator.GraphState;

import GraphCalculator.GraphRemoteControl;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteGraph_Kn<A> extends State {
    private int n;
    private ArrayList<A> items;

    public CompleteGraph_Kn(GraphRemoteControl graph, int n) {
        super(graph);
        this.n = n;
    }

    public HashMap<A, ArrayList<A>> getAdjList(ArrayList<A> items){
        Graph<A> graph = buildGraph(items);
        return graph.getAdjacencyList();
    }

    // Switch Patterns
    @Override
    public void completeGraph_Kn(int n){
        return;
    }


    @Override
    public String toString() {
        return "K_"+ this.n;
    }

    @Override
    public int countVertex() {
        return n;
    }

    @Override
    public int countEdge() {
        // (find degree * all vertext)/2
        int deg = n-1;
        return (deg*n)/2;
    }

    public Graph<A> buildGraph(ArrayList<A> items) {

        // set up
        Graph<A> graph =new Graph<>();

        // build adjacent list
        for (int from =0; from<items.size(); from++){
            graph.addNode(items.get(from));
            for (int to = 0; to<items.size(); to++){
                if (!items.get(from).equals(items.get(to))){
                    graph.addEdge(items.get(from),items.get(to));

                }
            }

        }

        return graph;
    }

    @Override
    public Graph<Integer> buildGraph() {

        // set up
        Graph<Integer> graph =new Graph<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int x =1; x<n+1; x++){
            list.add(x);
        }

        // build adjacent list
        for (int from =0; from<list.size(); from++){
            graph.addNode(list.get(from));
            for (int to = 0; to<list.size(); to++){
                if (!list.get(from).equals(list.get(to))){
                    graph.addEdge(list.get(from),list.get(to));

                }
            }

        }

        return graph;
    }


    
}
