class Node{
    private static Node nullNode = new Node(0);
    private Node parent = nullNode;
    private Node left = nullNode;
    private Node right = nullNode;
    private final int value;

    public Node(int value){
        this.value = value;
    }
    public void detach(){
        if (parent.left == this){
            parent.left = nullNode;
        }else{
            parent.right = nullNode;
        }
        this.parent = nullNode;
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
        if (parent == nullNode){
            System.err.println("Cannot rotate root with its parent");
            return;
        }
        Node oldGrandParent = parent.parent;
        parent.detach();
        Node oldParent = parent;
        detach();
        Node oldChild;
        if (this == parent.right){
            oldChild = detachLeft();
            
        } else {
            oldChild = detachRight();
        }
        // I give my child to my parent.
        // My parent becomes my child.
        // I become the child of my grandparent
        oldParent.insert(oldChild);
        this.insert(oldParent);
        if (oldGrandParent != nullNode){
            oldGrandParent.insert(this);
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
    public Node getRoot(){
        if (parent == nullNode){
            return this;
        }else{
            return parent.getRoot();
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
}

