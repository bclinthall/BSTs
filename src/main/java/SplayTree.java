public class SplayTree extends CounterNode{
    public SplayTree(int value, BstCounter bstCounter){
        super(value, bstCounter);
    }
    public SplayTree(BST node, BstCounter bstCounter){
        super(node.getValue(), bstCounter);
       	if (node.getLeft() != nullNode){
			setLeft(new SplayTree(node.getLeft(), bstCounter));
       	}
       	if (node.getRight() != nullNode){
           	setRight(new SplayTree(node.getRight(), bstCounter));
       	}
    }
	
    
    @Override
    public void insert(int toInsert){
        SplayTree node = new SplayTree(toInsert, bstCounter);
        super.insert(node);
        splayToRoot(node);
    }
    @Override
    public BST find(int key){
        BST found = super.find(key);
        if (found != nullNode){
            splayToRoot(found);
        }
        return found;
    }

    public void splayToRoot(BST node){
        while (!splay(node)){
            continue;
        }
    }
    public boolean splay(BST node){
        if (node.isRoot()){
            return true;
        } else if (node.parentIsRoot()){
            node.rotateUp();
            return true;
        }
		boolean willBeAtRoot = node.parent.parentIsRoot();
        if (node.isInLine()){
            node.rotateParentUp();
            node.rotateUp();
        } else {
            node.rotateUp();
            node.rotateUp();
        }
        return willBeAtRoot;
    }
    public BST[] split(int k){
        BST node = find(k);
        splayToRoot(node);
        BST[] pair = {node, node.getRight()};
        node.setRight(nullNode);
        return pair;
    }
    public BST join(SplayTree a, SplayTree b){
        BST max1 = a;
        while (max1.getRight() != nullNode){
            max1 = max1.getRight();
        }
        BST min2 = b;
        splayToRoot(max1);
        assert (max1.getRight() == nullNode);
        assert(max1.getValue() > b.getValue());
        max1.setRight(b);
        return max1;
    }
}
