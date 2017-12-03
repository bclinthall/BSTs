public class MaxDepthTree extends SplayTree{
    public MaxDepthTree(){
        super();
    }
    public MaxDepthTree(BstCounter bstCounter){
        super(bstCounter);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public MaxDepthTree(int lgN){
        this(lgN, new BstCounter());
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public MaxDepthTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
        markDepth(getRoot(), 0, lgN-1);
    }
	private void markDepth(Node node, int depth, int maxDepth){
		AuxNode auxNode = (AuxNode)node;
		auxNode.setDepth(depth);
		auxNode.setSubtreeMaxDepth(maxDepth);
		if(node.getRight() != NullNode.get()){
			markDepth(node.getRight(), depth+1, maxDepth);
		}
		if(node.getLeft() != NullNode.get()){
			markDepth(node.getLeft(), depth+1, maxDepth);
		}
	}
	private int getMaxSubtreeDepth(Node node){
		if (node == NullNode.get()){
    		return 0;
		}else{
    		return ((AuxNode)node).getSubtreeMaxDepth();
		}
	}
	private void updateMaxSubtreeDepth(Node node){
		int d = ((AuxNode)node).getDepth();
		int dr = getMaxSubtreeDepth(node.getRight());
		int dl = getMaxSubtreeDepth(node.getLeft());
		d = dr > d ? dr : d;
		d = dl > d ? dl : d;
		((AuxNode)node).setSubtreeMaxDepth(d);
	}
	@Override
	protected void afterRotateUp(Node parent, Node oldParent){
		updateMaxSubtreeDepth(parent);
		updateMaxSubtreeDepth(oldParent);
	}
    @Override
    public Node makeNode(int value){
        return new SimpleAuxNode(value);
    }
	@Override
	protected String getNodeLabel(Node node){
		AuxNode auxNode = (AuxNode)node;
		return auxNode.getValue() + ":" + auxNode.getDepth() + ":" + auxNode.getSubtreeMaxDepth();
	} 
}
