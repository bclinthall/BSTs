import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.ThreadLocalRandom;
public class AuxiliaryTreeTest{
	@Test public void constructionTest(){
    	RefTree ref = new RefTree(6);
        //ref.find(19);
    	String refPaths = PreferredPathGleaner.glean(ref).toString();

    	AuxiliaryTree aux = new AuxiliaryTree(6);

    	String auxPaths = PreferredPathGleaner.glean(aux).toString();
    	assertTrue(aux.getRoot().isValidBST());
    	assertEquals(refPaths, auxPaths);

    	for (int i=0; i<100; i++){
			ref.find(ThreadLocalRandom.current().nextInt(0, 64));
    	}
    	aux = new AuxiliaryTree((RefNode)ref.getRoot());
//    	aux.graph("Tango");
	}
}
