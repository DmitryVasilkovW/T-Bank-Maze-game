package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.SurfaceType;
import backend.academy.maze.model.PassageType;
import backend.academy.maze.service.generator.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        Maze maze = new Maze(height, width);
        initializeMaze(maze);

        maze.getCell(start.row(), start.col()).type(PassageType.PASSAGE);
        List<Edge> edges = new ArrayList<>();
        addEdges(maze, edges, start.row(), start.col());

        while (!edges.isEmpty()) {
            Edge edge = edges.remove(random.nextInt(edges.size()));
            if (isValidEdge(maze, edge)) {
                maze.getCell(edge.row1, edge.col1).type(PassageType.PASSAGE);
                maze.getCell(edge.row2, edge.col2).type(PassageType.PASSAGE);

                removeWall(maze, edge);

                maze.getCell(edge.row1, edge.col1).surface(getRandomSurface());
                maze.getCell(edge.row2, edge.col2).surface(getRandomSurface());

                addEdges(maze, edges, edge.row2, edge.col2);
            }
        }

        setStartAndEnd(maze, start, end);
        return maze;
    }

    private void initializeMaze(Maze maze) {
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                maze.getCell(row, col).type(PassageType.WALL);
            }
        }
    }

    private void addEdges(Maze maze, List<Edge> edges, int row, int col) {
        for (Coordinate direction : List.of(
                new Coordinate(0, 1), // Вправо
                new Coordinate(1, 0), // Вниз
                new Coordinate(0, -1), // Влево
                new Coordinate(-1, 0) // Вверх
        )) {
            int newRow = row + direction.row() * 2;
            int newCol = col + direction.col() * 2;
            if (isInBounds(newRow, newCol, maze) && maze.getCell(newRow, newCol).type() == PassageType.WALL) {
                edges.add(new Edge(row, col, newRow, newCol));
            }
        }
    }

    private boolean isInBounds(int row, int col, Maze maze) {
        return row >= 0 && col >= 0 && row < maze.height() && col < maze.width();
    }

    private boolean isValidEdge(Maze maze, Edge edge) {
        return maze.getCell(edge.row2, edge.col2).type() == PassageType.WALL;
    }

    private void removeWall(Maze maze, Edge edge) {
        int wallRow = (edge.row1 + edge.row2) / 2;
        int wallCol = (edge.col1 + edge.col2) / 2;
        maze.getCell(wallRow, wallCol).type(PassageType.PASSAGE);
    }

    private void setStartAndEnd(Maze maze, Coordinate start, Coordinate end) {
        maze.getCell(start.row(), start.col()).type(PassageType.PASSAGE);
        maze.getCell(start.row(), start.col()).surface(SurfaceType.START);

        maze.getCell(end.row(), end.col()).type(PassageType.PASSAGE);
        maze.getCell(end.row(), end.col()).surface(SurfaceType.END);
    }

    private SurfaceType getRandomSurface() {
        int randomValue = random.nextInt(10);
        if (randomValue < 2) {
            return SurfaceType.MUD;
        } else if (randomValue < 4) {
            return SurfaceType.SAND;
        } else if (randomValue == 9) {
            return SurfaceType.COIN;
        } else {
            return SurfaceType.NORMAL;
        }
    }

    private static class Edge {
        int row1, col1, row2, col2;

        Edge(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }
}