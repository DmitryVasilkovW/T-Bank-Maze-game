package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Edge;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.service.RandomSufferGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {
    private final Random random = new Random();
    private final RandomSufferGenerator sufferGenerator;
    private final List<Coordinate> directions;
    private Maze maze;
    private List<Edge> edges;
    private static final int DISTANCE_MULTIPLIER = 2;
    private static final int DISTANCE_DIVIDER = 2;

    public PrimGenerator(RandomSufferGenerator sufferGenerator, List<Coordinate> directions) {
        this.sufferGenerator = sufferGenerator;
        this.directions = directions;
    }

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        setUpGenerator(height, width, start);

        handleAllEdges();
        setStartAndEnd(maze, start, end);
        return maze;
    }

    private void setStartAndEnd(Maze maze, Coordinate start, Coordinate end) {
        maze.getCell(start.row(), start.col()).type(PassageType.PASSAGE);
        maze.getCell(start.row(), start.col()).surface(SurfaceType.START);

        maze.getCell(end.row(), end.col()).type(PassageType.PASSAGE);
        maze.getCell(end.row(), end.col()).surface(SurfaceType.END);
    }

    private void setUpGenerator(int height, int width, Coordinate start) {
        maze = new Maze(height, width);
        maze.getCell(start.row(), start.col()).type(PassageType.PASSAGE);

        edges = new ArrayList<>();
        addEdges(maze, start.row(), start.col());
    }

    private void handleAllEdges() {
        while (!edges.isEmpty()) {
            Edge edge = edges.remove(random.nextInt(edges.size()));
            if (isValidEdge(maze, edge)) {
                maze.getCell(edge.firstRow(), edge.firstCol()).type(PassageType.PASSAGE);
                maze.getCell(edge.secondRow(), edge.secondCol()).type(PassageType.PASSAGE);

                removeWall(maze, edge);

                maze.getCell(edge.firstRow(), edge.firstCol()).surface(sufferGenerator.getRandomSurface());
                maze.getCell(edge.secondRow(), edge.secondCol()).surface(sufferGenerator.getRandomSurface());

                addEdges(maze, edge.secondRow(), edge.secondCol());
            }
        }
    }

    private void removeWall(Maze maze, Edge edge) {
        int wallRow = (edge.firstRow() + edge.secondRow()) / DISTANCE_DIVIDER;
        int wallCol = (edge.firstCol() + edge.secondCol()) / DISTANCE_DIVIDER;
        maze.getCell(wallRow, wallCol).type(PassageType.PASSAGE);
    }

    private boolean isValidEdge(Maze maze, Edge edge) {
        return maze.getCell(edge.secondRow(), edge.secondCol()).type() == PassageType.WALL;
    }

    private void addEdges(Maze maze, int row, int col) {
        for (Coordinate direction : directions) {
            int newRow = row + direction.row() * DISTANCE_MULTIPLIER;
            int newCol = col + direction.col() * DISTANCE_MULTIPLIER;
            if (isValidMove(newRow, newCol) && maze.getCell(newRow, newCol).type() == PassageType.WALL) {
                edges.add(new Edge(row, col, newRow, newCol));
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && col >= 0 && row < maze.height() && col < maze.width();
    }

}
