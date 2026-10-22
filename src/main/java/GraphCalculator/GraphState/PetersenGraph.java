package GraphCalculator.GraphState;

import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.Trees.Line;
import GraphCalculator.Comparator.IntIncreasing;
import DataStructure.Graph;
import MathParts.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public class PetersenGraph extends State {
    private int n;
    private ArrayList<Vector2D> vertexList;

    // automorphism
    private HashMap<Vector2D, ArrayList<Vector2D>> adjacencyList;
    private HashMap<Vector2D, Vector2D> g =new HashMap<>();
    //private ArrayList<ArrayList<String>> automorphisms =new ArrayList<>();


    public PetersenGraph(GraphRemoteControl graph, int n) {
        super(graph);
        this.n = n;
        this.vertexList = this.buildVertex();
    }

    public ArrayList<Vector2D> buildVertex(){

        ArrayList<Vector2D> vertices = new ArrayList<>();
        ArrayList<Integer> numList = new ArrayList<>();
        for (int x=1; x<n+1;x++){
            numList.add(x);
        }

        // build the buildVertex list
        for (int x=0; x<numList.size()-1;x++){
            int y = numList.get(x)+1;
            while (numList.contains(y)){
                vertices.add(new Vector2D(numList.get(x),y));
                y++;
            }
        }

        return vertices;
    }

    // Switch Patterns
    public void completeGraph_Kn(int n){
        this.graph.setState(new CompleteGraph_Kn(this.graph,n));
    }
    public void biPartiteGraph_Kmn(int m, int n){
        this.graph.setState(new BiPartiteGraph_Kmn(this.graph,m,n));
    }
    public void petersenGraph(int n){
        return;
    }
    public void bst(){
        this.graph.setState(new BST(this.graph,new IntIncreasing()));
    }
    public void line(int n){
        this.graph.setState(new Line(this.graph,n));
    }



    @Override
    public String toString(){
        return "PetersenGraph "+ this.n+" ele(s): ";
    }

    @Override
    public int countVertex() {
        int v = (this.n*(this.n-1))/2;
        //System.out.println(v);
        return v;
    }

    @Override
    public int countEdge() {
        int degree = (this.n - 2) * (this.n - 3) / 2;
        int vertex = countVertex();
        int e = (degree*vertex)/2;
        //System.out.println(e);
        return e;
    }

    @Override
    public Graph<Vector2D> buildGraph() {

        // set up
        Graph<Vector2D> graph =new Graph<>();

        // build adjacent list
        for (int x =0; x<vertexList.size()-1; x++){
            for (int y=x+1;y<vertexList.size();y++){
                if (!Vector2D.shareCommon(vertexList.get(x), vertexList.get(y))){
                    graph.addBidirectionalEdge(vertexList.get(x),vertexList.get(y));
                }
            }
        }
        return graph;
    }



    public int getSizeAuto(){

        if (this.automorphisms.isEmpty()){
            automorphism();
        }
        return this.automorphisms.size();
    }

    @Override
    public ArrayList<ArrayList<String>> automorphism(){
        System.out.println("PetersenGraph version");

        ArrayList<Vector2D> remainder = new ArrayList<>();
        Graph<Vector2D> graph1 = this.buildGraph();
        this.adjacencyList = graph1.getAdjacencyList();
        remainder.addAll(this.adjacencyList.keySet());

        this.sizeFrom = this.countVertex();

        ArrayList<Vector2D> temp = new ArrayList<>();
        permutation(remainder, temp);

        // print out the auto
        for (Object list : this.automorphisms){
            System.out.println(list);
        }
        return this.automorphisms;
    }

    // automorphism is actually permutation
    private void permutation(ArrayList<Vector2D> remainder, ArrayList<Vector2D> pending) {
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
    private boolean autoHelper(ArrayList<Vector2D> permutation){
        // SetUp: build the temp automorphism
        HashMap<Vector2D, ArrayList<Vector2D>> temp = new HashMap<>();
        /**
         * the permutation has the order from 1 to n. But the hashmap does not
         */
        for (int x=0; x<vertexList.size(); x++){
            this.g.put(vertexList.get(x),permutation.get(x));
        }

        // perform the g on the graph
        /*
        g is hashmap -> no order. We need add key to it
         */

        for (Vector2D key : this.adjacencyList.keySet()){
            temp.put(g.get(key),new ArrayList<>());

            /*
            we do not only swap the key
            we also need to adjust its edges
             */
            for (Vector2D valus : this.adjacencyList.get(key)){
                /*
                put the adjusted edges to the key
                 */
                temp.get(g.get(key)).add(g.get(valus));
            }
        }

        // check if temp is an automorphism

        for (Vector2D key : this.adjacencyList.keySet()){
            if (!compareAdjV(this.adjacencyList.get(key),temp.get(key))){
                return false;
            }
        }

        this.sizeAuto++;
        //System.out.println(permutation);

        // convert to g( (a,b) )= (c,d)'
        ArrayList<String> list = new ArrayList<>();
        for (int x=0; x< this.vertexList.size(); x++){

            list.add("g(" + vertexList.get(x).getX() + "," + vertexList.get(x).getY()+ ")=" +
                    "(" + g.get(vertexList.get(x)).getX() + "," + g.get(vertexList.get(x)).getY()+ ")");
        }
        this.automorphisms.add(list);
        return true;
    }

    private boolean compareAdjV(ArrayList<Vector2D> a1,ArrayList<Vector2D> a2){


        if (a1.size()!= a2.size()){
            return false;
        }

        for (Vector2D x : a1){
            if (!a2.contains(x)){
                return false;
            }
        }

        return true;

    }



}
