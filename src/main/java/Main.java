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
    private static int maxLgN = 21;
    public static void main(String[] args){
        //refVsAux(18);
        
        for (int lgN = 7; lgN < maxLgN; lgN++){
			int n = 1 << lgN;
            plainRandomTest(lgN, n);
            bitReversalTest(lgN, n);
            sequentialAccessTest(lgN,n);
        }
    }

    private static IntStream sequentialStream(int n){
        return IntStream.range(0,n);
    }
    private static void sequentialAccessTest(final int lgN, final int n){
		testTree("Splay", lgN, () -> new SplayTree(lgN), "sequential", sequentialStream(n), n);
		testTree("Tango", lgN, () -> new AuxTree(lgN), "sequential", sequentialStream(n), n);
        testTree("Vanilla", lgN, () -> new BST(lgN), "sequential", sequentialStream(n), n);
		if(lgN<=18){
			testTree("Treap", lgN, () -> new QueueKeyTreap(lgN, sequentialStream(n)), "sequential", sequentialStream(n), n);
		}
    }

    private static IntStream plainRandomStream(int n){
        return new Random(1l).ints(0,n).limit(n*10);
    }
    private static void plainRandomTest(final int lgN, final int n){
		testTree("Splay", lgN, () -> new SplayTree(lgN), "plainRandom", plainRandomStream(n), n*10);
		testTree("Tango", lgN, () -> new AuxTree(lgN), "plainRandom", plainRandomStream(n), n*10);
		testTree("Treap", lgN, () -> new QueueKeyTreap(lgN, plainRandomStream(n)), "plainRandom", plainRandomStream(n), n*10);
    }
    private static IntStream bitReversalStream(int lgN, int n){
		return IntStream.range(0, n).map(x -> reverseBits(x, lgN));
    }
    private static void bitReversalTest(final int lgN, final int n){
        testTree("Splay", lgN, () -> new SplayTree(lgN), "bitReversal", bitReversalStream(lgN, n), n);
        testTree("Tango", lgN, () -> new AuxTree(lgN), "bitReversal", bitReversalStream(lgN, n), n);
        testTree("Vanilla", lgN, () -> new BST(lgN), "bitReversal", bitReversalStream(lgN, n), n);
        testTree("Treap", lgN, () -> new QueueKeyTreap(lgN, bitReversalStream(lgN, n)), "bitReversal", bitReversalStream(lgN, n), n);
    }
    
    private static void testTree(String treeType, int lgN, TreeMaker treeMaker, String sequenceType, IntStream accesses, int m){
        //log.add("\"treeType\",\"sequenceType\",\"nodes\",\"accesses\",\"operations\",\"time\"");
        String fileName = String.format("results/%s_%s_%d_%d",
                              treeType,
                              sequenceType,
                              1<<lgN,
                              m);
        if (new File(fileName).exists()){
			System.out.println(fileName + " already exists");
			return;
        }
        BST tree = treeMaker.makeTree();
        System.out.println("Beginning test for " + fileName);
        long startTime = System.nanoTime();
        tree.serve(accesses);
        long endTime = System.nanoTime();
        String data = String.format("\"%s\",\"%s\",%d,%d,%d,%.3f",
                              treeType,
                              sequenceType,
                              1<<lgN,
                              m,
                              tree.getOpCount(),
                              (endTime - startTime)/1e9);
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.printf("%9d accesses on a %9d node %s tree = %12d ops. Time=%.3f\n",
                          m,
                          1 << lgN,
                          treeType,
                          tree.getOpCount(),
                          (endTime-startTime)/1e9);
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
	private static void compare(RefTree ref, AuxTree aux){
    	List<List<Integer>> refPaths = PreferredPathGleaner.glean(ref);
		List<List<Integer>> auxPaths = PreferredPathGleaner.glean(aux);
		System.out.println("+++++++++++ They match: " + refPaths.equals(auxPaths));
	}
	private static void refVsAux(int lgN){
    	RefTree ref = new RefTree(lgN);
		AuxTree aux = new AuxTree(lgN);
    	ref.serve(plainRandomStream(1<<lgN));
    	aux.serve(plainRandomStream(1<<lgN));
    	compare(ref, aux);
	}

}
@FunctionalInterface
interface TreeMaker{
    BST makeTree();
}
