package backend.academy.maze.service.solver;

import backend.academy.maze.model.Coordinate;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Coordinate start, Coordinate end);
}
