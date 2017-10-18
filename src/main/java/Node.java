import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

class Node{
    private static Node nullNode = new Node(0);
    private Node parent = nullNode;
    private Node left = nullNode;
    private Node right = nullNode;
    private int id;
    private static int nodeCount = 0;
    protected final int value;

    public Node(int value){
        this.value = value;
        this.id = nodeCount;
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
    public Node detachLeft(){
        Node tmp = left;
        this.left = nullNode;
        return tmp;
    }
    public Node getRight(){
        return right;
    }
    public void setRight(Node right){
        this.right = right;
        right.parent = this;
    }
    public Node detachRight(){
        Node tmp = right;
        this.right = nullNode;
        return tmp;
    }
    public boolean isRoot(){
        return parent == nullNode;
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
    public void insert(int toInsert){
        insert(new Node(toInsert));
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
    public void rotateUp(){
        if (isRoot()){
            System.err.println("Cannot rotate root with its parent");
            return;
        }
        Node oldGrandParent = parent.parent;
        Node oldParent = parent;
        Node oldChild = null;
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
    public Node[] find(int key){
        if(key < value && left != nullNode){
            return getLeft().find(key);
        }else if (key > value && right != nullNode){
            return getRight().find(key);
        }else if (value == key){
            return new Node[]{this, this};
        }else{
            return new Node[]{null, this};
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
    public Node getRootForFree(){
        if (parent == nullNode){
            return this;
        }else{
            return parent.getRootForFree();
        }
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
    public void getGraphLines(List<String> graphLines){
        graphLines.add("\t\"" + id + "\" [label=\"" + value + "\"];");
        if (this.left != nullNode){
            graphLines.add("\t\"" + id + "\" -> \"" + this.left.id + "\";");
            this.left.getGraphLines(graphLines);
        } else {
            addNonNode(graphLines, "l");
        }
        //addNoneNode(graphLines, "c");
        if (this.right != nullNode){
            graphLines.add("\t\"" + id + "\" -> \"" + this.right.id + "\";");
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
}

