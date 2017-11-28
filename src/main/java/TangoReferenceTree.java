public class TangoReferenceTree extends BST implements PreferredPathsTree{
	boolean leftPreferred = true;
	public TangoReferenceTree(BST node){
		super(node.getValue());
		if (node.getLeft() != nullNode){
			setLeft(new TangoReferenceTree(node.getLeft()));
		}
		if (node.getRight() != nullNode){
			setRight(new TangoReferenceTree(node.getRight()));
		}
	}
	@Override
    public BST find(int key){
        if(key < value && left != nullNode){
            leftPreferred = true;
            return getLeft().find(key);
        }else if (key > value && right != nullNode){
            leftPreferred = false;
            return getRight().find(key);
        }else if (value == key){
            leftPreferred = true;
            return this;
        }else{
            return nullNode;
        }
    }
    @Override
    protected String getGraphLineLeft(){
        String line = "\t\"" + id + "\" -> \"" + this.left.id + "\"";
        if (!leftPreferred){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }
    @Override
    protected String getGraphLineRight(){
		String line = "\t\"" + id + "\" -> \"" + this.right.id + "\"";
        if (leftPreferred){
            line += " [color=gray95];";
        }else{
            line += ";";
        }
        return line;
    }

	public boolean isOnPreferredPath(){
    	TangoReferenceTree parent = (TangoReferenceTree)getParent();
		if (parent.getRight() == this){
			return !parent.leftPreferred;
		}else{
    		return parent.leftPreferred;
		}
		
	}
}
