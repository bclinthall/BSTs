import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class AuxiliaryTree extends SplayTree implements PreferredPathsTree{
    int depth;
    int minSubtreeDepth;
    int maxSubtreeDepth;
    boolean marked;

	public AuxiliaryTree(int value, BstCounter bstCounter, int depth){
    	super(value, bstCounter);
    	this.depth = depth;
	}
	public static AuxiliaryTree fromReferenceTree(TangoReferenceTree ref, BstCounter bstCounter, int depth){
		List<Node> hangersOn = new ArrayList<>();
		List<Integer> hangersOnDepth = new ArrayList<>();
		AuxiliaryTree auxTree = new AuxiliaryTree(ref.getValue(), bstCounter, depth);
		auxTree.marked = true;
		boolean cont = true;
		while(cont){
    		cont = false;
    		depth++;
    		Node preferredChild = ref.leftPreferred ? ref.getLeft() : ref.getRight();
    		Node dispreferredChild = ref.leftPreferred ? ref.getRight() : ref.getLeft();
    		if (dispreferredChild != nullNode){
        		hangersOn.add(dispreferredChild);
        		hangersOnDepth.add(depth);
        		cont = true;
    		}
    		if (preferredChild != nullNode){
    			auxTree.insert(new AuxiliaryTree(preferredChild.getValue(), bstCounter, depth));
    			ref = (TangoReferenceTree)((TangoReferenceTree)preferredChild).getRootForFree();
   				cont = true;
    		}
    	}
		for (int i = 0; i < hangersOn.size(); i++){
			auxTree.insert(
    			fromReferenceTree((TangoReferenceTree)hangersOn.get(i), bstCounter, hangersOnDepth.get(i))
			);
		}
		return auxTree;
	}
	@Override
	public boolean isRoot(){
		return marked || super.isRoot();
	}

	public boolean isOnPreferredPath(){
    	return !marked;
	}
    @Override
    protected String getGraphLineLeft(){
        String line = "\t\"" + id + "\" -> \"" + left.id + "\"";
        if (((AuxiliaryTree)left).marked){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }
    @Override
    protected String getGraphLineRight(){
		String line = "\t\"" + id + "\" -> \"" + this.right.id + "\"";
        if (((AuxiliaryTree)right).marked){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }
}
