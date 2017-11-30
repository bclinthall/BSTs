
class SplayTree extends BST{ 
    public SplayTree(){
        super();
    }
    public SplayTree(BstCounter bstCounter){
        super(bstCounter);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public SplayTree(int lgN){
		super(lgN);
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public SplayTree(int lgN, BstCounter bstCounter){
        super(lgN, bstCounter);
    }

    @Override
	protected void afterInsert(Node inserted){
		splayToRoot(inserted);
	}

	@Override
	protected void afterFind(Node found){
        if (found != NullNode.get()){
            splayToRoot(found);
        }
	}

    public void splayToRoot(Node node){
        while (!node.isRoot()){
            splay(node);
        }
    }
    public void splay(Node node){
        if (node.getParent().isRoot()){
            rotateUp(node);
        }else if (node.isInLine()){
            rotateUp(node.getParent());
            rotateUp(node);
        } else {
            rotateUp(node);
            rotateUp(node);
        }
    }
    /*
     * Since we are always going to be joining the split back
     * into this tree somehow, I'm not bothering to make a new
     * tree of it.  This method just detaches and returns a node
     * greater than k whose subtree contains all other nodes
     * greater than k.
     */
     
    public Node split(int k){
        Node node = find(k);
        splayToRoot(node);
        return node.getRight().detach();
    }
    /*
     * @param toJoin: a node whose subtree contains only
     *                nodes whose values are greater than all
     *                the values in /this/ tree. It will be joined
     *                into /this/ tree.
     */
    public void join(Node toJoin){
        Node myMax = getRoot();
        while (myMax.getRight() != NullNode.get()) {
            myMax = myMax.getRight();
        }
        splayToRoot(myMax);
        myMax.insert(toJoin);
    }

}
