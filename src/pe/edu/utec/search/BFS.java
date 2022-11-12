package pe.edu.utec.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BFS {
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Node> solve(Maze maze) {
        LinkedList<Node> nextToVisit = new LinkedList<>();
        Node start = maze.getEntry();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
        	Node cur = nextToVisit.remove();

            if (!maze.isValidLocation(cur.getX(), cur.getY()) || maze.isExplored(cur.getX(), cur.getY())) {
                continue;
            }

            if (maze.isWall(cur.getX(), cur.getY())) {
                maze.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (maze.isExit(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
            	Node coordinate = new Node(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                nextToVisit.add(coordinate);
                maze.setVisited(cur.getX(), cur.getY(), true);
            }
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
}