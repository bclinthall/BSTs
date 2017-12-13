import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.Iterator;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;

public class Main{
    private static Random rand = new Random(1l);
    private static int maxLgN = 20;
    public static void main(String[] args){
        int lgN = 21;
        int n = 1 << lgN;
        List<Integer> accesses = plainRandomSequence(n, 10 * n);
		testTree("Treap", lgN, new QueueKeyTreap(lgN, accesses), "plainRandom", accesses);

        plainRandomTest();
        bitReversalTest();
        sequentialAccessTest();
    }
    private static void sequentialAccessTest(){
        for (int lgN = 7; lgN < maxLgN; lgN++){
            int n = 1 << lgN;
            List<Integer> accesses = new ArrayList<>(n);
            for (int i=0; i<n; i++){
                accesses.add(i);
            }
			testTree("Splay", lgN, new SplayTree(lgN), "sequential", accesses);
			testTree("Tango", lgN, new AuxTree(lgN), "sequential", accesses);
			testTree("Treap", lgN, new QueueKeyTreap(lgN, accesses), "sequential", accesses);
        }
    }
    private static void plainRandomTest(){
		for (int lgN = 7; lgN < maxLgN; lgN++){
    		int n = 1 << lgN;
			List<Integer> accesses = plainRandomSequence(n, 10 * n);
			testTree("Splay", lgN, new SplayTree(lgN), "plainRandom", accesses);
			testTree("Tango", lgN, new AuxTree(lgN), "plainRandom", accesses);
			testTree("Treap", lgN, new QueueKeyTreap(lgN, accesses), "plainRandom", accesses);
		}
    }
    private static void bitReversalTest(){
        for (int lgN = 7; lgN < maxLgN; lgN++){
            int n = 1 << lgN;
            List<Integer> accesses = bitReversalSequence(lgN, 10);
            testTree("Splay", lgN, new SplayTree(lgN), "bitReversal", accesses);
            testTree("Tango", lgN, new AuxTree(lgN), "bitReversal", accesses);
            testTree("Treap", lgN, new QueueKeyTreap(lgN, accesses), "bitReversal", accesses);
        }
    }
    private static void specialRandomTest(){
        for (int lgN = 10; lgN < maxLgN ; lgN++){
            for (int maxFreq = 1000; maxFreq < 16000; maxFreq*=2){
                List<Integer> accesses = specialRandomSequence(1 << lgN, maxFreq);
                testTree("Splay", lgN, new SplayTree(lgN), "specialRandom" + maxFreq, accesses);
                testTree("Tango", lgN, new AuxTree(lgN), "specialRandom" + maxFreq, accesses);
                testTree("Treap", lgN, new QueueKeyTreap(lgN, accesses), "specialRandom" + maxFreq, accesses);
            }
        }
    }
    
    private static void testTree(String treeType, int lgN, BST tree, String sequenceType, List<Integer> accesses){
        //log.add("\"treeType\",\"sequenceType\",\"nodes\",\"accesses\",\"operations\",\"time\"");
        String fileName = String.format("results/%s_%s_%d_%d",
                              treeType,
                              sequenceType,
                              1<<lgN,
                              accesses.size());
        if (new File(fileName).exists()){
			System.out.println(fileName + " already exists");
			return;
        }
        long startTime = System.nanoTime();
        tree.serve(accesses);
        long endTime = System.nanoTime();
        String data = String.format("\"%s\",\"%s\",%d,%d,%d,%.3f",
                              treeType,
                              sequenceType,
                              1<<lgN,
                              accesses.size(),
                              tree.getOpCount(),
                              (endTime - startTime)/1e9);
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.printf("%9d accesses on a %9d node %s tree = %12d ops. Time=%.3f\n", accesses.size(), 1 << lgN, treeType, tree.getOpCount(), (endTime-startTime)/1e9);
    }
    private static List<Integer> plainRandomSequence(int n, int size){
		List<Integer> list = new ArrayList(size);
        Iterator<Integer> randInts = rand.ints(0, n).iterator();
		for (int i=0; i < size; i++){
			list.add(randInts.next());
		}
		return list;
    }
    
    private static List<Integer> specialRandomSequence(int n, int maxFreq){
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> randInts = rand.ints(0, maxFreq).iterator();
        for (int i = 0; i < n; i++) {
            int randFreq = randInts.next();
            for (int j = 0; j < randFreq; j++){
                list.add(i);
            }
        }
        java.util.Collections.shuffle(list, rand);
        
        return list;
    }
    public static List<Integer> bitReversalSequence(int lgN, int repetitions){
        int n = (1 << lgN) - 1;
        List<Integer> once = new ArrayList<>(n);
        for (int i=0; i<n; i++){
            once.add(reverseBits(i, lgN));
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < repetitions; i++){
            list.addAll(once);
        }
        return list;
    }
    private static int reverseBits(int n, int bits){
        int m = 0;
        int bitsMinusOne = bits - 1;
        for (int i = 0; i<bitsMinusOne; i++){
            m = m | (n & 1);
            m = m << 1;
            n = n >> 1;
        }
        m = m | (n & 1);
        return m;
    }
    private static void demonstrateReferenceTree(){
		RefTree refTree = new RefTree(6);
		refTree.graph("referenceTreeBefore");
		refTree.find(19);
		refTree.graph("referenceTreeAfter");

    }
}
