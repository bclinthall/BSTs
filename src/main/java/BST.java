import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

class BST implements Node{
    protected static BST nullNode = new BST(0);
    protected BST parent = nullNode;
    protected BST left = nullNode;
    protected BST right = nullNode;
    private RootHolder rootHolder;
    protected int id;
    private static int nodeCount = 0;
    protected final int value;

    public BST(int value){
        this(value, new RootHolder());
        rootHolder.setRoot(this);
    }
    public BST(int value, RootHolder rootHolder){
        this.value = value;
        this.id = nodeCount;
        this.rootHolder = rootHolder;
        nodeCount++;
    }
    public void detach(){
        if (parent.left == this){
            parent.left = nullNode;
        }else{
            parent.right = nullNode;
        }
        this.parent = nullNode;
    }
    @Override
    public BST getParent(){
        return parent;
    }
    @Override
    public BST getLeft(){
        return left;
    }
    public void setLeft(BST left){
        this.left = left;
        left.parent = this;
    }
    public BST detachLeft(){
        BST tmp = left;
        this.left = nullNode;
        return tmp;
    }
    @Override
    public BST getRight(){
        return right;
    }
    public void setRight(BST right){
        this.right = right;
        right.parent = this;
    }
    public BST detachRight(){
        BST tmp = right;
        this.right = nullNode;
        return tmp;
    }
    @Override
    public boolean isRoot(){
        return parent instanceof RootHolder;
    }
    public boolean parentIsRoot(){
        return parent.isRoot();
    }
    public boolean isInLine(){
        if(isRoot() || parentIsRoot()){
            throw new UnsupportedOperationException("It doesn't make sense to "
                        + "call 'isInLine' on the root or a child of the root.");
        }
        return parent.parent.left == parent && parent.left == this
            || parent.parent.right == parent && parent.right == this;
    }
    @Override
    public void insert(int toInsert){
        insert(new BST(toInsert, rootHolder));
    }
    public void insert(BST toInsert){
        toInsert.rootHolder = rootHolder;
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
    public void rotateUp(){
        if (isRoot()){
            System.err.println("Cannot rotate root with its parent");
            return;
        }
        BST oldGrandParent = parent.parent;
        BST oldParent = parent;
        BST oldChild = null;
        if (this == parent.right){
            oldChild = detachLeft();
        } else {
            oldChild = detachRight();
        }
        parent.detach();
        detach();
        // I give my child to my parent.
        // My parent becomes my child.
        // I become the child of my grandparent
        oldParent.insert(oldChild);
        this.insert(oldParent);
        if (oldGrandParent != nullNode){
            oldGrandParent.insert(this);
        }
    }
    public void rotateParentUp(){
        if (isRoot()){
            throw new UnsupportedOperationException("It doesn't make sense to rotate the root up");
        }
        parent.rotateUp();
    }
    @Override
    public BST find(int key){
        if(key < value && left != nullNode){
            return getLeft().find(key);
        }else if (key > value && right != nullNode){
            return getRight().find(key);
        }else if (value == key){
            return this;
        }else{
            return nullNode;
        }
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
    public BST getRootForFree(){
        return rootHolder.getRootForFree();
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
    protected String getGraphLineLeft(){
        return "\t\"" + id + "\" -> \"" + this.left.id + "\";";
    }
    protected String getGraphLineRight(){
		return "\t\"" + id + "\" -> \"" + this.right.id + "\";";
    }
    public void getGraphLines(List<String> graphLines){
        graphLines.add("\t\"" + id + "\" [label=\"" + value + "\"];");
        if (this.left != nullNode){
            graphLines.add(getGraphLineLeft());
            this.left.getGraphLines(graphLines);
        } else {
            addNonNode(graphLines, "l");
        }
        //addNoneNode(graphLines, "c");
        if (this.right != nullNode){
            graphLines.add(getGraphLineRight());
            this.right.getGraphLines(graphLines);
        }else {
            addNonNode(graphLines, "r");
        }
    }
    private void addNonNode(List<String> graphLines, String ident){
        graphLines.add("\t\"" + id + "\" -> \"" + id + ident + "\" [color=gray95];");
        graphLines.add("\t\"" + id + ident + "\" [shape=point, color=gray95];");
    }
    public void graph(String name){
        String outputType = "ps";
        List<String> graphLines = new ArrayList<>();
        graphLines.add("digraph G {");
        graphLines.add("\tnode [shape=circle];");
        getGraphLines(graphLines);
        graphLines.add("}");
        String graphName = "graphs/" + name;
        String path = graphName +  ".gv";
        try{
            Files.write(Paths.get(path), graphLines);
            String graphPath = graphName + "." + outputType;
            Runtime.getRuntime().exec("dot -T" + outputType + " "
                          + path + " -o " + graphPath).waitFor();
            Runtime.getRuntime().exec("xdg-open " + graphPath).waitFor();
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public int getValue(){
        return value;
    }
}
class RootHolder extends BST{
    BST root;
    public RootHolder(){
        super(0, null);
    }
    public void setRoot(BST node){
        super.setLeft(node);
        root = node;
    }
    @Override
    public void setLeft(BST node){
        setRoot(node);
    }
    @Override
    public void setRight(BST node){
        setRoot(node);
    }
    @Override
    public void insert(BST node){

        setRoot(node);
    }
    @Override
    public BST getRootForFree(){
        return root;
    }
    
}
