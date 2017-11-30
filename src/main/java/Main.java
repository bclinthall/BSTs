import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.Iterator;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        List<String> log = new ArrayList<>();
        log.add("\"type\",\"nodes\",\"accesses\",\"operations\",\"time\"");
        for (int lgN = 10; lgN < 21; lgN++){
            testTrees(lgN, 1000, log);
            testTrees(lgN, 1000, log);
            testTrees(lgN, 1000, log);
            testTrees(lgN, 5000, log);
            testTrees(lgN, 10000, log);
        }
        try {
            Files.write(Paths.get("results/log.csv"), log);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void testTrees(int lgN, int maxFreq, List<String> log){
        List<Integer> accesses = randomAccessSequence(1 << lgN, maxFreq);
        testTree("Splay", lgN, new SplayTree(lgN), maxFreq, accesses, log);
    }
    
    private static void testTree(String type, int lgN, BST tree, int maxFreq, List<Integer> accesses, List<String> log){
        long startTime = System.nanoTime();
        for (int i = 0; i<accesses.size(); i++){
            tree.find(accesses.get(i));
        }
        long endTime = System.nanoTime();
        log.add(String.format("\"%s\",%d,%d,%d,%d,%d",
                              type,
                              lgN,
                              accesses.size(),
                              maxFreq,
                              tree.getOpCount(),
                              (endTime - startTime)/1e9));
        System.out.printf("%9d accesses on a %9d node tree = %12d ops. Time=%f\n", accesses.size(), 1 < lgN, tree.getOpCount(), (endTime-startTime)/1e9);
        if (!tree.getRoot().isValidBST()){
            throw new IllegalStateException("search resulted in illegal " + type + " tree state");
        }
    }
    private static List<Integer> randomAccessSequence(int n, int maxFreq){
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> randInts = new Random().ints(0, maxFreq).iterator();
        for (int i = 0; i < n; i++) {
            int randFreq = randInts.next();
            for (int j = 0; j < randFreq; j++){
                list.add(i);
            }
        }
        java.util.Collections.shuffle(list);
        
        return list;
    }
    private static void demonstrateReferenceTree(){
		RefTree refTree = new RefTree(6);
		refTree.graph("referenceTreeBefore");
		refTree.find(19);
		refTree.graph("referenceTreeAfter");

    }
}
