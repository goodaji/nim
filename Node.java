import java.util.ArrayList;

class Node<T> {
    private final T root;
    private Node<T> parent;
    private final ArrayList<Node<T>> children;

    public Node(T root) {
        this.root = root;
        children = new ArrayList<>();
    }

    public Node<T> addChild(T child) {
        Node<T> childNode = new Node<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public T getRood() {
        return root;
    }

    public boolean() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    @Override
    public String toString() {
        return root != null ? root.toString() : "null";
    }
}