import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;

public class QueueKeyTreap extends BST{
    public QueueKeyTreap(int lgN, IntStream accessSequence){
        this(lgN, new BstCounter(), accessSequence);
    }
    public QueueKeyTreap(int lgN, BstCounter bstCounter, IntStream accessSequence){
        this.bstCounter = bstCounter;
        int n = 1 << lgN;
        List<QueueKeyTreapNode> nodes = new ArrayList<>(n);
        //create all the nodes
		System.out.println("creating nodes");
        for (int i=0; i<n; i++){
            nodes.add(new QueueKeyTreapNode(i));
        }
        //load them all up with their accesses
        System.out.println("loading accesses");
        int[] i = {0};
        int[] rootKey = {-1};
        accessSequence.forEachOrdered(a -> {
                                      if(i[0]==0)rootKey[0]=a;
                                      nodes.get(a).addAccess(i[0]);
                                      i[0]++;
                                   });
		System.out.println("building tree");
        QueueKeyTreapNode rootNode = nodes.get(rootKey[0]);
        insert(rootNode);
        makeTreap(rootNode, nodes, 0, rootNode.getValue()-1);
        makeTreap(rootNode, nodes, rootNode.getValue()+1, nodes.size()-1);
        
    }
    private void makeTreap(
        QueueKeyTreapNode parent,
        List<QueueKeyTreapNode> nodes,
        int left,
        int right)
    {
        if (left >= right){
            return;
        }
        int maxHeapKey = -1;
        int treeKeyOfMaxHeapKey = -1;
        QueueKeyTreapNode node = null;
        for (int i=left; i<=right; i++){
            node = nodes.get(i);
            if (node.heapKey() > maxHeapKey){
                maxHeapKey = node.heapKey();
                treeKeyOfMaxHeapKey = i;
            }
        }
        node = nodes.get(treeKeyOfMaxHeapKey);
        parent.insert(node);
        makeTreap(node, nodes, left, treeKeyOfMaxHeapKey - 1);
        makeTreap(node, nodes, treeKeyOfMaxHeapKey + 1, right);
    }
    public Node makeNode(int value){
        return null;
    }
    @Override
    protected void afterFind(Node found){
        if (found.isNull()){
            return;
        }
        QueueKeyTreapNode node = (QueueKeyTreapNode) found;
        node.access();
        int key = node.heapKey();
        Node left = node.getLeft();
        Node right = node.getRight();
        int leftKey = getHeapKey(left);
        int rightKey = getHeapKey(right);
        bstCounter.increment();
        bstCounter.increment();
        while(key > leftKey || key > rightKey){
            if(leftKey < rightKey){
                rotateUp(left);
                left = node.getLeft();
                leftKey = getHeapKey(left);
            }else{
                rotateUp(right);
                right = node.getRight();
                rightKey = getHeapKey(right);
            }
            bstCounter.increment();
        }
    }
    public int getHeapKey(Node node){
        if (node.isNull()){
            return Integer.MAX_VALUE;
        }
        return ((QueueKeyTreapNode)node).heapKey();
    }
}
class QueueKeyTreapNode extends BstNode{
    protected Queue<Integer> queueKey = new LinkedList<>();
    public QueueKeyTreapNode(int value){
        super(value);
        
    }
    public int heapKey(){
        Integer heapKey = queueKey.peek();
        if (heapKey == null){
            return Integer.MAX_VALUE;
        }
        return heapKey;
    }
    public void addAccess(int a){
        queueKey.add(a);
    }
    public void access(){
        queueKey.remove();
    }
}
