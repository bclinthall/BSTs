/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void testNodeReadout(){
        BST seven = new BST(7);
        seven.setLeft(new BST(4));
        seven.getLeft().setLeft(new BST(3));
        seven.setRight(new BST(10));
        seven.getRight().setLeft(new BST(8));
        seven.getRight().setRight(new BST(22));
        String actual   = seven.toString();
        System.out.println(actual);
        String expected = "7:{4:{3, }, 10:{8, 22}}";
        assertEquals(expected, actual);
    }

    @Test public void testInvalidBST(){
        BST seven = new BST(7);
        seven.setLeft(new BST(4));
        seven.getLeft().setLeft(new BST(3));
        seven.setRight(new BST(10));
        seven.getRight().setLeft(new BST(8));
        seven.getRight().setRight(new BST(9));
        assertFalse(seven.isValidBST());
    }

    @Test public void testInsert(){
        BST seven = makeTestTree();
        String actual   = seven.toString();
        System.out.println(actual);
        String expected = "7:{4:{3, }, 10:{8, 22}}";
        assertEquals(expected, actual);
    }
    @Test public void testRotateOnRight(){
        BST seven = makeTestTree();
        BST two = new BST(2);
        two.insert(seven);
        System.out.println(two);
        seven.getRight().rotateUp();
        System.out.println(two);
        assertTrue(two.isValidBST());
        String expected = "2:{, 10:{7:{4:{3, }, 8}, 22}}";
        assertEquals(two.toString(), expected);
    }

    private BST makeTestTree(){
        BST seven = new BST(7);
        seven.insert(4);
        seven.insert(3);
        seven.insert(10);
        seven.insert(8);
        seven.insert(22);
        return seven;
    }

}
