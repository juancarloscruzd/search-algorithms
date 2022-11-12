package pe.edu.utec.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS {
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Node> solve(Maze maze) {
        List<Node> path = new ArrayList<>();
        if (explore(maze, maze.getEntry()
            .getX(),
            maze.getEntry()
                .getY(),
            path)) {
            return path;
        }
        return Collections.emptyList();
    }

    private boolean explore(Maze maze, int row, int col, List<Node> path) {
        if (!maze.isValidLocation(row, col) || maze.isWall(row, col) || maze.isExplored(row, col)) {
            return false;
        }

        path.add(new Node(row, col));
        maze.setVisited(row, col, true);

        if (maze.isExit(row, col)) {
            return true;
        }

        for (int[] direction : DIRECTIONS) {
            Node coordinate = getNextCoordinate(row, col, direction[0], direction[1]);
            if (explore(maze, coordinate.getX(), coordinate.getY(), path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private Node getNextCoordinate(int row, int col, int i, int j) {
        return new Node(row + i, col + j);
    }
}