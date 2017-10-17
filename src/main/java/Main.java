public class Main{
    public static void main(String[] args){
        testSplayTree(1000, 2000);
        testSplayTree(1000, 4000);
        testSplayTree(10000, 20000);
    }
    private static void testSplayTree(int treeSize, int accesses){
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
    }
}
