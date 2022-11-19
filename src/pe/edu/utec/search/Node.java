package pe.edu.utec.search;

public class Node {
	
    int x;
    int y;
    int g;
    int f;
    int h;
    boolean isBlock;
    Node parent;

    public Node(int x, int y) {
    	
        this.x = x;
        this.y = y;
        this.parent = null;
    }

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Node getParent() {
        return parent;
    }
    
    public void calculateHeuristic(Node finalNode) {
    	this.h = (int) Math.pow( Math.pow(getX() - finalNode.getX(), 2) + Math.pow(getY() - finalNode.getY(), 2), 1/2);
    }
    
    public void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }
    
    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return this.getX() == other.getX() && this.getY() == other.getY();
    }

    @Override
    public String toString() {
        return "Node [row=" + x + ", col=" + y + "]";
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
}