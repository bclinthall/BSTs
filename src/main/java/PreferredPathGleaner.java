import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
class PreferredPathGleaner{
    Queue<PreferredPathsTree> queue;
    List<List<Integer>> preferredPaths;
    private PreferredPathGleaner(){
    }
	public void processChildForPreferredPath(Node node){
        if(node != BST.nullNode){
            PreferredPathsTree ppt = (PreferredPathsTree) node;
			if (ppt.isOnPreferredPath()){
    			queue.add(ppt);
			}else{
				new PreferredPathGleaner().addPreferredPaths(ppt, preferredPaths);
        	}
        }
    }
    	
	static public List<List<Integer>> glean(PreferredPathsTree node){
		List<List<Integer>> preferredPaths = new ArrayList<>();
		new PreferredPathGleaner().addPreferredPaths(node, preferredPaths);
		preferredPaths.sort(
    		(List<Integer> o1, List<Integer> o2)-> {
        		return -1 * Integer.compare(o1.size(), o2.size());
        	});
        return preferredPaths;
		
	}
	private void addPreferredPaths(
    	PreferredPathsTree node,
    	List<List<Integer>> preferredPaths)
    {
        this.preferredPaths = preferredPaths;
        queue = new LinkedList<>();
		List<Integer> preferredPath = new ArrayList<>();
		preferredPaths.add(preferredPath);
		queue.add(node);
		while (! queue.isEmpty()){
			node = queue.remove();
			preferredPath.add(node.getValue());
			Node left = node.getLeft();
			Node right = node.getRight();
			processChildForPreferredPath(left);
			processChildForPreferredPath(right);
		}
		preferredPath.sort(null);
	}
	
}
interface PreferredPathsTree extends Node{
	public boolean isOnPreferredPath();
}
