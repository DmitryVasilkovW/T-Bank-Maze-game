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
import java.util.Queue;

public class BFSSolver implements Solver {
    private final Maze maze;
    private final int[][] directions;
    private Queue<Coordinate> queue;
    private HashMap<Coordinate, Coordinate> parentMap;
    private boolean[][] visited;
    private Cell[][] grid;
    private int height;
    private int width;

    public BFSSolver(int[][] directions, Maze maze) {
        this.maze = maze;
        this.directions = directions;
    }

    @Override
    public List<Coordinate> solve(Coordinate start, Coordinate end) {
        resetCollectionsAndParametersForSolving(start);
        return getPathToEnd(end);
    }

    private void resetCollectionsAndParametersForSolving(Coordinate start) {
        grid = maze.grid();
        height = grid.length;
        width = grid[0].length;
        visited = new boolean[height][width];
        parentMap = new HashMap<>();
        queue = new LinkedList<>();
        queue.add(start);
        visited[start.row()][start.col()] = true;
    }

    private List<Coordinate> getPathToEnd(Coordinate end) {
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(parentMap, end);
            }

            tryAllDirections(current);
        }

        return Collections.emptyList();
    }

    private void tryAllDirections(Coordinate current) {
        for (int[] direction : directions) {
            int newRow = current.row() + direction[0];
            int newCol = current.col() + direction[1];
            if (isInBounds(newRow, newCol)
                    && !visited[newRow][newCol]
                    && grid[newRow][newCol].type() == PassageType.PASSAGE) {
                visited[newRow][newCol] = true;
                Coordinate neighbor = new Coordinate(newRow, newCol);
                queue.add(neighbor);
                parentMap.put(neighbor, current);
            }
        }
    }

    private List<Coordinate> reconstructPath(HashMap<Coordinate, Coordinate> parentMap, Coordinate end) {
        var path = new ArrayList<Coordinate>();
        Coordinate current = end;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
}
