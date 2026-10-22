package MathParts;

import java.util.ArrayList;

public class Permutation<A> {

    private ArrayList<ArrayList<Integer>> permutation = new ArrayList<>();
    private int n;

    public Permutation(int n){
        this.n = n;

        if (n<=0){
            ArrayList<Integer> remainder = new ArrayList<>();
            ArrayList<Integer> pending = new ArrayList<>();
            generatePermutation(remainder,pending);
        } else {
            ArrayList<Integer> remainder = new ArrayList<>();
            for (int x = 1; x < n + 1; x++) {
                remainder.add(x);
            }

            ArrayList<Integer> pending = new ArrayList<>();
            generatePermutation(remainder, pending);
        }

    }



//    public String toString(){
//        int size = this.permutation.size();
//
//    }

    public int countSize(){
        return this.permutation.size();
    }

    public ArrayList<ArrayList<Integer>> getPermutation(){
        return this.permutation;
    }


//    // I am sosososo good!
     // we use kn here
//    public void generatePermutation(int n, ArrayList<String> items, String output){
//
//        // special case
//        if (n<=0){
//            System.out.println(n + " does not greater than 0\nPlease reenter n");            return;
//        }
//
//        // set up
//        C2_Graphs c2 = new C2_Graphs();
//        CompleteGraph_Kn kn = new CompleteGraph_Kn(c2, n);
//
//        if (n != 1){
//            ArrayList<String> explored = new ArrayList<>();
//            for (Object key : kn.getAdjList(items).keySet()){
//                String pending = "";
//                pending = output + key;
//                ArrayList<String> copy = new ArrayList<>(items);
//                copy.remove(key);
//                generatePermutation(n-1,copy,pending);
//            }
//        } else {
//            String pending = "";
//            pending = output + items.getFirst();
//            String[] strSplit = pending.split("");
//            ArrayList<String> strList = new ArrayList<String>(
//                    Arrays.asList(strSplit));
//
//            this.permutation.add(strList);
//
//        }
//    }


    // edition 2
//    public void generatePermutation( ArrayList<Integer> remainder, ArrayList<Integer> pending){
//        if (remainder.isEmpty()){
//            this.permutation.add(pending);
//            return;
//        }
//
//        for (int v=0 ; v<remainder.size(); v++){
//            ArrayList<Integer> copyR = new ArrayList<>();
//            copyR.addAll(remainder);
//            copyR.remove(v); // remove, we use index but not the exactly content
//
//            ArrayList<Integer> copyP = new ArrayList<>();
//            copyP.addAll(pending);
//            copyP.add(remainder.get(v));
//            generatePermutation(copyR,copyP);
//
////            // edition 3: try to undo pending and remainder
////            remainder.add(v,pending.getLast());
////            pending.removeLast();
//        }
//
//    }

    // edition 3: try to undo pending and remainder
    // only can run n<= 11.
    public void generatePermutation( ArrayList<Integer> remainder, ArrayList<Integer> pending){
        if (remainder.isEmpty()){
            this.permutation.add(new ArrayList<>(pending));
            return;
        }

        for (int v=0 ; v<remainder.size(); v++){

            pending.add(remainder.get(v)); // we should add to pending first, otherwise, x=0 is a different num
            remainder.remove(v); // remove, we use index but not the exactly content
            generatePermutation(remainder,pending);
            remainder.add(v,pending.getLast());
            pending.removeLast();

        }

}
    public static void main(String[] args) {
        Permutation permutation1 = new Permutation(11);
        System.out.println(permutation1.countSize());
        //System.out.println(permutation1.getPermutation());

    }

}

