package pe.edu.utec.search;

public class Node {
    int x;
    int y;
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
}