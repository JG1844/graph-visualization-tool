package GraphCalculator.TestClass;

import GraphCalculator.GraphIO.GraphMLExporter;
import GraphCalculator.GraphIO.GraphMLImporter;
import GraphCalculator.GraphRemoteControl;
import GraphCalculator.GraphState.CustomGraph;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Round-trip check: build a family graph -> export GraphML -> import ->
 * verify vertex/edge counts, labels and the full automorphism group.
 * Run with: java GraphCalculator.TestClass.GraphMLRoundTripTest
 */
public class GraphMLRoundTripTest {
    private static int failures = 0;

    public static void main(String[] args) throws Exception {
        File dir = Files.createTempDirectory("graphml-roundtrip").toFile();
        check(dir, "K5", 5, 10, 120, g -> g.completeGraph_Kn(5));
        check(dir, "Petersen5", 10, 15, 120, g -> g.petersenGraph(5));
        check(dir, "K32", 5, 6, 12, g -> g.biPartiteGraph_Kmn(3, 2));
        check(dir, "Line5", 5, 4, 2, g -> g.line(5));
        check(dir, "Star7", 8, 7, 5040, g -> g.starTree(7));
        if (failures == 0) {
            System.out.println("\nALL ROUND-TRIP CHECKS PASSED");
        } else {
            System.out.println("\n" + failures + " CHECK(S) FAILED");
            System.exit(1);
        }
    }

    private static void check(File dir, String name, int expV, int expE, int expFullAuto,
                              Consumer<GraphRemoteControl> builder) throws Exception {
        System.out.println("--- " + name + " ---");
        GraphRemoteControl original = new GraphRemoteControl();
        builder.accept(original);

        // original labels as strings
        Set<String> origLabels = new HashSet<>();
        for (Object k : original.buildGraph().getAdjacencyList().keySet()) {
            origLabels.add(String.valueOf(k));
        }
        check("original vertex count", expV, origLabels.size());

        File file = new File(dir, name + ".graphml");
        GraphMLExporter.export(original.buildGraph(), file, original.getStateName());

        Map<String, List<String>> adj = GraphMLImporter.importGraph(file);
        check("imported vertex count", expV, adj.size());
        int edgeCount = 0;
        for (List<String> l : adj.values()) edgeCount += l.size();
        check("imported edge count", expE, edgeCount / 2);
        check("labels preserved", origLabels, adj.keySet());

        // automorphism on the imported graph (full automorphism group)
        GraphRemoteControl imported = new GraphRemoteControl();
        imported.setState(new CustomGraph<>(imported, adj, "imported:" + name));
        imported.automorphism();
        int importedAuto = imported.getSizeAuto();
        check("imported automorphism count", expFullAuto, importedAuto);

        // report the built-in family automorphism for comparison (skip Petersen: 10! slow)
        int originalAuto = -1;
        if (!"Petersen5".equals(name)) {
            original.automorphism();
            originalAuto = original.getSizeAuto();
        }
        System.out.println("  original(auto)=" + originalAuto
                + " imported(full auto)=" + importedAuto
                + " labels=" + origLabels + " OK");
    }

    private static void check(String label, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            failures++;
            System.out.println("  FAIL " + label + ": expected=" + expected + " actual=" + actual);
        }
    }
}
