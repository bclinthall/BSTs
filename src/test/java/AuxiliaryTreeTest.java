import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;
import java.util.List;
import java.util.List;
import java.util.ArrayList;
public class AuxiliaryTreeTest{
    @Test public void find10(){
        findAndCompare(6, 10, false);
    }
    @Test public void find48(){
        findAndCompare(6, 48, false);
    }
    @Test public void find56(){
        findAndCompare(6, 56, false);
    }
    @Test public void find60(){
        findAndCompare(6,60,false);
    }
    
    @Test public void find62(){
        findAndCompare(6,62,false);
    }
    @Test public void find63(){
        findAndCompare(6, 63, false);
    }
	@Test public void constructionTest(){
		refVsAux(6, 0, false, false);
	}
    private void findAndCompare(int lgN, int toFind,  boolean graph){
		AuxTree aux = new AuxTree(lgN);
		if(graph) aux.graph("before"+toFind);
		aux.find(toFind);
		if(graph) aux.graph("found"+toFind);
		RefTree ref = new RefTree(lgN);
		ref.find(toFind);
		compare(ref, aux, false);
    }
    public void graphTwo(){
        int lgN = 6;
    	RefTree ref = new RefTree(lgN);
		AuxTree aux = new AuxTree(lgN);
		List<Integer> seq = generateRandomAccesses(lgN, 100);
		ref.serve(seq);
		aux.serve(seq);
		ref.graph("ref");
		aux.graph("aux");
    }
    @Test public void twoAccesses() {

		RefTree ref = new RefTree(6);
		AuxTree aux = new AuxTree(6);

        ref.find(17);
		ref.find(10);
		aux.find(17);
		aux.find(10);
		compare(ref, aux, false);
    }
	@Test public void afterAccessTests(){
		refVsAux(8, 10000, true, false);
	}
	private List<Integer> generateRandomAccesses(int lgN, int size){
		int max = 1 << lgN;
		List<Integer> accessSequence = new ArrayList<>();
		Random rand = new Random(1l);
		for (int i=0; i < size; i++){
            accessSequence.add(rand.nextInt(max));
		}
		return accessSequence;
	}
	private void refVsAux(int lgN, int accesses, boolean log, boolean graph){
    	RefTree ref = new RefTree(lgN);
		AuxTree aux = new AuxTree(lgN);
		List<Integer> accessSequence = generateRandomAccesses(lgN, accesses);
		refVsAux(ref, aux, accessSequence, log, graph);
	}
	private void refVsAux(RefTree ref, AuxTree aux, List<Integer> accesses, boolean log, boolean graph){
    	ref.serve(accesses);
    	aux.serve(accesses);
    	compare(ref, aux, log);
    	if(graph){
			ref.graph("ref");
			aux.graph("aux");
    	}
	}
	private void compare(RefTree ref, AuxTree aux, boolean log){
    	List<List<Integer>> refPaths = PreferredPathGleaner.glean(ref);
		List<List<Integer>> auxPaths = PreferredPathGleaner.glean(aux);
		if(log){
    		for (int i=0; i < refPaths.size(); i++){
        		System.out.println("r " + refPaths.get(i)); 
        		System.out.println("a " + auxPaths.get(i));
    		}
		}
		assertEquals(refPaths, auxPaths);
	}
}
