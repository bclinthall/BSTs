class Node{
    protected final int id;
    private static int nodeCount = 0;
    private final int value;
    public static Node nullNode = new Node(0);
    private Node parent = nullNode;
    private Node left = nullNode;
    private Node right = nullNode;

    public Node(int value){
        this.value = value;
        this.id = nodeCount;
        nodeCount++;
    }
    public int getId(){
        return id;
    }
    public boolean isLeftChild(){
        return parent.left == this;
    }
    public boolean isRightChild(){
        return parent.right == this;
    }
    public Node detach(){
        if (isLeftChild()){
            parent.left = nullNode;
        }
        if(isRightChild()){
            parent.right = nullNode;
        }
        this.parent = nullNode;
        return this;
    }
    public Node getParent(){
        return parent;
    }
    public Node getLeft(){
        return left;
    }
    public void setLeft(Node left){
        this.left = left;
        left.parent = this;
    }
    public Node getRight(){
        return right;
    }
    public void setRight(Node right){
        this.right = right;
        right.parent = this;
    }
    public void insert(Node toInsert){
        if (value < toInsert.value){
            if (right == nullNode){
                setRight(toInsert);
            }else{
                right.insert(toInsert);
            }
        } else {
            if (left == nullNode){
                setLeft(toInsert);
            } else {
                left.insert(toInsert);
            }
        }
    }
    public boolean isRoot(){
        return parent instanceof RootHolder;
    }
    public boolean isInLine(){
        if(isRoot() || parent.isRoot()){
            throw new UnsupportedOperationException("It doesn't make sense to "
                        + "call 'isInLine' on the root or a child of the root.");
        }
        return parent.parent.left == parent && parent.left == this
            || parent.parent.right == parent && parent.right == this;
    }

    public boolean isValidBST(){
        boolean isValid = true;
        if (left != nullNode){
            isValid = value > left.value;
            if (!isValid){
                System.err.printf("%d.left = %d\n", value, left.value);
                return false;
            }
            isValid = left.isValidBST();
        }
        if (isValid && right != nullNode){
            isValid = value < right.value;
            if (!isValid){
                System.err.printf("%d.right = %d\n", value, right.value);
                return false;
            }
            isValid = right.isValidBST();
        }
        return isValid;
    }
    @Override
    public String toString(){
        if (this == nullNode){
            return "";
        } else if (left == nullNode && right == nullNode){
            return value + "";
        }else{
            return value + ":{"+left + ", " + right + "}";
        }
    }
    public int getValue(){
        return value;
    }
}
class RootHolder extends Node{
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
		throw new UnsupportedOperationException("I'm the root holder.  Don't tell me to getRight()");
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
		if (getLeft() == nullNode){
			setLeft(node);
		}else{
    		getLeft().insert(node);
		}
    }

}
