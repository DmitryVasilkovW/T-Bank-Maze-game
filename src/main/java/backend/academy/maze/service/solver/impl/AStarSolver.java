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

    private final Maze maze;
    private final int[][] directions;
    private final CostHandlerChain costHandlerChain;
    private PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::totalCost));
    private HashMap<Coordinate, Double> costMap = new HashMap<>();
    private HashMap<Coordinate, Coordinate> cameFrom = new HashMap<>();
    private HashSet<Coordinate> visited = new HashSet<>();
    private static final double INITIAL_PRIORITY_FOR_NODE = 0.0;

    public AStarSolver(int[][] directions, CostHandlerChain costHandlerChain, Maze maze) {
        this.directions = directions;
        this.costHandlerChain = costHandlerChain;
        this.maze = maze;
    }

    @Override
    public List<Coordinate> solve(Coordinate start, Coordinate end) {
        resetCollectionsForSolving();
        initCollectionsForSolving(start, end);

        return getPathToEnd(end);
    }

    private void resetCollectionsForSolving() {
        openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::totalCost));
        costMap = new HashMap<>();
        cameFrom = new HashMap<>();
        visited = new HashSet<>();
    }

    private void initCollectionsForSolving(Coordinate start, Coordinate end) {
        openSet.add(new Node(start, heuristic(start, end)));
        costMap.put(start, INITIAL_PRIORITY_FOR_NODE);
    }

    private List<Coordinate> getPathToEnd(Coordinate end) {
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

            updateCostsForNeighbor(currentCoord, end);
        }

        return Collections.emptyList();
    }

    private void updateCostsForNeighbor(Coordinate currentCoord, Coordinate end) {
        for (Coordinate neighbor : getNeighbors(currentCoord, maze)) {
            if (visited.contains(neighbor)) {
                continue;
            }

            double newCost = getCostForNeighbor(currentCoord, neighbor);
            if (newCost < costMap.getOrDefault(neighbor, Double.MAX_VALUE)) {
                costMap.put(neighbor, newCost);
                double priority = newCost + heuristic(neighbor, end);
                openSet.add(new Node(neighbor, priority));
                cameFrom.put(neighbor, currentCoord);
            }
        }
    }

    private double getCostForNeighbor(Coordinate currentCoord, Coordinate neighbor) {
        Cell neighborCell = maze.getCell(neighbor.row(), neighbor.col());
        return costMap.get(currentCoord) + getMoveCost(neighborCell);
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
