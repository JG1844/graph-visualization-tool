package DataStructure;


import MathParts.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Graph<N> {
    private HashMap<N, ArrayList<N>> adjacencyList;

    public Graph(){
        this.adjacencyList = new HashMap<>();
    }

    public void setAdjacencyList(HashMap<N, ArrayList<N>> newAdj){
        this.adjacencyList = newAdj;
    }

    public void addEdge(N from, N to){
        this.addNode(from);
        this.addNode(to);
        this.adjacencyList.get(from).add(to);
    }

    public void addNode(N node){
        if (!this.adjacencyList.containsKey(node)){
            this.adjacencyList.put(node,new ArrayList<>());
        }
    }

    public HashMap<N, ArrayList<N>> getAdjacencyList() {
        return adjacencyList;
    }


    public void addBidirectionalEdge(N node1, N node2){
        this.addNode(node1);
        this.addNode(node2);
        this.adjacencyList.get(node1).add(node2);
        this.adjacencyList.get(node2).add(node1);
    }


    public String toString(){
        String out="";
        for (N node : this.adjacencyList.keySet()){
            out+=node+": [";
            for (N to : this.adjacencyList.get(node)){
                out+=to+" ";
            }
            out+="]\n";
        }
        return out;
    }

    public boolean validPath(ArrayList<N> path){
        for (int x=0; x<path.size()-1;x++){
            N from = path.get(x);
            N to = path.get(x+1);
            if (!connected(from,to)){
                return false;
            }
        }
        return true;
    }

    public boolean connected(N from, N to){
        for (N toVal : this.adjacencyList.get(from)){
            if (toVal.equals(to)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A","B");
        graph.addEdge("A","C");
        graph.addEdge("B","A");
        graph.addEdge("B","D");
        graph.addEdge("D","E");
        graph.addEdge("C","E");
        graph.addEdge("E","F");
        graph.addEdge("F","G");
        System.out.println(graph);
        System.out.println();

        ArrayList<String> path1 = new ArrayList<>(Arrays.asList("A","C","E"));
        System.out.println(graph.validPath(path1));

        ArrayList<String> path2 = new ArrayList<>(Arrays.asList("A","C","E","F","G"));
        System.out.println(graph.validPath(path2));

        ArrayList<String> path3 = new ArrayList<>(Arrays.asList("B","C","E"));
        System.out.println(graph.validPath(path3));

        ArrayList<String> path4 = new ArrayList<>(Arrays.asList("A","C","E","D"));
        System.out.println(graph.validPath(path4));



    }

}

