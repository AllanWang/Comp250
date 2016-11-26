package a4q2;

import java.util.*;

public class TesterGraph2 {

    private static boolean errorSomewhere = false;

    public static void main(String[] args) {
        //Add tests here
        original();
        triangles();
        //Leave this here
        if (errorSomewhere) print("\nError found somewhere");
        else print("\nAll tests passed!");
    }

    /*
     * Tests are called from a static method, as shown below
     * Each test is created with a new TesterGraph2(), followed by adding vertices, edges, and traversals
     * You can optionally print the graph after vertex/edge insertions, and share answers at the very end
     * If you are creating a new test (see shareAnswer() method)
     */

    //This is the original test from TesterGraph
    private static void original() {
        V[] vertices = new V[12];
        for (int i = 1; i <= 12; i++) {
            vertices[i - 1] = new V(Integer.toString(i), new Square(i));
        }
        new TesterGraph2().addVertices(vertices)
                .addEdges("1 2", "1 5", "1 6", "2 3", "2 4", "6 7", "6 12", "7 8", "7 11", "8 9", "8 10")
                .printGraph()
                .traverse("1", 25, "1 5", "1 6", "1 6 7", "1 6 7 8", "1 6 7 8 9", "1 6 7 8 10", "1 6 7 11", "1 6 12")
                .traverse("7", 200, "7 8 10");
    }

    private static void triangles() {
        V[] vertices = new V[12];
        for (int i = 1; i <= 12; i++) {
            vertices[i - 1] = new V(Integer.toString(i), new Triangle(i, i + 2));
        }
        new TesterGraph2().addVertices(vertices)
                .addEdges("1 2", "1 5", "1 6", "2 3", "2 4", "6 7", "6 12", "7 8", "7 11", "8 9", "8 10")
                .printGraph()
                .traverse("1", 25, "1 6 7", "1 6 7 8", "1 6 7 8 9", "1 6 7 8 10", "1 6 7 11", "1 6 12")
                .traverse("7", 200, "None");
    }

    // I'm too lazy to write Vertex<Shape> every time
    private static class V extends Vertex<Shape> {
        V(String key, Shape element) {
            super(key, element);
        }
    }

    private static class Test {
        String key;
        float threshold;
        List<String> results;
        boolean valid;

        Test(String s, float t, List<String> r, boolean b) {
            key = s;
            threshold = t;
            results = r;
            valid = b;
        }
    }

    private final ShapeGraph graph = new ShapeGraph();
    private List<Test> tests = new ArrayList<>();

    /**
     * Add vertices to graph; should be done first
     * @param vertices array of vertices
     * @return this class
     */
    public TesterGraph2 addVertices(V... vertices) {
        for (V vertex : vertices) graph.addVertex(vertex.getKey(), vertex);
        return this;
    }

    /**
     * Add edges to graph that already has vertices
     *
     * @param edges both start and end key separated by space
     * @return current class for easy method calling
     */
    public TesterGraph2 addEdges(String... edges) {
        for (String s : edges) {
            String[] pair = s.split(" ");
            if (pair.length != 2) continue;
            graph.addEdge(pair[0], pair[1]);
        }
        return this;
    }

    /**
     * Optional method to print the graph
     * @return this class
     */
    public TesterGraph2 printGraph() {
        print("The Graph");
        System.out.println(graph);
        return this;
    }

    /**
     * Graph traversal; can be done multiple times for a given graph
     * If traversing numerous times doesn't work, make sure you've reset the visited boolean in your graph class
     * @param key key of vertex to start at
     * @param threshold threshold area
     * @param answers optional array of Strings reflecting the expected output of this result
     * @return this class
     */
    public TesterGraph2 traverse(String key, float threshold, String... answers) {
        LinkedList<LinkedList<String>> result = graph.traverseFrom(key, threshold);
        print("Traversing from %s with a threshold of %.2f", key, threshold);
        boolean error = false;
        List<String> results = new ArrayList<>();
        if (result.size() == 0) {
            results.add("None");
            print("\tNone");
            if (answers != null && (answers.length != 1 || !answers[0].equals("None"))) {
                print("\t\tIncorrect");
                error = true;
            }
        } else {
            for (int i = 0; i < result.size(); i++) {
                LinkedList<String> path = result.get(i);
                String pathS = "";
                for (String s : path) pathS += s + " ";
                pathS = pathS.trim(); //remove trailing whitespace
                print("\t%s", pathS);
                results.add(pathS);
                if (answers != null && answers.length != 0) {
                    if (answers.length <= i) {
                        error = true;
                        print("\t\tThere aren't this many paths");
                        continue;
                    }
                    if (!pathS.equals(answers[i])) {
                        error = true;
                        print("\t\tAnswer should be %s", answers[i]);
                    }
                }
            }
        }
        if (error) {
            errorSomewhere = true;
            print("\n\tAn error was found, correct answer is");
            for (String s : answers) print("\t\t%s", s);
        } else if (answers != null && answers.length != 0) {
            print("\n\tCorrect!\n");
        }
        tests.add(new Test(key, threshold, results, !error));
        return this;
    }

    /**
     * for those who are making their own tests, use this to return a line that you can easily copy and paste
     * into your test method
     * should be the last method call, hence void return
     */
    public void shareAnswers() {
        if (tests.size() == 0) {
            print("No tests found; please call the traverse method");
            return;
        }
        print("Below are the answers to your traversal tests");
        for (Test t : tests) {
            print("\tTest from key %s with threshold %.2f", t.key, t.threshold);
            if (!t.valid) print("\t\tResult does not match answers given in the test method");
            for (int i = 0; i < t.results.size(); i++) {
                if (i == 0) System.out.print("\t\t");
                System.out.print("\"" + t.results.get(i) + "\"");
                if (i != t.results.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
    }

    /**
     * Just a print helper that you see in all my classes
     * @param s String with formats
     * @param o optional args
     */
    private static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

}
















