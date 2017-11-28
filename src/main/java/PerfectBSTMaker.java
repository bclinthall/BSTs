import java.util.Queue;
import java.util.LinkedList;

public class PerfectBSTMaker{
	public static BST makePerfectBST(int lgN){
		int n = 1 << lgN;
		Queue<NodeLowHigh> queue = new LinkedList<>();
		BST node = new BST(n >> 1);
		queue.add(new NodeLowHigh(node, 1, n));
		while (!queue.isEmpty()){
			makeChildren(queue.remove(), queue);
		}
		return node;
	}
	public static void makeChildren(NodeLowHigh nlh, Queue queue){
		BST node = nlh.node;
		int val = node.getValue();
		BST left = new BST((nlh.low + val)/2);
		node.insert(left);
		BST right = new BST((val + nlh.high)/2);
		node.insert(right);
		if (nlh.high != right.getValue()+1){
			queue.add(new NodeLowHigh(left, nlh.low, val));
			queue.add(new NodeLowHigh(right, val, nlh.high));
		}
	}
}

class NodeLowHigh{
	BST node;
	int high;
	int low;
	NodeLowHigh(BST node, int low, int high){
    	this.node = node;
    	this.low = low;
    	this.high = high;
	}
}
