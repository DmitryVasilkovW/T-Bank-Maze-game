package backend.academy.maze.service.solver.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.solver.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
        Map<Coordinate, Integer> costMap = new HashMap<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();

        openSet.add(new Node(start, 0, heuristic(start, end)));
        costMap.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.getCoordinate();

            if (currentCoord.equals(end)) {
                return reconstructPath(cameFrom, end);
            }

            for (Coordinate neighbor : getNeighbors(currentCoord, maze)) {
                Cell neighborCell = maze.getCell(neighbor.row(), neighbor.col());
                int newCost = costMap.get(currentCoord) + getMoveCost(neighborCell);

                if (newCost < costMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    costMap.put(neighbor, newCost);
                    int priority = newCost + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, newCost, priority));
                    cameFrom.put(neighbor, currentCoord);
                }
            }
        }

        return Collections.emptyList();
    }

    private int getMoveCost(Cell cell) {
        switch (cell.getSurface()) {
            case MUD: return 5;
            case SAND: return 3;
            case COIN: return 1;
            default: return 2;
        }
    }

    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private List<Coordinate> getNeighbors(Coordinate coord, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();

        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[0];
            int newCol = coord.col() + dir[1];

            if (newRow >= 0 && newRow < maze.getHeight() && newCol >= 0 && newCol < maze.getWidth()) {
                Cell neighborCell = maze.getCell(newRow, newCol);
                if (neighborCell.getType() == Cell.Type.PASSAGE) {
                    neighbors.add(new Coordinate(newRow, newCol));
                }
            }
        }

        return neighbors;
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate current) {
        List<Coordinate> path = new ArrayList<>();

        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(current);

        Collections.reverse(path);
        return path;
    }

    private static class Node {
        private final Coordinate coordinate;
        private final int cost;
        private final int totalCost;

        public Node(Coordinate coordinate, int cost, int totalCost) {
            this.coordinate = coordinate;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public int getTotalCost() {
            return totalCost;
        }
    }
}

