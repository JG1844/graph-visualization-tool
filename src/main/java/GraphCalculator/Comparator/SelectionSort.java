package GraphCalculator.Comparator;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectionSort <T> {
    private Comparator<T> comparator;

    public SelectionSort(Comparator<T> comparator){
        this.comparator = comparator;
    }

    public ArrayList<T> sort(ArrayList<T> input){
        ArrayList<T> output = new ArrayList<>();
        while (input.size() >0){
            int locFirst = 0;
            for (int x =0; x<input.size(); x++){
                T currentFisrt = input.get(locFirst);
                T toCompare = input.get(x);
                if (this.comparator.compare(currentFisrt,toCompare)){
                    locFirst = x;
                }
            }
            output.add(input.remove(locFirst));
        }
        return output;
    }

    public static void main(String[] args) {

        // 1st sorter
        System.out.println("new IntDecreasing()");
        SelectionSort<Integer> sorter = new SelectionSort<>(
                new IntDecreasing()
        );
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1,6,5,5,3,4));
        ArrayList<Integer> output = sorter.sort(list);
        System.out.println(output);

        // 2nd sorter
        System.out.println();
        System.out.println("new IntIncreasing()");
        SelectionSort<Integer> sorter1 = new SelectionSort<>(
                new IntIncreasing()
        );
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1,6,5,3,4));
        ArrayList<Integer> output1 = sorter1.sort(list1);
        System.out.println(output1);

//        // 3rd sorter
//        System.out.println();
//        System.out.println("3rd sorter");
//        SelectionSort<String> sorter3 = new SelectionSort<>(
//                new AlphaComparator()
//        );
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("d");
        list3.add("c");
        list3.add("z");
        list3.add("a");

        //ArrayList<String> output3 = sorter3.sort(list3);
        //System.out.println(output3);
    }

}
