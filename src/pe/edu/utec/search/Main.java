package pe.edu.utec.search;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/*
 * Ingregrantes: 
 * - Cruz Díaz, Juan Carlos
 * - Anchiraico Orozco, Marco Antonio
 * 
 * */

public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws Exception {
        File maze1 = new File("resources/lab1.txt");
        File maze2 = new File("resources/lab2.txt");
        File maze3 = new File("resources/lab3.txt");

        logger.info("Laberinto 1");
        execute(maze1);
        logger.info("Laberinto 2");
        execute(maze2);
        logger.info("Laberinto 3");
        execute(maze3);
    }

    private static void execute(File file) throws Exception {
        MazeMap maze = new MazeMap(file);
        dfs(maze);
        bfs(maze);
        astar(maze);
    }
    
    private static void printResults(MazeMap mazeMap, List<Node> path, String type, int maxDepth, int frontierSize, int maxFrontierSize) {
    	System.out.println(String.format("Laberinto con ruta óptima [%s]:", type));
        mazeMap.printPath(path);
        System.out.println(String.format("Longitud de la ruta encontrada: %s", path.size()));
        System.out.println(String.format("Máxima profundidad alcanzada: %s", maxDepth));
        System.out.println(String.format("Número de nodos en la frontera: %s", frontierSize));
        System.out.println(String.format("Tamaño máximo alacazado por la frontera: %s", maxFrontierSize));
        System.out.println("============================================= \n");
    }
    
    private static void astar(MazeMap mazeMap) {
        AStar aStar = new AStar(mazeMap);
        List<Node> path = aStar.solve();
        printResults(mazeMap, path, "A*", aStar.getDepth(), aStar.getFrontier().size(), aStar.getMaxFrontierSize());
        mazeMap.reset();
    }

    private static void bfs(MazeMap mazeMap) {
        BFS bfs = new BFS();
        List<Node> path = bfs.solve(mazeMap);
        printResults(mazeMap, path, "BFS", bfs.getDepth()/4, bfs.getFrontier().size(), bfs.getMaxFrontierSize());
        mazeMap.reset();
    }

    private static void dfs(MazeMap mazeMap) {
        DFS dfs = new DFS();
        List<Node> path = dfs.solve(mazeMap);
        printResults(mazeMap, path, "DFS", dfs.getDepth(), dfs.getFrontier().size(), dfs.getMaxFrontierSize());
        mazeMap.reset();
    }
}