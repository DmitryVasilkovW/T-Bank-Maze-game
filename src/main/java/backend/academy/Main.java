package backend.academy;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import backend.academy.maze.service.io.Renderer;
import backend.academy.maze.service.io.impl.SimpleRenderer;
import backend.academy.maze.service.solver.Solver;
import backend.academy.maze.service.solver.impl.AStarSolver;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Generator generator = new DFSGenerator();
        Solver solver = new AStarSolver();
        Renderer renderer = new SimpleRenderer();

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        Maze maze = generator.generate(10, 10, start, end);
        System.out.println(renderer.render(maze));

        List<Coordinate> path = solver.solve(maze, start, end);

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            System.out.println(renderer.render(maze, path));
        } else {
            System.out.println("No path found.");
        }
    }
}
