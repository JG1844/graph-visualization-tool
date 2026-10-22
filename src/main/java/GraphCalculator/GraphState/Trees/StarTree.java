package GraphCalculator.GraphState.Trees;

import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.State;
import DataStructure.Graph;

import java.util.ArrayList;

public class StarTree extends State<Integer> {
    private int n;

    // automorphism (in permutation we need the vertce without 0)
    private ArrayList<Integer> numList = new ArrayList<>();
    //private ArrayList<ArrayList<String>> automorphisms =new ArrayList<>();
    private ArrayList<ArrayList<String>> permutations =new ArrayList<>();

    public StarTree(GraphRemoteControl graph, int n) {
        super(graph);
        this.n = n;
        for (int x =1; x<n+1; x++){
            this.numList.add(x);
        }


    }


    // Switch Patterns
    @Override
    public void starTree(int n){
        return;
    }

    @Override
    public String toString() {
        return String.format("StarTree_%d", n);
    }

    @Override
    public int countVertex() {
        return n+1;
    }

    @Override
    public int countEdge() {
        return n;
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
            graph.addBidirectionalEdge(0,list.get(from));
        }
        return graph;
    }

    // Automorphism
    @Override
    public int getSizeAuto(){
        return this.automorphisms.size();
    }

    @Override
    public ArrayList<ArrayList<String>> automorphism(){
        System.out.println("State version");

        ArrayList<String> remainder = new ArrayList<>();
        for (int x = 1; x < n + 1; x++) {
            remainder.add(String.valueOf(x));
        }

        ArrayList<String> pending = new ArrayList<>();
        permutation(remainder, pending);

        return this.automorphisms;
    }

    public void permutation( ArrayList<String> remainder, ArrayList<String> pending){
        if (remainder.isEmpty()){
            pending.addFirst(String.valueOf(0));

            // convert to g(v)=v'
            ArrayList<String> list = new ArrayList<>();

            for (int x=0; x< n+1; x++){
                list.add("g(" + x + ")=" + pending.get(x));
            }
            this.automorphisms.add(list);
            System.out.println(list);
            this.permutations.add(pending);
            //System.out.println(permutations);
            pending.remove(0);
            return;

        }

        for (int v=0 ; v<remainder.size(); v++){

            pending.add(remainder.get(v)); // we should add to pending first, otherwise, x=0 is a different num
            remainder.remove(v); // remove, we use index but not the exactly content
            permutation(remainder,pending);
            remainder.add(v,pending.getLast());
            pending.removeLast();

        }


    }
}
