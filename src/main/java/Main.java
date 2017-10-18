import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.Iterator;


public class Main{
    public static void main(String[] args){
        testTrees(1000, 200);

        

        
//        testSplayTree(1000, 4000);
//        testSplayTree(10000, 20000);
    }

    private static void testTrees(int treeSize, int maxFreq){
        List<Integer> accesses = randomAccessArray(treeSize, maxFreq);
        List<Integer> keys = shuffledKeyList(treeSize);
        testSplayTree(accesses, keys);
    }
    
    private static void testSplayTreeReverseOrder(int treeSize, int accesses){
        BstCounter bstCounter = new BstCounter();
        SplayTree node = new SplayTree(0, bstCounter);
        long startTime = System.nanoTime();
        for (int i = treeSize; i > 0; i--){
            node.getRootForFree().insert(i);
        }
        for (int i = 0; i<accesses; i++){
            node.getRootForFree().find(i % treeSize);
        }
        long endTime = System.nanoTime();
        System.out.printf("%9d accesses on a %9d node tree = %12d ops. Time=%f\n", accesses, treeSize, bstCounter.getCount(), (endTime-startTime)/1e9);
        if (!node.getRootForFree().isValidBST()){
            throw new IllegalStateException("search resulted in illegal splay tree state");
        }
        //node.getRootForFree().graph("thousand");
    }

    private static void testSplayTree(List<Integer> accesses, List<Integer> keys){
        BstCounter bstCounter = new BstCounter();
        SplayTree node = new SplayTree(keys.get(0), bstCounter);
        long startTime = System.nanoTime();
        for (int i = 1; i < keys.size(); i++){
            node.getRootForFree().insert(keys.get(i));
        }
        for (int i = 0; i<accesses.size(); i++){
            node.getRootForFree().find(accesses.get(i));
        }
        long endTime = System.nanoTime();
        System.out.printf("%9d accesses on a %9d node tree = %12d ops. Time=%f\n", accesses.size(), keys.size(), bstCounter.getCount(), (endTime-startTime)/1e9);
        node.getRootForFree().graph("hundredRandom");
        if (!node.getRootForFree().isValidBST()){
            throw new IllegalStateException("search resulted in illegal splay tree state");
        }

    }
    private static List<Integer> randomAccessArray(int n, int maxFreq){
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
    private static List<Integer> shuffledKeyList(int n){
        List<Integer> list = new ArrayList();
        for (int i=0; i<n; i++){
            list.add(i);
        }
        java.util.Collections.shuffle(list);
        return list;
    }
}
