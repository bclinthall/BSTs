public interface Node{
	public boolean isLeftChild();
	public boolean isRightChild();
	public Node detach();
	public Node getParent();
	public void setParent(Node node);
	public Node getLeft();
	public void setLeft(Node node);
	public Node getRight();
	public void setRight(Node node);
	public void insert(Node nodeInterface);
	public boolean isRoot();
	public boolean isInLine();
	public boolean isValidBST();
	public int getValue();
	public String getId();
}
