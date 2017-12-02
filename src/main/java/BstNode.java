class BstNode implements Node{
    protected final int id;
    private static int nodeCount = 0;
    protected final int value;
    private Node parent = NullNode.get();
    private Node left = NullNode.get();
    private Node right = NullNode.get();

    public BstNode(int value){
        this.value = value;
        this.id = nodeCount;
        nodeCount++;
    }
    public String getId(){
        return "Node" + id;
    }
    public boolean isLeftChild(){
        return parent.getLeft() == this;
    }
    public boolean isRightChild(){
        return parent.getRight() == this;
    }
    public Node detach(){
        if (isLeftChild()){
            parent.setLeft(NullNode.get());
        }
        if(isRightChild()){
            parent.setRight(NullNode.get());
        }
        this.parent = NullNode.get();
        return this;
    }
    public Node getParent(){
        return parent;
    }
    public void setParent(Node parent){
		this.parent = parent;
    }
    public Node getLeft(){
        return left;
    }
    public void setLeft(Node left){
        this.left = left;
        left.setParent(this);
    }
    public Node getRight(){
        return right;
    }
    public void setRight(Node right){
        this.right = right;
        right.setParent(this);
    }
    public void insert(Node toInsert){
        if (value < toInsert.getValue()){
            if (right == NullNode.get()){
                setRight(toInsert);
            }else{
                right.insert(toInsert);
            }
        } else {
            if (left == NullNode.get()){
                setLeft(toInsert);
            } else {
                left.insert(toInsert);
            }
        }
        afterInsert(toInsert);
    }
    protected void afterInsert(Node inserted){

    }
    public boolean isRoot(){
        return parent instanceof RootHolder;
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
        boolean isValid = true;
        if (left != NullNode.get()){
            isValid = value > left.getValue();
            if (!isValid){
                System.err.printf("%d.left = %d\n", value, left.getValue());
                return false;
            }
            isValid = left.isValidBST();
        }
        if (isValid && right != NullNode.get()){
            isValid = value < right.getValue();
            if (!isValid){
                System.err.printf("%d.right = %d\n", value, right.getValue());
                return false;
            }
            isValid = right.isValidBST();
        }
        return isValid;
    }
    @Override
    public String toString(){
        if (this == NullNode.get()){
            return "";
        } else if (left == NullNode.get() && right == NullNode.get()){
            return value + "";
        }else{
            return value + ":{"+left + ", " + right + "}";
        }
    }
    public int getValue(){
        return value;
    }
}
class RootHolder extends BstNode{
    public RootHolder(){
        super(0);
    }
    @Override
    public void setLeft(Node node){
        super.setLeft(node);
    }
    @Override
    public void setRight(Node node){
        setLeft(node);
    }
    @Override
    public Node getRight(){
		return getLeft();
    }
    @Override
    public Node getParent(){
		throw new UnsupportedOperationException("I'm the root holder.  Don't tell me to getParent()");
    }
    @Override
    public boolean isRoot(){
		throw new UnsupportedOperationException("I'm the root holder.  Don't ask me isRoot()");
    }
    @Override
    public boolean isRightChild(){
		throw new UnsupportedOperationException("I'm the root holder.  Don't ask me isRightChild()");
    }
    @Override
    public boolean isLeftChild(){
		throw new UnsupportedOperationException("I'm the root holder.  Don't ask me isLeftChild()");
    }
    @Override
    public Node detach(){
		throw new UnsupportedOperationException("I'm the root holder.  Don't tell me to detach()");
    }
    public Node getRoot(){
        return getLeft();
    }
    @Override
    public void insert(Node node){
		if (getLeft() == NullNode.get()){
			setLeft(node);
		}else{
    		getLeft().insert(node);
		}
    }

}
class NullNode extends BstNode{
	private static NullNode nullNode = new NullNode(0);
    private NullNode(int value){
        super(value);
    }
    public static NullNode get(){
        return nullNode;
    }
	@Override
	public Node detach(){
		return this;
	}
	@Override
    public void setLeft(Node node){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to setLeft()");
    }
    @Override
    public void setRight(Node node){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to setRight()");
    }
    @Override
    public Node getRight(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to getRight()");
    }
    @Override
    public Node getLeft(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to getLeft()");
    }
    @Override
    public Node getParent(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to getParent()");
    }
    @Override
    public boolean isRoot(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't ask me isRoot()");
    }
    @Override
    public boolean isRightChild(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't ask me isRightChild()");
    }
    @Override
    public boolean isLeftChild(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't ask me isLeftChild()");
    }
    public Node getRoot(){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to getRoot()");
    }
    @Override
    public void insert(Node node){
		throw new UnsupportedOperationException("I'm the nullNode.  Don't tell me to insert()");
    }
}
