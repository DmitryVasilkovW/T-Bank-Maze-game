package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
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

        maze.getCell(start.row(), start.col()).setType(Cell.Type.PASSAGE);
        List<Edge> edges = new ArrayList<>();
        addEdges(maze, edges, start.row(), start.col());

        while (!edges.isEmpty()) {
            Edge edge = edges.remove(random.nextInt(edges.size()));
            if (isValidEdge(maze, edge)) {
                maze.getCell(edge.row1, edge.col1).setType(Cell.Type.PASSAGE);
                maze.getCell(edge.row2, edge.col2).setType(Cell.Type.PASSAGE);

                removeWall(maze, edge);

                maze.getCell(edge.row1, edge.col1).setSurface(getRandomSurface());
                maze.getCell(edge.row2, edge.col2).setSurface(getRandomSurface());

                addEdges(maze, edges, edge.row2, edge.col2);
            }
        }

        setStartAndEnd(maze, start, end);
        return maze;
    }

    private void initializeMaze(Maze maze) {
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                maze.getCell(row, col).setType(Cell.Type.WALL);
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
            if (isInBounds(newRow, newCol, maze) && maze.getCell(newRow, newCol).type() == Cell.Type.WALL) {
                edges.add(new Edge(row, col, newRow, newCol));
            }
        }
    }

    private boolean isInBounds(int row, int col, Maze maze) {
        return row >= 0 && col >= 0 && row < maze.getHeight() && col < maze.getWidth();
    }

    private boolean isValidEdge(Maze maze, Edge edge) {
        return maze.getCell(edge.row2, edge.col2).type() == Cell.Type.WALL;
    }

    private void removeWall(Maze maze, Edge edge) {
        int wallRow = (edge.row1 + edge.row2) / 2;
        int wallCol = (edge.col1 + edge.col2) / 2;
        maze.getCell(wallRow, wallCol).setType(Cell.Type.PASSAGE);
    }

    private void setStartAndEnd(Maze maze, Coordinate start, Coordinate end) {
        maze.getCell(start.row(), start.col()).setType(Cell.Type.PASSAGE);
        maze.getCell(start.row(), start.col()).setSurface(Cell.Surface.START);

        maze.getCell(end.row(), end.col()).setType(Cell.Type.PASSAGE);
        maze.getCell(end.row(), end.col()).setSurface(Cell.Surface.END);
    }

    private Cell.Surface getRandomSurface() {
        int randomValue = random.nextInt(10);
        if (randomValue < 2) {
            return Cell.Surface.MUD;
        } else if (randomValue < 4) {
            return Cell.Surface.SAND;
        } else if (randomValue == 9) {
            return Cell.Surface.COIN;
        } else {
            return Cell.Surface.NORMAL;
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