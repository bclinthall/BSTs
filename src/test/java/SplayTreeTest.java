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
        assertEquals(26, root.getCount());
        assertEquals(true, root.isValidBST());
        
    }
    @Test public void testSplit(){
        SplayTree root = makeTestTree();
        Node[] pair = root.split(8);
        assertTrue(pair[0].isValidBST());
        assertTrue(pair[1].isValidBST());
        assertEquals("8:{7:{3:{, 4}, }, }", pair[0].toString());
        assertEquals("10:{, 22}", pair[1].toString());
        
    }
    private SplayTree makeTestTree(){
        BstCounter bstCounter = new BstCounter();
        Node root = new SplayTree(7, bstCounter);
        root.insert(4);
        root = root.getRootForFree();
        root.insert(3);
        root = root.getRootForFree();
        root.insert(10);
        root = root.getRootForFree();
        root.insert(8);
        root = root.getRootForFree();
        root.insert(22);
        root = root.getRootForFree();
        SplayTree splayRoot = (SplayTree) root;
        System.out.println("count: " + splayRoot.getCount());
        return splayRoot;
    }
}
