package GraphCalculator.GraphState;

import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.Trees.Line;
import GraphCalculator.GraphState.Trees.PerfectBinaryTree;
import GraphCalculator.GraphState.Trees.StarTree;
import GraphCalculator.Comparator.IntIncreasing;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class State<A> {
    protected GraphRemoteControl graph;

    // I.V.: Automorphism
    protected ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
    protected HashMap<Integer, ArrayList<Integer>> adjacencyList;
    protected HashMap<Integer, Integer> g =new HashMap<>();
    protected ArrayList<ArrayList<String>> automorphisms =new ArrayList<>();

    protected int sizeFrom;
    protected int sizeAuto;


    // Constructor
    public State(GraphRemoteControl graph){
        this.graph = graph;

        for (int x=1; x<sizeFrom+1; x++){
            this.g.put(x,null);
        }
    }

    // Switch Patterns
    public void completeGraph_Kn(int n){
        this.graph.setState(new CompleteGraph_Kn(this.graph,n));
    }
    public void biPartiteGraph_Kmn(int m, int n){
        this.graph.setState(new BiPartiteGraph_Kmn(this.graph,m,n));
    }
    public void petersenGraph(int n){
        this.graph.setState(new PetersenGraph(this.graph,n));
    }
    public void bst(){
        this.graph.setState(new BST(this.graph,new IntIncreasing()));
    }
    public void line(int n){
        this.graph.setState(new Line(this.graph,n));
    }
    public void perfectBinaryTree(int level){
        this.graph.setState(new PerfectBinaryTree(this.graph,level));
    }
    public void starTree(int n){
        this.graph.setState(new StarTree(this.graph,n));
    }


    public abstract String toString();
    public abstract int countVertex();
    public abstract int countEdge();
    public abstract Graph<A> buildGraph();


    // Automorphism
//    public abstract int getSizeFrom();
    public void setAdjacencyList(HashMap<A, ArrayList<A>> adjacencyList){
        //this.adjacencyList = adjacencyList;
    }

    public ArrayList<ArrayList<String>> getAutomorphisms(){
        return this.automorphisms;
    }

    public void setFrom(int from){
        this.sizeFrom = from;
    }
    public int getSizeAuto(){

        if (this.automorphisms.isEmpty()){
            automorphism();
        }
        return this.automorphisms.size();
    }

    public ArrayList<ArrayList<String>> automorphism(){
        System.out.println("State version: " + this.graph.toString());

        ArrayList<Integer> remainder = new ArrayList<>();
        Graph<Integer> graph1 = this.graph.buildGraph();
        this.adjacencyList = graph1.getAdjacencyList();

        for (int key : this.adjacencyList.keySet()){
            remainder.add(key);
        }
        this.sizeFrom = graph.countVertex();


        permutation(remainder, new ArrayList<>());

        // print out the auto

        for (ArrayList<String> list : this.automorphisms){
            System.out.println(list);
        }
        return this.automorphisms;
    }

    // automorphism is actually permutation
    private void permutation(ArrayList<Integer> remainder, ArrayList<Integer> pending) {
        if (remainder.isEmpty()) {
            //System.out.println(pending);
            autoHelper(new ArrayList<>(pending));
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

    /**
     * Compare with the input adjacent to find out all automorphisms
     */
    private boolean autoHelper(ArrayList<Integer> permutation){
        // SetUp: build the temp automorphism
        HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
        /**
         * the permutation has the order from 1 to n. But the hashmap does not
         */
        for (int x=0; x<sizeFrom; x++){
            this.g.put(x+1,permutation.get(x));
        }


        // perform the g on the graph
        /*
        g is hashmap -> no order. We need add key to it
         */

        for (int key : this.adjacencyList.keySet()){
            temp.put(g.get(key),new ArrayList<>());

            /*
            we do not only swap the key
            we also need to adjust its edges
             */
            for (int valus : this.adjacencyList.get(key)){
                /*
                put the adjusted edges to the key
                 */
                temp.get(g.get(key)).add(g.get(valus));
            }
        }

        // check if temp is an automorphism

        for (int key : this.adjacencyList.keySet()){
            if (!compareAdjV(this.adjacencyList.get(key),temp.get(key))){
                return false;
            }
        }

        this.sizeAuto++;
        //System.out.println(permutation);

        // convert to g(v)=v'
        ArrayList<String> list = new ArrayList<>();
        for (int x=1; x< this.sizeFrom+1; x++){
            list.add("g(" + x + ")=" + g.get(x));
        }
        this.automorphisms.add(list);
        return true;

//        if (Objects.equals(this.adjacencyList,temp)){
//            this.sizeAuto++;
//            this.automorphisms.add(new HashMap<>(g));
//            return true;
//        } else {
//            System.out.println(permutation);
//            return false;
//        }

    }

    private boolean compareAdjV(ArrayList<Integer> a1,ArrayList<Integer> a2){


        if (a1.size()!= a2.size()){
            return false;
        }

        for (int x : a1){
            if (!a2.contains(x)){
                return false;
            }
        }

        return true;

    }

}
