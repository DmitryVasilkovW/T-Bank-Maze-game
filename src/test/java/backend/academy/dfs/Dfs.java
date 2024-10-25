package backend.academy.dfs;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import java.util.Set;

public class Dfs {
    public static void dfsValidate(Maze maze, Coordinate coord, Set<Coordinate> visited) {
        if (coord.row() < 0 || coord.row() >= maze.height() ||
                coord.col() < 0 || coord.col() >= maze.width()) {
            return;
        }

        Cell cell = maze.getCell(coord.row(), coord.col());
        if (cell.type() != PassageType.PASSAGE || visited.contains(coord)) {
            return;
        }

        visited.add(coord);

        dfsValidate(maze, new Coordinate(coord.row() + 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row() - 1, coord.col()), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() + 1), visited);
        dfsValidate(maze, new Coordinate(coord.row(), coord.col() - 1), visited);
    }
}
