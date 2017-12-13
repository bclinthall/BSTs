import java.util.Queue;
import java.util.LinkedList;
public class AuxTree extends SplayTree implements PreferredPathsTree{
    public AuxTree(){
        super();
    }
    public AuxTree(BstCounter bstCounter){
        super(bstCounter);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public AuxTree(int lgN){
		this(lgN, new BstCounter());
    }
    /* 
     * Makes a perfect BST of size 2^lgN
     */
    public AuxTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
        markNodesSetDepths(lgN);
    }
private void markNodesSetDepthsRecursive(AuxNode node, int maxDepth){
    if(!node.getRight().isNull()){
    	AuxNode right = (AuxNode)node.getRight();
    	right.setDepth(node.getDepth()+1);
    	right.setSubtreeMaxDepth(maxDepth);
    	right.setSubtreeMinDepth(right.getDepth());
    	right.mark();
    	markNodesSetDepthsRecursive(right, maxDepth);
    }
    if(!node.getLeft().isNull()){
    	AuxNode left = (AuxNode)node.getLeft();
    	left.setDepth(node.getDepth()+1);
    	left.setSubtreeMaxDepth(maxDepth);
    	left.setSubtreeMinDepth(left.getDepth());
    	markNodesSetDepthsRecursive(left, maxDepth);
    }	
}
	private void markNodesSetDepths(int lgN){
		int maxDepth = lgN - 1;
		AuxNode root = (AuxNode)getRoot();
		root.setDepth(0);
		root.setSubtreeMaxDepth(maxDepth);
		markNodesSetDepthsRecursive(root, maxDepth);
	}
	@Override
	public Node makeNode(int value){
    	return new AuxNode(value);
	}
	private void logNode(String label, Node node){
		return;
//		if(node.isNull()){
//    		System.out.println(label + " is null.");
//		}else{
//    		System.out.println(label + " key is " + node.getValue() +
//    		                   ", isInBBST:" + isInBBST(node));
//		}
	}
    public Node find(int key){
        Node node = getRoot();
        while (!node.isNull()&& node.getValue() != key){
            if(key < node.getValue()){
                node = node.getLeft();
            }else if (key > node.getValue()){
                node = node.getRight();
            }
            bstCounter.increment();
            if(isMarked(node)){
				handleMarked((AuxNode)node);
            }
        }
        afterFind(node);
        return node;
    }
    /*
    * Find node of minimum key value that has depth greater than d
    */
    private Node getNodeL(int d){
		Node node = getRoot();
		while(true){
    		if(isInBBST(node.getLeft())
    		    && getSubtreeMaxDepth(node.getLeft(), false)  > d){
        		node = node.getLeft();
    		}else if(getDepth(node) > d){
        		logNode("NodeL", node);
        		return node;
    		}else{
        		node = node.getRight();
    		}
		}
	}

    /*
     * Get the node of highest key less than the keys of
     * all nodes at depth greater than d.
     */
    private Node getLPrime(int d){
        Node nodeL = getNodeL(d);
		// lPrime is nodeL's predecessor. It will become easy to find
		// if we first splay nodeL to the root. 
		Node lPrime = NullNode.get();
        if(!nodeL.isNull()){
        	rotateToRoot(nodeL);
        	if (isInBBST(nodeL.getLeft())){
            	lPrime = nodeL.getLeft();
            	while(isInBBST(lPrime.getRight())){
                	lPrime = lPrime.getRight();
            	}
        	}
        }
        logNode("lPrime", lPrime);
        return lPrime;
    }
    /*
    * Find node of maximum key value that has depth greater than d
    */
    private Node getNodeR(int d){
		Node node = getRoot();
		while (true){
    		if (isInBBST(node.getRight())
    		    && getSubtreeMaxDepth(node.getRight(), false)  > d){
        		node = node.getRight();
    		}else if(getDepth(node)  > d){
        		logNode("nodeR", node);
        		return node;
    		}else{
        		node = node.getLeft();
    		}
		}
	}
    /*
     * Get the node of lowest key  greater than the keys of
     * all nodes at depth greater than d.
     */
    private Node getRPrime(int d){
        Node nodeR = getNodeR(d);
        // rPrime is nodeR's successor.  It will become easy to 
        // find if we first splay nodeR to the root.
        Node rPrime = NullNode.get();
        if (!nodeR.isNull()){
            rotateToRoot(nodeR);
            if (isInBBST(nodeR.getRight())){
                rPrime = nodeR.getRight();
                while(isInBBST(rPrime.getLeft())){
                   rPrime = rPrime.getLeft();
                }
            }
        }
        logNode("rPrime", rPrime);
        return rPrime;
    }
    int marksHandled = 0;
	private void handleMarked(AuxNode node){
    	marksHandled++;
    	//System.out.println("marksHandled: " + marksHandled);
    	int d = node.getSubtreeMinDepth() - 1;
    	//System.out.printf("+++Key %d is marked at depth %d\n", node.getValue(), d);

		Node rPrime = getRPrime(d);
		Node lPrime = getLPrime(d);
		// Put all nodes with depth greater than d in 
		// rPrime's left subtree.
		if (!rPrime.isNull()){
    		rotateToRoot(rPrime);
		}
		// Put all nodes with depths greater than d in 
		// lPrime's right subtree.
    	if(!lPrime.isNull()){
			rotateToRoot(lPrime);
		}
		// Now lPrime is at the root, and rPrime is its right child.
		// rPrimes left subtree contains all and only nodes with 
		// depth > d. So, we mark rPrime's left subtree.
		Node toUpdate;
		if (rPrime.isNull()){
    		mark(lPrime.getRight());
    		toUpdate = lPrime;
		}else{
    		mark(rPrime.getLeft());
    		toUpdate = rPrime;
		}
		while(!toUpdate.isRoot()){
			updateSubtreeDepths(toUpdate);
			toUpdate = toUpdate.getParent();
		}

		node.unmark();
		toUpdate = node.getParent();
		while(!toUpdate.isRoot()){
			updateSubtreeDepths(toUpdate);
			toUpdate = toUpdate.getParent();
		}
	}

