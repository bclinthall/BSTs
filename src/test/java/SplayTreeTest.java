import org.junit.Test;
import static org.junit.Assert.*;

public class SplayTreeTest{
    @Test public void testFindCounter(){
        SplayTree tree = makeTestTree();
        System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
        System.out.println(tree);
        long before = tree.getOpCount();
        //tree.graph("before7_"+tree.getOpCount());
        tree.find(7); // +3+3
        //tree.graph("before22_"+tree.getOpCount());
        tree.find(22); // +1+1
        //tree.graph("before4_"+tree.getOpCount());
        tree.find(4); // +3+3
        //tree.graph("before3_"+tree.getOpCount());
        tree.find(3);// +1+1
        //tree.graph("end_"+tree.getOpCount());
        System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        assertEquals(16, tree.getOpCount()-before);
        assertEquals(true, tree.getRoot().isValidBST());
        
    }
    @Test public void testSplit(){
        SplayTree tree = makeTestTree();
        Node right = tree.split(8);
        assertTrue(right.isValidBST());
        assertTrue(tree.getRoot().isValidBST());
        assertEquals("8:{7:{3:{, 4}, }, }", tree.getRoot().toString());
        assertEquals("10:{, 22}", right.toString());
        
    }
    private SplayTree makeTestTree(){
        SplayTree tree = new SplayTree();
        tree.insert(7);
        tree.insert(4);
        tree.insert(3);
        tree.insert(10);
        tree.insert(8);
        tree.insert(22);
        System.out.println("count: " + tree.getOpCount());
        return tree;
    }
}
