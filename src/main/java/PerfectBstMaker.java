import java.util.Queue;
import java.util.LinkedList;

public class PerfectBstMaker{
    /*
     * Returns a node, the root of a perfect BST of size 2^lgN
     */
	public static Node makePerfectBst(int lgN){
		int n = 1 << lgN;
		Queue<NodeLowHigh> queue = new LinkedList<>();
		Node node = new Node(n >> 1);
		queue.add(new NodeLowHigh(node, 1, n));
		while (!queue.isEmpty()){
			makeChildren(queue.remove(), queue);
		}
		return node;
	}
	public static void makeChildren(NodeLowHigh nlh, Queue queue){
		Node node = nlh.node;
		int val = node.getValue();
		Node left = new Node((nlh.low + val)/2);
		node.insert(left);
		Node right = new Node((val + nlh.high)/2);
		node.insert(right);
		if (nlh.high != right.getValue()+1){
			queue.add(new NodeLowHigh(left, nlh.low, val));
			queue.add(new NodeLowHigh(right, val, nlh.high));
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
