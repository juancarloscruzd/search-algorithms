package pe.edu.utec.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BFS {
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    private static int depth = 0;
    private static int maxFrontierSize = 0;
    private LinkedList<Node> frontier = new LinkedList<>(); // equivalent to the Edge data structure
	private Set<Node> exploredNodes = new HashSet<Node>();

	public List<Node> solve(MazeMap mazeMap) {
        
        
        Node start = mazeMap.getEntry();
        frontier.add(start);

        while (!frontier.isEmpty()) {
        	
        	if (frontier.size() > maxFrontierSize) maxFrontierSize = frontier.size();
        	
        	Node cur = frontier.remove();

            if (!mazeMap.isValidLocation(cur.getX(), cur.getY()) || mazeMap.isExplored(cur.getX(), cur.getY())) {
                continue;
            }

            if (mazeMap.isWall(cur.getX(), cur.getY())) {
                mazeMap.setVisited(cur.getX(), cur.getY(), true);
                exploredNodes.add(cur);
                continue;
            }

            if (mazeMap.isExit(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
            	Node node = new Node(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                frontier.add(node);
                mazeMap.setVisited(cur.getX(), cur.getY(), true);
                exploredNodes.add(cur);
            }
            depth++;
        }
        return Collections.emptyList();
    }

    private List<Node> backtrackPath(Node cur) {
        List<Node> path = new ArrayList<>();
        Node iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }
    
    public int getDepth() {
		return depth;
	}
    
    public int getMaxFrontierSize() {
		return maxFrontierSize;
	}
    
    public LinkedList<Node> getFrontier() {
		return frontier;
	}

	public Set<Node> getExploredNodes() {
		return exploredNodes;
	}
}