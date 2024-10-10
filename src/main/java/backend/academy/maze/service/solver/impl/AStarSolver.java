package backend.academy.maze.service.solver.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.solver.Solver;

import java.util.*;

public class AStarSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getTotalCost));
        Map<Coordinate, Double> costMap = new HashMap<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Set<Coordinate> visited = new HashSet<>();

        openSet.add(new Node(start, 0, heuristic(start, end)));
        costMap.put(start, 0.0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.getCoordinate();

            if (currentCoord.equals(end)) {
                return reconstructPath(cameFrom, end);
            }

            if (visited.contains(currentCoord)) {
                continue;
            }
            visited.add(currentCoord);

            for (Coordinate neighbor : getNeighbors(currentCoord, maze)) {
                if (visited.contains(neighbor)) {
                    continue;
                }

                Cell neighborCell = maze.getCell(neighbor.row(), neighbor.col());
                double newCost = costMap.get(currentCoord) + getMoveCost(neighborCell);

                if (newCost < costMap.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    costMap.put(neighbor, newCost);
                    double priority = newCost + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, newCost, priority));
                    cameFrom.put(neighbor, currentCoord);
                }
            }
        }

        return Collections.emptyList();
    }

    private double getMoveCost(Cell cell) {
        switch (cell.getSurface()) {
            case MUD:
                return 5;
            case SAND:
                return 3;
            case COIN:
                return -2;
            default:
                return 1;
        }
    }

    private double heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private List<Coordinate> getNeighbors(Coordinate coord, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();

        int[][] directions = {
                {0, 1},   // ->
                {0, -1},  // <-
                {1, 0},   // \|/
                {-1, 0}   // ^
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
        private final double cost;
        private final double totalCost;

        public Node(Coordinate coordinate, double cost, double totalCost) {
            this.coordinate = coordinate;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public double getTotalCost() {
            return totalCost;
        }
    }
}
