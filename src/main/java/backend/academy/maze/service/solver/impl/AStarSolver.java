package backend.academy.maze.service.solver.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.Node;
import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.CostRequest;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.solver.Solver;
import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;

import java.util.*;

public class AStarSolver implements Solver {
    private final int[][] directions;
    private final CostHandlerChain costHandlerChain;

    public AStarSolver(int[][] directions, CostHandlerChain costHandlerChain) {
        this.directions = directions;
        this.costHandlerChain = costHandlerChain;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::totalCost));
        Map<Coordinate, Double> costMap = new HashMap<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Set<Coordinate> visited = new HashSet<>();

        openSet.add(new Node(start,  heuristic(start, end)));
        costMap.put(start, 0.0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.coordinate();

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
                    openSet.add(new Node(neighbor, priority));
                    cameFrom.put(neighbor, currentCoord);
                }
            }
        }

        return Collections.emptyList();
    }

    private double getMoveCost(Cell cell) {
        Cost cost = costHandlerChain.handle(new CostRequest(cell.surface()));
        return cost.cost();
    }

    private double heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private List<Coordinate> getNeighbors(Coordinate coord, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[0];
            int newCol = coord.col() + dir[1];

            if (newRow >= 0 && newRow < maze.height() && newCol >= 0 && newCol < maze.width()) {
                Cell neighborCell = maze.getCell(newRow, newCol);
                if (neighborCell.type() == PassageType.PASSAGE) {
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
}
