import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class ReverseBitSequenceTest{
    @Test public void test3_2(){
        List<Integer> list = Main.bitReversalSequence(3,2);
        List<Integer> expected = Arrays.asList(
            new Integer[]{0, 4, 2, 6, 1, 5, 3, 0, 4, 2, 6, 1, 5, 3}
        );
        assertEquals(expected, list);
    }
}
