import org.junit.Test;
import static org.junit.Assert.*;

public class PerfectBSTMakerTest{
    String perfectSix =  "32:{16:{8:{4:{2:{1, 3}, 6:{5, 7}}, 12:{10:{9, 11}, 14:{13, 15}}}, 24:{20:{18:{17, 19}, 22:{21, 23}}, 28:{26:{25, 27}, 30:{29, 31}}}}, 48:{40:{36:{34:{33, 35}, 38:{37, 39}}, 44:{42:{41, 43}, 46:{45, 47}}}, 56:{52:{50:{49, 51}, 54:{53, 55}}, 60:{58:{57, 59}, 62:{61, 63}}}}}";
	@Test public void test6(){
		BST node = PerfectBSTMaker.makePerfectBST(6);
		assertEquals(perfectSix, node.toString());
	}
	@Test public void perfectSplayTreeTest(){
		BST node = PerfectBSTMaker.makePerfectBST(6);
		BstCounter counter = new BstCounter();
		SplayTree splayTree = new SplayTree(node, counter);
		assertEquals(0, counter.getCount());
		assertEquals(perfectSix, splayTree.toString());
		//splayTree.graph("beforeSplay");
	}
	@Test public void referenceTreeTest(){
		BST node = PerfectBSTMaker.makePerfectBST(6);
		TangoReferenceTree refTree = new TangoReferenceTree(node);
		assertEquals(perfectSix, refTree.toString());
	}
}

