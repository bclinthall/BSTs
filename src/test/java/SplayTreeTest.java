import org.junit.Test;
import static org.junit.Assert.*;

public class SplayTreeTest{
    @Test public void testFindCounter(){
        SplayTree root = makeTestTree();
        System.out.println(root);
        root.find(7); // +0
        root = (SplayTree) root.getRootForFree();
        root.find(3); // +4
        root = (SplayTree) root.getRootForFree();
        root.find(4); // +2
        root = (SplayTree) root.getRootForFree();
        root.find(22);// +6
        root = (SplayTree) root.getRootForFree();
        assertEquals(12, root.getCount());
        
    }
    @Test public void largeSplayTree(){
        testSplayTree(1000, 2000);
        testSplayTree(1000, 4000);
        testSplayTree(10000, 20000);
    }
    private void testSplayTree(int treeSize, int accesses){
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
    }
    private SplayTree makeTestTree(){
        BstCounter bstCounter = new BstCounter();
        SplayTree root = new SplayTree(7, bstCounter);
        root.insert(4);
        root.insert(3);
        root.insert(10);
        root.insert(8);
        root.insert(22);
        return root;
    }
}
