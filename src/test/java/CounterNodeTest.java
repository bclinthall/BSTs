import org.junit.Test;
import static org.junit.Assert.*;

public class CounterNodeTest{
    @Test public void testFindCounter(){
        CounterNode seven = makeTestTree();
        System.out.println(seven);
        seven.find(7); // +0
        seven.find(3); // +2
        seven.find(4); // +1
        seven.find(22);// +2
        assertEquals(5, seven.getCount());
        
    }

    private CounterNode makeTestTree(){
        BstCounter bstCounter = new BstCounter();
        CounterNode seven = new CounterNode(7, bstCounter);
        seven.insert(4);
        seven.insert(3);
        seven.insert(10);
        seven.insert(8);
        seven.insert(22);
        return seven;
    }
}
