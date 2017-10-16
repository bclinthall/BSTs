/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
    @Test public void testNodeReadout(){
        Node seven = new Node(7);
        seven.setLeft(new Node(4));
        seven.getLeft().setLeft(new Node(3));
        seven.setRight(new Node(10));
        seven.getRight().setLeft(new Node(8));
        seven.getRight().setRight(new Node(22));
        String actual   = seven.toString();
        System.out.println(actual);
        String expected = "7:{4:{3, }, 10:{8, 22}}";
        assertEquals(expected, actual);
    }

    @Test public void testInvalidBST(){
        Node seven = new Node(7);
        seven.setLeft(new Node(4));
        seven.getLeft().setLeft(new Node(3));
        seven.setRight(new Node(10));
        seven.getRight().setLeft(new Node(8));
        seven.getRight().setRight(new Node(9));
        assertFalse(seven.isValidBST());
    }

    @Test public void testInsert(){
        Node seven = makeTestTree();
        String actual   = seven.toString();
        System.out.println(actual);
        String expected = "7:{4:{3, }, 10:{8, 22}}";
        assertEquals(expected, actual);
    }
    @Test public void testRotateOnRight(){
        Node seven = makeTestTree();
        Node two = new Node(2);
        two.insert(seven);
        System.out.println(two);
        seven.getRight().rotateUp();
        System.out.println(two);
        assertTrue(two.isValidBST());
    }

    private Node makeTestTree(){
        Node seven = new Node(7);
        seven.insert(4);
        seven.insert(3);
        seven.insert(10);
        seven.insert(8);
        seven.insert(22);
        return seven;
    }
}