	private void mark(Node node){
    	if(node.isNull()) return;
		((AuxNode)node).mark();
	}
	private boolean isMarked(Node node){
    	if (node.isNull()){
        	return false;
    	} else {
        	return ((AuxNode)node).isMarked();
    	}
	}
	private int getDepth(Node node){
    	if(node.isNull()){
        	return 0;
    	}else{
        	return ((AuxNode)node).getDepth();
    	}
	}
	static boolean isInBBST(Node node){
    	return !node.isNull() && !((AuxNode)node).isMarked();
	}
	static int getSubtreeMaxDepth(Node node, boolean descend){
    	if (node.isNull()){
        	return 0;
    	}
		AuxNode auxNode = (AuxNode) node;
		if(!descend && auxNode.isMarked()){
    		return 0;
		}
		return auxNode.getSubtreeMaxDepth();        	
	}
	static int getSubtreeMinDepth(Node node, boolean descend){
    	if (node.isNull()){
        	return Integer.MAX_VALUE;
    	}
		AuxNode auxNode = (AuxNode) node;
		if(!descend && auxNode.isMarked()){
    		return Integer.MAX_VALUE;
		}
		return auxNode.getSubtreeMinDepth();        	
	}
	private void updateSubtreeDepths(Node node){
    	updateSubtreeMaxDepth(node);
    	updateSubtreeMinDepth(node);
	}
	private void updateSubtreeMaxDepth(Node node){
		int d = ((AuxNode)node).getDepth();
		int dr = getSubtreeMaxDepth(node.getRight(), false);
		int dl = getSubtreeMaxDepth(node.getLeft(), false);
		d = dr > d ? dr : d;
		d = dl > d ? dl : d;
		((AuxNode)node).setSubtreeMaxDepth(d);
	}
	private void updateSubtreeMinDepth(Node node){
		int d = ((AuxNode)node).getDepth();
		int dr = getSubtreeMinDepth(node.getRight(), false);
		int dl = getSubtreeMinDepth(node.getLeft(), false);
		d = dr < d ? dr : d;
		d = dl < d ? dl : d;
		((AuxNode)node).setSubtreeMinDepth(d);
	}
	@Override
	protected void afterRotateUp(Node parent, Node oldParent){
		updateSubtreeDepths(oldParent);
		updateSubtreeDepths(parent);
	}
	@Override
	public boolean isOnPreferredPath(Node node){
		if(node.isNull()){
    		return false;
		}
		return !((AuxNode)node).isMarked();
	}
	@Override
    protected String getGraphLineLeft(Node node){
        String line = "\t\"" + node.getId() + "\" -> \"" + node.getLeft().getId() + "\"";
        if (!isOnPreferredPath(node.getLeft())){
            line += " [color=red, lwidth=0.5, style=dotted, arrowhead=empty];";
        }else{
            line += ";";
        }
        return line;
    }
    @Override
    protected String getGraphLineRight(Node node){
        String line = "\t\"" + node.getId() + "\" -> \"" + node.getRight().getId() + "\"";
        if (!isOnPreferredPath(node.getRight())){
            line += " [color=red, lwidth=0.5, style=dotted, arrowhead=empty];";
        }else{
            line += ";";
        }
        return line;
    }
	@Override
	protected String getNodeLabel(Node node){
		AuxNode auxNode = (AuxNode)node;
		return auxNode.getValue()
		       + ":" + auxNode.getDepth()
		       + ":" + auxNode.getSubtreeMaxDepth()
		       + ":" + auxNode.getSubtreeMinDepth();
	} 
}

class AuxNode extends BstNode{
    int depth = 0;
	int subtreeMaxDepth = 0;
	int subtreeMinDepth = 0;
	boolean marked = false;
	public AuxNode(int value){
    	super(value);
	}
	public void setDepth(int depth){
    	this.depth = depth;
	}
	public int getDepth(){
    	return depth;
	}
	public void setSubtreeMaxDepth(int depth){
    	subtreeMaxDepth = depth;
	}
	public int getSubtreeMaxDepth(){
    	return subtreeMaxDepth;
	}
	public void setSubtreeMinDepth(int depth){
    	subtreeMinDepth = depth;
	}
	public int getSubtreeMinDepth(){
    	return subtreeMinDepth;
	}
	public void mark(){
    	marked = true;
	}
	public void unmark(){
    	marked = false;
	}
	public boolean isMarked(){
    	return marked;
	}

	@Override
	protected void afterInsert(Node inserted){
		setSubtreeMaxDepth(Math.max(subtreeMaxDepth, AuxTree.getSubtreeMaxDepth(inserted, false)));
	}
}
