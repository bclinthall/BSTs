interface Node{
    public void detach();
    public Node getParent();
    public Node getLeft();
    public Node getRight();
    public boolean isRoot();
    public void insert(int toInsert);
	public int getValue();
    public Node find(int key);
}

