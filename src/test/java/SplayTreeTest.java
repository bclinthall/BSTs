import org.junit.Test;
import static org.junit.Assert.*;

public class SplayTreeTest{
    @Test public void testFindCounter(){
        SplayTree root = makeTestTree();
        System.out.println(root);
        root.find(7); // +0
        root =(SplayTree) root.getRootForFree();
        root.find(22); // +4
        root = (SplayTree) root.getRootForFree();
        root.find(4); // +2
        root = (SplayTree) root.getRootForFree();
        root.find(3);// +6
        root = (SplayTree) root.getRootForFree();
        assertEquals(12, root.getCount());
        assertEquals(true, root.isValidBST());
        
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
