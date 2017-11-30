import org.junit.Test;
import static org.junit.Assert.*;

public class AuxiliaryTreeTest{
	@Test public void constructionTest(){
    	RefTree ref = new RefTree(6);
    	System.out.println(PreferredPathGleaner.glean(ref));
    	ref.find(19);

//    	AuxiliaryTree aux = AuxiliaryTree.fromReferenceTree(ref, new BstCounter(), 0);
//    	aux.graph("aux6Before");
//    	aux.find(18);
//    	aux = (AuxiliaryTree)aux.getRootForFree();
//    	aux.graph("aux6After");

/*    	SplayTree splay = new SplayTree();
    	splay.insert(32);
    	splay.insert(16);
    	splay.insert(8);
    	splay.insert(4);
    	splay.insert(2);
    	splay.insert(1);
    	splay.graph("splay");*/
    }
}
