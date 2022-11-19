package pe.edu.utec.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFS {
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    private static int depth = 0;
    private static int maxFrontierSize = 0;
    private Stack<Node> frontier = new Stack<>(); // equivalent to the Edge data structure
	private Set<Node> exploredNodes = new HashSet<Node>();

    public List<Node> solve(MazeMap maze) {
        List<Node> path = new ArrayList<>(); 
    	frontier.push(maze.getEntry());
    	while(!frontier.isEmpty()) {
    		if(frontier.size() > maxFrontierSize) maxFrontierSize = frontier.size();
    		Node curNode = frontier.pop();
    		if (!maze.isValidLocation(curNode.getX(), curNode.getY()) || maze.isWall(curNode.getX(), curNode.getY()) || maze.isExplored(curNode.getX(), curNode.getY())) {
                continue;
            }
    		
    		path.add(curNode);
    		maze.setVisited(curNode.getX(), curNode.getY(), true);
    		
    		if (maze.isExit(curNode.getX(), curNode.getY())) return path;
    		for (int[] direction : DIRECTIONS) {
                Node node = getNextNode(curNode.getX(), curNode.getY(), direction[0], direction[1]);
                frontier.push(node);
            }
    		exploredNodes.add(curNode);
    		depth++;
    	}
    	return Collections.emptyList();
    }

    private Node getNextNode(int row, int col, int i, int j) {
        return new Node(row + i, col + j);
    }
    
    public int getDepth() {
		return depth;
	}
    
    public int getMaxFrontierSize() {
		return maxFrontierSize;
	}
    
    public Stack<Node> getFrontier() {
		return frontier;
	}

	public Set<Node> getExploredNodes() {
		return exploredNodes;
	}
}