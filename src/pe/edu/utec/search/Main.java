package pe.edu.utec.search;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        File maze1 = new File("resources/lab3.txt");
//        File maze2 = new File("src/main/resources/maze/maze2.txt");

        execute(maze1);
//        execute(maze2);
    }

    private static void execute(File file) throws Exception {
        Maze maze = new Maze(file);
        dfs(maze);
        bfs(maze);
    }

    private static void bfs(Maze maze) {
        BFS bfs = new BFS();
        List<Node> path = bfs.solve(maze);
        maze.printPath(path);
        maze.reset();
    }

    private static void dfs(Maze maze) {
        DFS dfs = new DFS();
        List<Node> path = dfs.solve(maze);
        maze.printPath(path);
        maze.reset();
    }
}