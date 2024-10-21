package backend.academy.maze.service.solver.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.solver.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Cell[][] grid = maze.grid();
        int height = grid.length;
        int width = grid[0].length;
        boolean[][] visited = new boolean[height][width];
        Map<Coordinate, Coordinate> parentMap = new HashMap<>();
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        visited[start.row()][start.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                return reconstructPath(parentMap, end);
            }

            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = current.row() + direction[0];
                int newCol = current.col() + direction[1];
                if (isInBounds(newRow, newCol, height, width) &&
                        !visited[newRow][newCol] &&
                        grid[newRow][newCol].type() == PassageType.PASSAGE) {
                    visited[newRow][newCol] = true;
                    Coordinate neighbor = new Coordinate(newRow, newCol);
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> parentMap, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private boolean isInBounds(int row, int col, int height, int width) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
}
