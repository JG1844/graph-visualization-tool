package MathParts;

import java.util.ArrayList;

public class Subset {
    private ArrayList<ArrayList<Integer>> trueTable;
    private ArrayList<ArrayList<Integer>> subset;
    private ArrayList<Integer> numList=new ArrayList<>();
    private int n;
    private int size;

    public Subset(int n){
        this.n = n;
        this.size = (int) Math.pow(2, n);
        this.trueTable = generateTrueSubset();

        for (int x =1; x<n+1; x++){
            this.numList.add(x);
        }
        this.subset = generateSubset();
    }

    public ArrayList<ArrayList<Integer>> getTrueTable(){
        return this.trueTable;
    }
    public ArrayList<ArrayList<Integer>> getSubset(){
        return this.subset;
    }

    public ArrayList<ArrayList<Integer>> generateTrueSubset(){

        // set up
        ArrayList<ArrayList<Integer>> trueTable = new ArrayList<>();

        // add 0 to the list
        ArrayList<Integer> list = new ArrayList<>();
        for (int t=0;t<this.n;t++){
            list.add(0);
        }

        trueTable.add(new ArrayList<>(list));
        while (trueTable.size()!=this.size){
            if (list.getLast()==0){
                list.set(list.size()-1,1);
                trueTable.add(new ArrayList<>(list));
            } else {
                list.set(list.size()-1,0);
                binaire(list,list.size()-2);
                trueTable.add(new ArrayList<>(list));
            }
        }

        return trueTable;
    }

    private void binaire(ArrayList<Integer> list, int i){


        if (list.get(i)==1){
            list.set(i,0);
            binaire(list,i-1);
        }else {
            list.set(i,1);
        }
    }

    public boolean compare(ArrayList<ArrayList<Integer>> s1, ArrayList<ArrayList<Integer>> s2){

        for (ArrayList<Integer> list : s1){
            if (!s2.contains(list)){
                return false;
            }
        }

        return true;
    }

    private boolean compareHelper(ArrayList<Integer> a1, ArrayList<Integer> a2){
        for (int value : a1){
            if (!a2.contains(value)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<ArrayList<Integer>> generateSubset(){
        ArrayList<ArrayList<Integer>> subset = new ArrayList<>();

        for (ArrayList<Integer> list : this.trueTable){
            ArrayList<Integer> temp = new ArrayList<>();
            for (int x=0; x<list.size(); x++){
                if (list.get(x)==1){
                    temp.add(this.numList.get(x));
                }
            }
            subset.add(temp);
        }

        return subset;
    }

    public static void main(String[] args) {
        Subset subset1 = new Subset(4);
        ArrayList<ArrayList<Integer>> list = subset1.getTrueTable();
        System.out.println(list);

        ArrayList<ArrayList<Integer>> s = subset1.getSubset();
        System.out.println(s);
    }

}
