import org.junit.Test;
import static org.junit.Assert.*;

public class AuxiliaryTreeTest{
	@Test public void constructionTest(){
    	BST perfect6 = PerfectBSTMaker.makePerfectBST(6);
    	TangoReferenceTree ref = new TangoReferenceTree(perfect6);
    	System.out.println(PreferredPathGleaner.glean(ref));
    	ref.graph("ref6Before");
    	AuxiliaryTree aux = AuxiliaryTree.fromReferenceTree(ref, new BstCounter(), 0);
    	aux.graph("aux6Before");
    	aux.find(18);
    	aux = (AuxiliaryTree)aux.getRootForFree();
    	aux.graph("aux6After");

    	SplayTree splay = new SplayTree(32, new BstCounter());
    	splay.insert(16);
    	splay = (SplayTree)splay.getRootForFree();
    	splay.insert(8);
    	splay = (SplayTree)splay.getRootForFree();
    	splay.insert(4);
    	splay = (SplayTree)splay.getRootForFree();
    	splay.insert(2);
    	splay = (SplayTree)splay.getRootForFree();
    	splay.insert(1);
    	splay = (SplayTree)splay.getRootForFree();
    	splay.graph("splay");
    }
}
