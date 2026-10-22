package GraphCalculator.TestClass;

import GraphCalculator.GraphRemoteControl;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;  // Add this line

public class Test_Automorphism {


    // BinaryTree
    @Test
    public void BinaryTree3(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.perfectBinaryTree(3);
        graphs.automorphism();

        // 7-node perfect binary tree: root fixed; two child subtrees swap,
        // each internal node swaps its two leaves -> 2 x 2 x 2 = 8 automorphisms
        assertEquals(8, graphs.getSizeAuto());
    }

    // Star
    @Test
    public void star4(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.starTree(4);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(24, graphs.getSizeAuto());
    }

    // Petersen (Not good)

    // Kmn
    @Test
    public void k23(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.biPartiteGraph_Kmn(2,3);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(2, graphs.getSizeAuto());
    }
    @Test
    public void k32(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.biPartiteGraph_Kmn(3,2);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(6, graphs.getSizeAuto());
    }



    // Kn
    @Test
    public void k3(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.completeGraph_Kn(3);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(6, graphs.getSizeAuto());
    }

    // Line
    @Test
    public void line5(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.line(5);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(2, graphs.getSizeAuto());
    }

    @Test
    public void line3(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.line(3);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(2, graphs.getSizeAuto());
    }


    @Test
    public void line7(){
        GraphRemoteControl graphs = new GraphRemoteControl();
        graphs.line(7);
        graphs.automorphism();

        // Now you can use assertions
        assertEquals(2, graphs.getSizeAuto());
    }
}