package MathParts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class TestClass<A> {

    private boolean compareArrayList(ArrayList<A> a1, ArrayList<A> a2){
        if (a1.size()!=a2.size()){
            return false;
        }

        for (A x : a1){
            if (!a2.contains(x)){
                return false;
            }
        }
        return true;
    }

    private boolean comparePermutation(ArrayList<ArrayList<Integer>> a1, ArrayList<ArrayList<Integer>> a2){
        if (a1.size()!=a2.size()){
            return false;
        }

        for (ArrayList<Integer> x : a1){
            if (!a2.contains(x)){
                return false;
            }
        }
        return true;
    }





    @Test
    public void testPermutationNeg() {
        Permutation p1 = new Permutation(0);

        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        ArrayList<ArrayList<Integer>> actual = p1.getPermutation();

        assertTrue(comparePermutation(actual, expected));
    }

    @Test
    public void testPermutation0() {
        Permutation p1 = new Permutation(0);

        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        ArrayList<ArrayList<Integer>> actual = p1.getPermutation();

        assertTrue(comparePermutation(actual, expected));
    }

    @Test
    public void testPermutation3() {
        Permutation p1 = new Permutation(3);

        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();

        expected.add(new ArrayList<>(List.of(1, 2, 3)));
        expected.add(new ArrayList<>(List.of(1, 3, 2)));
        expected.add(new ArrayList<>(List.of(2, 1, 3)));
        expected.add(new ArrayList<>(List.of(2, 3, 1)));
        expected.add(new ArrayList<>(List.of(3, 1, 2)));
        expected.add(new ArrayList<>(List.of(3, 2, 1)));

        ArrayList<ArrayList<Integer>> actual = p1.getPermutation();

        assertTrue(comparePermutation(actual, expected));
    }

    @Test
    public void testPermutation6() {
        Permutation p1 = new Permutation(4);

        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();

        expected.add(new ArrayList<>(List.of(1, 2, 3, 4)));
        expected.add(new ArrayList<>(List.of(1, 2, 4, 3)));
        expected.add(new ArrayList<>(List.of(1, 3, 2, 4)));
        expected.add(new ArrayList<>(List.of(1, 3, 4, 2)));
        expected.add(new ArrayList<>(List.of(1, 4, 2, 3)));
        expected.add(new ArrayList<>(List.of(1, 4, 3, 2)));

        expected.add(new ArrayList<>(List.of(2, 1, 3, 4)));
        expected.add(new ArrayList<>(List.of(2, 1, 4, 3)));
        expected.add(new ArrayList<>(List.of(2, 3, 1, 4)));
        expected.add(new ArrayList<>(List.of(2, 3, 4, 1)));
        expected.add(new ArrayList<>(List.of(2, 4, 1, 3)));
        expected.add(new ArrayList<>(List.of(2, 4, 3, 1)));

        expected.add(new ArrayList<>(List.of(3, 1, 2, 4)));
        expected.add(new ArrayList<>(List.of(3, 1, 4, 2)));
        expected.add(new ArrayList<>(List.of(3, 2, 1, 4)));
        expected.add(new ArrayList<>(List.of(3, 2, 4, 1)));
        expected.add(new ArrayList<>(List.of(3, 4, 1, 2)));
        expected.add(new ArrayList<>(List.of(3, 4, 2, 1)));

        expected.add(new ArrayList<>(List.of(4, 1, 2, 3)));
        expected.add(new ArrayList<>(List.of(4, 1, 3, 2)));
        expected.add(new ArrayList<>(List.of(4, 2, 1, 3)));
        expected.add(new ArrayList<>(List.of(4, 2, 3, 1)));
        expected.add(new ArrayList<>(List.of(4, 3, 1, 2)));
        expected.add(new ArrayList<>(List.of(4, 3, 2, 1)));

        ArrayList<ArrayList<Integer>> actual = p1.getPermutation();

        assertTrue(comparePermutation(actual, expected));
    }




}




