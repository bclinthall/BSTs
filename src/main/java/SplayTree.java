public class SplayTree extends CounterNode{
    public SplayTree(int value, BstCounter bstCounter){
        super(value, bstCounter);
    }
    @Override
    public void insert(int toInsert){
        SplayTree node = new SplayTree(toInsert, bstCounter);
        super.insert(node);
        splayToRoot(node);
    }
    @Override
    public Node find(int key){
        Node found = super.find(key);
        if (found != nullNode){
            splayToRoot(found);
        }
        return found;
    }

    public void splayToRoot(Node node){
        while (!node.isRoot()){
            splay(node);
        }
    }
    public void splay(Node node){
        if (node.isRoot()){
            return;
        } else if (node.parentIsRoot()){
            node.rotateUp();
        } else if (node.isInLine()){
            node.rotateParentUp();
            node.rotateUp();
        } else {
            node.rotateUp();
            node.rotateUp();
        }
    }
    public Node[] split(int k){
        Node node = find(k);
        splayToRoot(node);
        Node[] pair = {node, node.getRight()};
        node.setRight(nullNode);
        return pair;
    }
    public Node join(SplayTree a, SplayTree b){
        Node max1 = a;
        while (max1.getRight() != nullNode){
            max1 = max1.getRight();
        }
        Node min2 = b;
        splayToRoot(max1);
        assert (max1.getRight() == nullNode);
        assert(max1.getValue() > b.getValue());
        max1.setRight(b);
        return max1;
    }
}
