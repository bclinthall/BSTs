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
    public static void main(String[] args){
        plainRandomTest();
    }
    private static void plainRandomTest(){
		for (int lgN = 7; lgN < 11; lgN++){
    		int n = 1 << lgN;
			List<Integer> accesses = plainRandomSequence(n, 10 * n);
			testTree("Splay", lgN, new SplayTree(lgN), "plainRandom", accesses);
			testTree("Tango", lgN, new AuxTree(lgN), "plainRandom", accesses);
		}
    }

    private static void specialRandomTest(){
        for (int lgN = 10; lgN < 21; lgN++){
            for (int maxFreq = 1000; maxFreq < 16000; maxFreq*=2){
                List<Integer> accesses = specialRandomSequence(1 << lgN, maxFreq);
                testTree("Splay", lgN, new SplayTree(lgN), "specialRandom" + maxFreq, accesses);
                testTree("Tango", lgN, new AuxTree(lgN), "specialRandom" + maxFreq, accesses);
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
    private static void demonstrateReferenceTree(){
		RefTree refTree = new RefTree(6);
		refTree.graph("referenceTreeBefore");
		refTree.find(19);
		refTree.graph("referenceTreeAfter");

    }
}
