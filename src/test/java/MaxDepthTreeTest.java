import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.ThreadLocalRandom;
public class MaxDepthTreeTest{
    @Test public void constructorTest(){
		MaxDepthTree tree = new MaxDepthTree(6);
    }
    @Test public void preservationTest(){
        int lgN = 7;
		MaxDepthTree tree = new MaxDepthTree(lgN);
    	for (int i=0; i<1000; i++){
			tree.find(ThreadLocalRandom.current().nextInt(0,(int)Math.pow(2, lgN)));
    	}
		tree.graph("MaxDepthTree");
    }
}
        
