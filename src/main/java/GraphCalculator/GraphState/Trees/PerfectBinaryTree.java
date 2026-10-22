package GraphCalculator.GraphState.Trees;

import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.BST;
import GraphCalculator.GraphState.State;
import DataStructure.BinaryTreeNode;
import GraphCalculator.Comparator.IntIncreasing;
import DataStructure.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class PerfectBinaryTree extends State {
    private int level;
    private int count = 1;

    public PerfectBinaryTree(GraphRemoteControl graph, int level) {
        super(graph);
        this.level=level;
    }

    // Switch Patterns
    @Override
    public void perfectBinaryTree(int level){
        return;
    }

    @Override
    public String toString() {
        return "PBT_"+ this.level;
    }

    @Override
//    public int countVertex() {
//        if (level-1<0){
//            return 0;
//        } else if (level-1==0) {
//            return 1;
//        } else {
//            return (level-1)*2;
//        }
//    }

    public int countVertex() {
        ArrayList<Integer> count = new ArrayList<>();
        count.add(0);
        int copy = level;
        cvHelper(level,count);
        return count.getFirst();
    }

    private void cvHelper(int level, ArrayList<Integer> count){
        if (level-1<0){
            return;
        } else if (level-1==0) {
            count.set(0,count.getFirst()+1);
            cvHelper(level-1,count);
        } else {
            count.set(0,count.getFirst()+((level-1)*2));
            cvHelper(level-1,count);

        }
    }


    @Override
    public int countEdge() {
        int v = countVertex();
        // perfect binary tree is a tree: edges = vertices - 1
        return v - 1;
    }

    @Override
    public Graph<Integer> buildGraph() {
        BST<Integer> bst = new BST<Integer>(this.graph,new IntIncreasing());
        HashMap<Integer, ArrayList<Integer>> adjlist = bst.bfs(buildGraphHelper(level));
        Graph<Integer> graph = new Graph<>();
        graph.setAdjacencyList(adjlist);
        return graph;
    }


    public BinaryTreeNode<Integer> buildGraphHelper(int l) {

        if (l == 0) {
            return null;
        }

        BinaryTreeNode<Integer> node =
                new BinaryTreeNode<Integer>(count,null,null);

        count = count+1;

        node.setLeft(buildGraphHelper(l - 1));
        node.setRight(buildGraphHelper(l - 1));

        return node;

    }


}
