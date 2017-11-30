import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
class PreferredPathGleaner{
    Queue<Node> queue;
    List<List<Integer>> preferredPaths;
    private PreferredPathGleaner(){
    }
	public void processChildForPreferredPath(PreferredPathsTree tree, Node node){
        if(node != Node.nullNode){
			if (tree.isOnPreferredPath(node)){
    			queue.add(node);
			}else{
				new PreferredPathGleaner().addPreferredPaths(tree, node, preferredPaths);
        	}
        }
    }
    	
	static public List<List<Integer>> glean(PreferredPathsTree tree){
		List<List<Integer>> preferredPaths = new ArrayList<>();
		new PreferredPathGleaner().addPreferredPaths(tree, tree.getRoot(), preferredPaths);
		preferredPaths.sort(
    		(List<Integer> o1, List<Integer> o2)-> {
        		return -1 * Integer.compare(o1.size(), o2.size());
        	});
        return preferredPaths;
		
	}
	private void addPreferredPaths(
    	PreferredPathsTree tree,
    	Node node,
    	List<List<Integer>> preferredPaths)
    {
        this.preferredPaths = preferredPaths;
        queue = new LinkedList<>();
		List<Integer> preferredPath = new ArrayList<>();
		preferredPaths.add(preferredPath);
		queue.add(node);
		while (!queue.isEmpty()){
			node = queue.remove();
			preferredPath.add(node.getValue());
			Node left = node.getLeft();
			Node right = node.getRight();
			processChildForPreferredPath(tree, left);
			processChildForPreferredPath(tree, right);
		}
		preferredPath.sort(null);
	}
	
}
abstract class PreferredPathsTree extends BST{
	public abstract boolean isOnPreferredPath(Node node);
    public PreferredPathsTree(){
        super();
    }
    public PreferredPathsTree(BstCounter bstCounter){
        super(bstCounter);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public PreferredPathsTree(int lgN){
		super(lgN);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public PreferredPathsTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
    }

}
