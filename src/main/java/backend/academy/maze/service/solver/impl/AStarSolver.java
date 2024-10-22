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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {
    private final Maze maze;
    private final int[][] directions;
    private final CostHandlerChain costHandlerChain;
    private PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::totalCost));
    private HashMap<Coordinate, Double> costMap = new HashMap<>();
    private HashMap<Coordinate, Coordinate> cameFrom = new HashMap<>();
    private HashSet<Coordinate> visited = new HashSet<>();
    private static final double INITIAL_PRIORITY_FOR_NODE = 0.0;
    private static final int X_AXIS_COORDINATE_INDEX = 0;
    private static final int Y_AXIS_COORDINATE_INDEX = 1;
    private static final PassageType TYPE_OF_PASSAGE = PassageType.PASSAGE;

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
        for (Coordinate neighbor : getNeighbors(currentCoord)) {
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

    private double heuristic(Coordinate first, Coordinate second) {
        return Math.abs(first.row() - second.row()) + Math.abs(first.col() - second.col());
    }

    private List<Coordinate> getNeighbors(Coordinate coord) {
        var neighbors = new ArrayList<Coordinate>();

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[X_AXIS_COORDINATE_INDEX];
            int newCol = coord.col() + dir[Y_AXIS_COORDINATE_INDEX];

            if (isValidMove(newRow, newCol)) {
                Cell neighbor = maze.getCell(newRow, newCol);
                if (neighbor.type() == TYPE_OF_PASSAGE) {
                    neighbors.add(new Coordinate(newRow, newCol));
                }
            }
        }

        return neighbors;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < maze.height() && col >= 0 && col < maze.width();
    }

    private List<Coordinate> reconstructPath(HashMap<Coordinate, Coordinate> cameFrom, Coordinate current) {
        var path = new ArrayList<Coordinate>();
        var tmpCurrent = new Coordinate(current.row(), current.col());

        while (cameFrom.containsKey(tmpCurrent)) {
            path.add(tmpCurrent);
            tmpCurrent = cameFrom.get(tmpCurrent);
        }
        path.add(tmpCurrent);

        Collections.reverse(path);
        return path;
    }
}
