package pe.edu.utec.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {
    private static int DEFAULT_HV_COST = 10;
    private int hvCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> frontier; // equivalent to the Edge data structure
    private Set<Node> explored;
    private Node initialNode;
    private Node finalNode;
    private MazeMap mazeMap;
    private static int depth = 0;
    private static int maxFrontierSize = 0;

    public AStar(MazeMap mazeMap, int hvCost) {
        this.mazeMap = mazeMap;
    	this.hvCost = hvCost;
        setInitialNode(mazeMap.getEntry());
        setFinalNode(mazeMap.getExit());
        this.searchArea = new Node[mazeMap.getHeight()][mazeMap.getWidth()];
        this.frontier = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        setBlocks();
        this.explored = new HashSet<>();
    }
 
    public AStar(MazeMap mazeMap) {
        this(mazeMap, DEFAULT_HV_COST);
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    private void setBlocks() {
    	for (int row = 0; row < mazeMap.getHeight(); row++) {
            for (int col = 0; col < mazeMap.getWidth(); col++) {
                if(mazeMap.getMaze()[row][col] == 1) {
                	setBlock(row, col);
                }
            }
        }
    }

    public List<Node> solve() {
        frontier.add(initialNode);
        while (!isEmpty(frontier)) {
        	if (frontier.size() > maxFrontierSize) maxFrontierSize = frontier.size();
            Node currentNode = frontier.poll();
            explored.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
            depth++;
        }
        return Collections.emptyList();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getExploredNodes().contains(adjacentNode)) {
            if (!getFrontier().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getFrontier().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                	getFrontier().remove(adjacentNode);
                	getFrontier().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getFrontier() {
        return frontier;
    }

    public void setFrontier(PriorityQueue<Node> frontier) {
        this.frontier = frontier;
    }

    public Set<Node> getExploredNodes() {
        return explored;
    }

    public void setExplored(Set<Node> explored) {
        this.explored = explored;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }
    
    public int getDepth() {
		return depth;
	}
    
    public int getMaxFrontierSize() {
		return maxFrontierSize;
	}
}
