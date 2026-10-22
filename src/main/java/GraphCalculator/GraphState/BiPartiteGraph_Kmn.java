package GraphCalculator.GraphState;

import GraphCalculator.GraphRemoteControl;
import DataStructure.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BiPartiteGraph_Kmn extends State {
    private int n;
    private int m;
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayList<String> nList = new ArrayList<>();

//    // I.V.: Automorphism
//    private ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
      private HashMap<String, ArrayList<String>> adjacencyList;
//    private HashMap<Integer, Integer> g =new HashMap<>();
      //private ArrayList<ArrayList<String>> automorphisms =new ArrayList<>();
//
//
//    private int sizeFrom; // it is m
    private int sizeAuto; // it is permutation of m

    public BiPartiteGraph_Kmn(GraphRemoteControl graph, int m, int n) {
        super(graph);
        this.n = n;
        this.m = m;
        for (int x =1; x<m+1; x++){
            mList.add("m"+x);
        }
        for (int x =1; x<n+1; x++){
            nList.add("n"+x);
        }
    }

    // Switch Patterns
    @Override
    public void biPartiteGraph_Kmn(int m, int n){
        return;
    }


    @Override
    public String toString() {
        return String.format("K_%d,%d", m, n);
    }

    @Override
    public int countVertex() {
        return n+m;
    }

    @Override
    public int countEdge() {
        return n*m;
    }

    @Override
    public Graph<String> buildGraph() {
        /*
        in Kmn, we use Graph<String> because we apply
        m1 & n7. We form a string but not purely integer

         */
        // set up
        Graph<String> graph =new Graph<>();

        // build adjacent list (undirected, both partitions are real nodes)
        for (String m : mList) {
            for (String n : nList) {
                graph.addBidirectionalEdge(m, n);
            }
        }

        return graph;
    }

    // Automorphism
    /*
    Kmn has differen version of the automorphism, so we need to write it differently
    the reason is that we can not swap a vertex in m to a vertex in n
     */

    // automorphism

    public int getSizeAuto(){

        if (this.automorphisms.isEmpty()){
            automorphism();
        }
        return this.automorphisms.size();
    }

    @Override
    public ArrayList<ArrayList<String>> automorphism(){
        System.out.println("Kmn version:");
        ArrayList<String> remainder = new ArrayList<>();

        for (String key : mList){
//            System.out.println(key);
            remainder.add(key);
        }

        permutation(remainder, new ArrayList<>());
        for (Object list : this.automorphisms){
            System.out.println(list);
        }
        return this.automorphisms;
    }

    private void permutation(ArrayList<String> remainder, ArrayList<String> pending) {
        if (remainder.isEmpty()) {
            ArrayList<String> list = new ArrayList<>();
            for (int x=0; x<pending.size(); x++){
                list.add("g("+ mList.get(x) + ")=" + pending.get(x));
            }
            this.automorphisms.add(list);
            return;
        }

        for (int v = 0; v < remainder.size(); v++) {

            pending.add(remainder.get(v)); // we should add to pending first, otherwise, x=0 is a different num
            remainder.remove(v); // remove, we use index but not the exactly content
            permutation(remainder, pending);
            remainder.add(v, pending.getLast());
            pending.removeLast();

        }

    }




}
