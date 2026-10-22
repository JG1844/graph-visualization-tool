package GraphCalculator;

import GraphCalculator.GraphState.*;
import GraphCalculator.GraphState.Trees.Line;
import GraphCalculator.GraphState.Trees.PerfectBinaryTree;
import GraphCalculator.GraphState.Trees.StarTree;
import GraphCalculator.Comparator.IntIncreasing;
import DataStructure.Graph;
import Visualization.GraphStreamRenderer;

import java.util.ArrayList;

public class GraphRemoteControl {
    private State state;
    private final GraphStreamRenderer renderer =
            new GraphStreamRenderer();

    public GraphRemoteControl(){
    }

    // Switch State
    public void completeGraph_Kn(int n){
        this.state = new CompleteGraph_Kn(this,n);
    }
    public void biPartiteGraph_Kmn(int m, int n){
        this.state = new BiPartiteGraph_Kmn(this,m,n);
    }
    public void petersenGraph(int n){
        this.state = new PetersenGraph(this,n);
    }
    public void bst(){
        this.state = new BST(this,new IntIncreasing());
    }
    public void line(int n){
        this.state=new Line(this,n);
    }
    public void perfectBinaryTree(int level){
        this.state=new PerfectBinaryTree(this,level);
    }
    public void starTree(int n){
        this.state = new StarTree(this,n);
    }



    // Update countVertex() to return a clean value
    public int countVertex(){
        int count = this.state.countVertex();
        System.out.println("Vertices: " + count);
        return count;
    }

    // Update countEdge() similarly
    public int countEdge(){
        int count = this.state.countEdge();
        System.out.println("Edges: " + count);
        return count;
    }

    public void setState(State newState){
        this.state = newState;
    }

    public String getStateName(){
        return this.state == null ? "No graph" : this.state.toString();
    }

    public String toString(){
        System.out.println(this.state.toString());
        return this.state.toString();
    }

    public void visulize() {
        this.renderer.visualize(this.state.buildGraph());
    }


    // Automorphism
    public void automorphism(){
        this.state.automorphism();
    }
    public ArrayList<ArrayList<String>> getAutomorphisms(){
        // 确保先计算自同构（如果未计算）
        if (this.state.getAutomorphisms().isEmpty()) {
            this.state.automorphism();
        }
        return this.state.getAutomorphisms();
    }

    public Graph<Integer> buildGraph(){
        return this.state.buildGraph();
    }
    public int getSizeAuto(){
        return this.state.getSizeAuto();
    }




    public static void main(String[] args) {
        //System.out.println(C2_Graphs.K_n(5));
        GraphRemoteControl graphs = new GraphRemoteControl();
//        graphs.biPartiteGraph_Kmn(3,2);
//        graphs.toString();
//        graphs.completeGraph_Kn(8);
//        graphs.toString();
//        graphs.starTree(9);
//        graphs.toString();
//        graphs.countVertex();
//        graphs.countEdge();
//        graphs.visulizeIt();

        //graphs.completeGraph_Kn(8);
        graphs.petersenGraph(5);
        graphs.visulize();
//        graphs.biPartiteGraph_Kmn(3,3);
////        graphs.visulizeIt();
////        graphs.countVertex();
//        graphs.automorphism();


    }


}
