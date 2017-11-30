import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

class BST {
    private RootHolder rootHolder = new RootHolder();
    protected
    BstCounter bstCounter;

    public BST(){
        this(Node.nullNode, new BstCounter());
    }
    public BST(BstCounter bstCounter){
		this(Node.nullNode, bstCounter);
    }
    public BST(Node root){
		this(root, new BstCounter());
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public BST(int lgN){
		this(lgN, new BstCounter());
    }
    /*
     * Makes a perfect BST of size 2^lgN
     */
    public BST(int lgN, BstCounter bstCounter){
		this(PerfectBstMaker.makePerfectBst(lgN), bstCounter);
    }
	/*
	 * This is the main construtor.  All other must
	 * constructors go through it.
	 */
    public BST(Node root, BstCounter bstCounter){
        this.bstCounter = bstCounter;
        rootHolder.insert(augument(root));
    }

	/*
	 * Does nothing for a generic BST, but some subclasses
	 * may need special augmented Nodes.  They can override this
	 * method. Make sure that every node creation gets wrapped in
	 * this method.
	 */
	protected Node augument(Node node){
		return node;
	}
	public long getOpCount(){
    	return bstCounter.getCount();
	}
    public void insert(int value){
        insert(augument(new Node(value)));
    }
    public void insert(Node node){
        rootHolder.insert(node);
        afterInsert(node);
    }
    protected void afterInsert(Node inserted){}
    
    public void rotateUp(Node node){
        if (node.isRoot()){
            System.err.println("Cannot rotate root with its parent");
            return;
        }
        bstCounter.increment();
        Node oldParent = node.getParent();
        // grandparent always exists. Is roothold if parent is root.
        Node oldGrandParent = oldParent.getParent();
        Node oldChild = null;
        if (node.isRightChild()){
            oldChild = node.getLeft().detach();
        } else {
            oldChild = node.getRight().detach();
        }
        oldParent.detach();
        node.detach();
        // I give my child to my parent.
        // My parent becomes my child.
        // I become the child of my grandparent
        oldParent.insert(oldChild);
        node.insert(oldParent);
        oldGrandParent.insert(node);
    }
    public Node find(int key){
        Node node = getRoot();
        while (node != Node.nullNode && node.getValue() != key){
            if(key < node.getValue()){
                node = node.getLeft();
            }else if (key > node.getValue()){
                node = node.getRight();
            }
            bstCounter.increment();
        }
        afterFind(node);
        return node;
    }
    protected void afterFind(Node found){}

    public Node getRoot(){
        return rootHolder.getRoot();
    }
    @Override
    public String toString(){
		return this.getClass().getSimpleName() + ": " + getRoot();
    }
    protected String getGraphLineLeft(Node node){
        return "\t\"" + node.getId() + "\" -> \"" + node.getLeft().getId() + "\";";
    }
    protected String getGraphLineRight(Node node){
		return "\t\"" + node.getId() + "\" -> \"" + node.getRight().getId() + "\";";
    }
    public void getGraphLines(List<String> graphLines, Node node){
        graphLines.add("\t\"" + node.getId() + "\" [label=\"" + node.getValue() + "\"];");
        if (node.getLeft() != Node.nullNode){
            graphLines.add(getGraphLineLeft(node));
            getGraphLines(graphLines, node.getLeft());
        } else {
            addNonNode(graphLines, "l", node);
        }
        //addNoneNode(graphLines, "c", node);
        if (node.getRight() != Node.nullNode){
            graphLines.add(getGraphLineRight(node));
            getGraphLines(graphLines, node.getRight());
        }else {
            addNonNode(graphLines, "r", node);
        }
    }
    private void addNonNode(List<String> graphLines, String ident, Node node){
        graphLines.add("\t\"" + node.getId() + "\" -> \"" + node.getId() + ident + "\" [color=gray95];");
        graphLines.add("\t\"" + node.getId() + ident + "\" [shape=point, color=gray95];");
    }
    public void graph(String name){
        String outputType = "ps";
        List<String> graphLines = new ArrayList<>();
        graphLines.add("digraph G {");
        graphLines.add("\tnode [shape=circle];");
        getGraphLines(graphLines, getRoot());
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



class BstCounter{
    private long count = 0;
    public void increment(){
        count++;
    }
    public long getCount(){
        return count;
    }
}

