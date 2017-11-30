import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/* We want to be able to treat an Auxiliary tree like a Node.
For the most part, it delegates its node methods to its root.
But we don't want splayToRoot to travel up to a parent tree.
This is accomplished because The AuxiliaryTree is not a parent
the its root. Its root is a genuine root.  So, we cannot traverse
out of this AuxiliaryTree by calling getParent().  However, we can
descend into it by call getLeft, or getRight, and by extension by
calling find or insert. When we join one AuxiliaryTree with another
we do not want to descend into child trees to find the max.
That is accomplished by an extra check in the join function below.
*/
class AuxiliaryTree extends SplayTree implements Node{
    
    protected final int id;
    private static int nodeCount = 0;
    int depth = 0;
    int minSubtreeDepth;
    int maxSubtreeDepth;
	Node parent = NullNode.get();

    public AuxiliaryTree(){
        super();
    }
    public AuxiliaryTree(BstCounter bstCounter){
        super(bstCounter);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public AuxiliaryTree(int lgN){
		super(lgN);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public AuxiliaryTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
    }

	public void setDepth(int depth){
    	this.depth = depth;
	}
	public AuxiliaryTree (RefNode ref, int depth){
    	super();
		List<Node> hangersOn = new ArrayList<>();
		List<Integer> hangersOnDepth = new ArrayList<>();
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
    			ref = (RefTree)((RefTree)preferredChild).getRootForFree();
   				cont = true;
    		}
    	}
		for (int i = 0; i < hangersOn.size(); i++){
			auxTree.insert(
    			fromReferenceTree((RefTree)hangersOn.get(i), bstCounter, hangersOnDepth.get(i))
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

















    public String getId(){
        return "Aux" + id;
    }
    public boolean isLeftChild(){
        return parent.getLeft() == this;
    }
    public boolean isRightChild(){
        return parent.getRight() == this;
    }
    public Node detach(){
        if (isLeftChild()){
            parent.setLeft(nullNode);
        }
        if(isRightChild()){
            parent.setRight(nullNode);
        }
        this.parent = nullNode;
        return this;
    }
    public Node getParent(){
        return parent;
    }
    public void setParent(Node parent){
		this.parent = parent;
    }
    public Node getLeft(){
        return getRoot().getLeft();
    }
    public void setLeft(Node left){
        getRoot().setLeft(left);
    }
    public Node getRight(){
        return getRoot().getRight();
    }
    public void setRight(Node right){
		getRoot.setRight(right);
	}
    public void insert(Node toInsert){
		getRoot.insert(toInsert);
	}
    public boolean isRoot(){
        throw new UnsupportedOperationException("I'm an aux tree, why are you asking if I'm a root?");
    }
    public boolean isInLine(){
        if(isRoot() || parent.isRoot()){
            throw new UnsupportedOperationException("It doesn't make sense to "
                        + "call 'isInLine' on the root or a child of the root.");
        }
        return parent.getParent().getLeft() == parent && parent.getLeft() == this
            || parent.getParent().getRight() == parent && parent.getRight() == this;
    }

    public boolean isValidBST(){
        return getRoot().isValidBST();
    }
    public int getValue(){
        return getRoot().getValue();
    }
    /*
     * @param toJoin: a node whose subtree contains only
     *                nodes whose values are greater than all
     *                the values in /this/ tree. It will be joined
     *                into /this/ tree.
     */
    @Override
    public void join(Node toJoin){
        Node myMax = getRoot();
        while (myMax.getRight() != NullNode.get() && !(myMax instanceof AuxiliaryTree)){
            myMax = myMax.getRight();
        }
        splayToRoot(myMax);
        myMax.insert(toJoin);
    }
}