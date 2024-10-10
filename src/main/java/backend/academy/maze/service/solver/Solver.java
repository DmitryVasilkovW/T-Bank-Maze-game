package backend.academy.maze.service.solver;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}