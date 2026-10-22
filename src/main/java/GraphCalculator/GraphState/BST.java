package GraphCalculator.GraphState;

import GraphCalculator.GraphRemoteControl;
import DataStructure.BinaryTreeNode;
import GraphCalculator.Comparator.Comparator;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// Binary Search Tree
public class BST<A> extends State<A> {

    private BinaryTreeNode<A> root;
    private Comparator<A> comparator;
    private HashMap<A, ArrayList<A>> adjlist;

    public BST(GraphRemoteControl graph, Comparator<A> comparator) {
        super(graph);
        this.comparator = comparator;
        this.root = null;
    }


    public HashMap<A, ArrayList<A>> getAdjlist(){
        return this.adjlist;
    }

    public BinaryTreeNode<A> getRoot() {
        return this.root;
    }

    public void insert(A value) {
        if (this.root == null) {
            this.root = new BinaryTreeNode<>(value, null, null);
        } else {
            this.insertHelper(this.root, value);
        }
    }

    private void insertHelper(BinaryTreeNode<A> node, A toInsert) {
        if (this.comparator.compare(toInsert, node.getValue())) {
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTreeNode<>(toInsert, null, null));
            } else {
                insertHelper(node.getLeft(), toInsert);
            }
        } else {
            // ties go right
            if (node.getRight() == null) {
                node.setRight(new BinaryTreeNode<>(toInsert, null, null));
            } else {
                insertHelper(node.getRight(), toInsert);
            }
        }
    }



    // this is BFS for tree
    public HashMap<A, ArrayList<A>> bfs(BinaryTreeNode<A> nodes){
        if (nodes==null){
            return null;
        }

        //BinaryTreeNode<A> copy = nodes; // in case we loose the track
        ArrayList<BinaryTreeNode<A>> queue = new ArrayList<>();
        queue.add(nodes);
        ArrayList<A> explored = new ArrayList<>();
        HashMap<A, ArrayList<A>> trace = new HashMap<>();

        while (!queue.isEmpty()){

            if (!explored.contains(queue.getFirst().getValue())){

                if (!trace.containsKey(queue.getFirst().getValue())){
                    trace.put(queue.getFirst().getValue(),new ArrayList<>());
                }

                if (queue.getFirst().getRight()!=null){
                    trace.get(queue.getFirst().getValue()).add(queue.getFirst().getRight().getValue());
                    queue.add(queue.getFirst().getRight());
                    trace.put(queue.getFirst().getRight().getValue(),new ArrayList<>(Arrays.asList(queue.getFirst().getValue())));
                }
                if (queue.getFirst().getLeft()!=null) {
                    trace.get(queue.getFirst().getValue()).add(queue.getFirst().getLeft().getValue());
                    queue.add(queue.getFirst().getLeft());
                    trace.put(queue.getFirst().getLeft().getValue(),new ArrayList<>(Arrays.asList(queue.getFirst().getValue())));

                }

                explored.add(queue.getFirst().getValue());
                queue.remove(queue.getFirst());

            } else {
                queue.remove(queue.getFirst());

            }
        }

        this.adjlist=trace;
        return trace;

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
        return;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int countVertex() {
        int count = 0;
        HashMap<A, ArrayList<A>> bst = this.bfs(this.getRoot());
        for (A key: bst.keySet()){
            count++;
        }
        return count;
    }

    @Override
    public int countEdge() {
        int count = 0;
        HashMap<A, ArrayList<A>> bst = this.bfs(this.getRoot());
        for (ArrayList<A> list: bst.values()){
            for (A node : list){
                count++;
            }
        }
        return count/2;

    }

    @Override
    public Graph buildGraph() {
        return null;
    }


    // no used:
    public boolean find(A value) {
        if (this.root == null) {
            return false;
        } else {
            return findHelper(this.root, value);
        }
    }

    private boolean findHelper(BinaryTreeNode<A> node, A toFind) {
        if (this.comparator.compare(toFind, node.getValue())) {
            if (node.getLeft() == null) {
                return false;
            } else {
                return findHelper(node.getLeft(), toFind);
            }
        } else if (this.comparator.compare(node.getValue(), toFind)) {
            if (node.getRight() == null) {
                return false;
            } else {
                return findHelper(node.getRight(), toFind);
            }
        } else {
            return true;
        }
    }

    public static void main(String[] args) {

    }
}

