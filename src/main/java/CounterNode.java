class CounterNode extends BST{
    final protected BstCounter bstCounter;
    CounterNode(int value, BstCounter bstCounter){
        super(value);
        this.bstCounter = bstCounter;
    }
    @Override
    public void insert(int toInsert){
        insert(new CounterNode(toInsert, bstCounter));
    }
    @Override
    public void rotateUp(){
        bstCounter.increment();
        super.rotateUp();
    }
    @Override
    public BST getLeft(){
        bstCounter.increment();
        return super.getLeft();
    }
    @Override
    public BST getRight(){
        bstCounter.increment();
        return super.getRight();
    }
    @Override
    public BST getParent(){
        bstCounter.increment();
        return super.getParent();
    }
    public long getCount(){
        return bstCounter.getCount();
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
