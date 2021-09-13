package spell;

public class Node implements INode {
    INode [] children;
    int count;

    public Node() {
        children = new Node [26];
        count = 0;
    }

    /**
     * Returns the frequency count for the word represented by the node.
     *
     * @return the frequency count for the word represented by the node.
     */
    public int getValue(){
        return count;
    }

    /**
     * Increments the frequency count for the word represented by the node.
     */
    public void incrementValue() {
        count++;
    }

    /**
     * Returns the child nodes of this node.
     *
     * @return the child nodes.
     */
    public INode[] getChildren(){
        return children;
    }
}
