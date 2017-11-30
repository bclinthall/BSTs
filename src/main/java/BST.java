import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;

class BST {
    private RootHolder rootHolder = new RootHolder();
    protected
    BstCounter bstCounter;

    public BST(){
        this(new BstCounter());
    }
    public BST(BstCounter bstCounter){
        this.bstCounter = bstCounter;
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
		this(bstCounter);
		insert(new PerfectBstMaker().makePerfectBst(lgN)); 
    }
	
	/*
	 * Does nothing for a generic BST, but some subclasses
	 * may need special augmented Nodes.  They can override this
	 * method. Make sure that every node creation gets wrapped in
	 * this method.
	 */
	public Node makeNode(int value){
		return new Node(value);
	}
	public long getOpCount(){
    	return bstCounter.getCount();
	}
    public void insert(int value){
        insert(makeNode(value));
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
        Node oldChild = Node.nullNode;
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
        while (node != Node.nullNode&& node.getValue() != key){
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
    class PerfectBstMaker{
        /*
         * Returns a node, the root of a perfect BST of size 2^lgN
         */
    	public Node makePerfectBst(int lgN){
    		int n = 1 << lgN;
    		Queue<NodeLowHigh> queue = new LinkedList<>();
    		Node node = makeNode(n >> 1);
    		queue.add(new NodeLowHigh(node, 1, n));
    		while (!queue.isEmpty()){
    			makeChildren(queue.remove(), queue);
    		}
    		return node;
    	}
    	public void makeChildren(NodeLowHigh nlh, Queue queue){
    		Node node = nlh.node;
    		int val = node.getValue();
    		//System.out.println(val);
    		Node left = makeNode((nlh.low + val)/2);
    		node.insert(left);
    		Node right = makeNode((val + nlh.high)/2);
    		node.insert(right);
    		if (nlh.high != right.getValue()+1){
    			queue.add(new NodeLowHigh(left, nlh.low, val));
    			queue.add(new NodeLowHigh(right, val, nlh.high));
    		}
    	}
    }

}

class NodeLowHigh{
	Node node;
	int high;
	int low;
	NodeLowHigh(Node node, int low, int high){
    	this.node = node;
    	this.low = low;
    	this.high = high;
	}
}

class BstCounter{
    private long count = 0;
    public void increment(){
        count++;
/*        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        System.out.println();
        System.out.println();
        for (int i=0; i<4; i++){
            System.out.println();
            System.out.print(stack[i]);
        }*/
    }
    public long getCount(){
        return count;
    }
}

