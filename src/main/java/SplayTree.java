public class SplayTree extends CounterNode{
    public SplayTree(int value, BstCounter bstCounter){
        super(value, bstCounter);
    }
    @Override
    public void insert(int toInsert){
        insert(new SplayTree(toInsert, bstCounter));
    }
    @Override
    public Node[] find(int key){
        Node[] foundPair = super.find(key);
        if (foundPair[0] != null){
            Node found = foundPair[0];
            while (!found.isRoot()){
                splay(found);
            }
        }
        return foundPair;
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
}
