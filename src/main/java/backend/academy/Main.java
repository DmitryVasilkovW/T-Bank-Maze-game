package backend.academy;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.impl.PrimGenerator;
import backend.academy.maze.service.io.Renderer;
import backend.academy.maze.service.io.impl.SimpleRenderer;
import backend.academy.maze.service.solver.Solver;
import backend.academy.maze.service.solver.impl.AStarSolver;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Generator generator = new PrimGenerator();
        int[][] directions = {
                {0, 1},   // ->
                {0, -1},  // <-
                {1, 0},   // \|/
                {-1, 0},  // ^
                //{1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        Solver solver = new AStarSolver(directions);
        Renderer renderer = new SimpleRenderer();

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(14, 14);

        Maze maze = generator.generate(15, 15, start, end);
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
