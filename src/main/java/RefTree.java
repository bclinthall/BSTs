public class RefTree extends PreferredPathsTree{
	boolean leftPreferred = true;
    public RefTree(){
        super();
    }
    public RefTree(BstCounter bstCounter){
        super(bstCounter);
    }
	public RefTree(Node root){
    	super(root);
	}
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public RefTree(int lgN){
		super(lgN);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public RefTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
    }

    public RefTree(Node root, BstCounter bstCounter){
        super(bstCounter);
    }
	@Override
	protected Node augment(Node node){
		return new RefNode(node.getValue());
	}
	private void setLeftPreferred(Node node, boolean leftPreferred){
		((RefNode)node).leftPreferred = leftPreferred;
	}
	private boolean isLeftPreferred(Node node){
		return ((RefNode)node).leftPreferred;
	}

	@Override
    public Node find(int key){
        Node node = getRoot();
        while (node != Node.nullNode && node.getValue() != key){
            if(key < node.getValue()){
                node = node.getLeft();
                setLeftPreferred(node, true);
            }else if (key > node.getValue()){
                node = node.getRight();
                setLeftPreferred(node, false);
            }
            bstCounter.increment();
        }
        afterFind(node);
        return node;
    }
    @Override
    protected String getGraphLineLeft(Node node){
        String line = "\t\"" + node.getId() + "\" -> \"" + node.getLeft().getId() + "\"";
        if (!isLeftPreferred(node)){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }
    @Override
    protected String getGraphLineRight(Node node){
		String line = "\t\"" + node.getId() + "\" -> \"" + node.getRight().getId() + "\"";
        if (isLeftPreferred(node)){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }

	public boolean isOnPreferredPath(Node node){
    	Node parent = node.getParent();
		if (parent.getRight() == node){
			return !isLeftPreferred(parent);
		}else{
    		return isLeftPreferred(parent);
		}
	}
    private static class RefNode extends Node{
    	boolean leftPreferred = true;
    	public RefNode(int value){
        	super(value);
    	}
    }
}
