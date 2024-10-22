package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.SurfaceRequest;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DFSGenerator implements Generator {
    private final Random random = new Random();
    private final SurfaceHandlerChain surfaceHandlerChain;
    private final List<Coordinate> directions;
    private Maze maze;
    private static final int UPPER_BORDER_FOR_RANDOM_VALUE = 10;
    private static final PassageType TYPE_OF_WALL = PassageType.WALL;
    private static final int DISTANCE_MULTIPLIER = 2;

    public DFSGenerator(SurfaceHandlerChain surfaceHandlerChain, List<Coordinate> directions) {
        this.surfaceHandlerChain = surfaceHandlerChain;
        this.directions = directions;
    }

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        maze = new Maze(height, width);
        generateMaze(start.row(), start.col());
        setStartAndFinishPositions(start, end);

        return maze;
    }

    private void setStartAndFinishPositions(Coordinate start, Coordinate finish) {
        Cell startCell = maze.getCell(start.row(), start.col());
        startCell.type(PassageType.PASSAGE);
        startCell.surface(SurfaceType.START);

        Cell endCell = maze.getCell(finish.row(), finish.col());
        endCell.type(PassageType.PASSAGE);
        endCell.surface(SurfaceType.END);
    }

    private void generateMaze(int row, int col) {
        Cell current = maze.getCell(row, col);
        current.type(PassageType.PASSAGE);
        current.surface(getRandomSurface());

        setSurfaces(row, col);
    }

    private SurfaceType getRandomSurface() {
        int randomValue = random.nextInt(UPPER_BORDER_FOR_RANDOM_VALUE);
        Surface surface = surfaceHandlerChain.handle(new SurfaceRequest(randomValue));
        return surface.surfaceType();
    }

    private void setSurfaces(int row, int col) {
        List<Coordinate> shuffledDirections = getShuffledDirections();
        for (Coordinate dir : shuffledDirections) {
            int newRow = getNewPosition(row, dir.row());
            int newCol = getNewPosition(col, dir.col());
            if (isValidMove(newRow, newCol)) {
                maze.getCell(row + dir.row(), col + dir.col()).type(PassageType.PASSAGE);
                generateMaze(newRow, newCol);
            }
        }
    }

    private List<Coordinate> getShuffledDirections() {
        var shuffled = new ArrayList<>(directions);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    private int getNewPosition(int from, int to) {
        return from + to * DISTANCE_MULTIPLIER;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && col >= 0 && row < maze.height() && col < maze.width()
                && maze.getCell(row, col).type() == TYPE_OF_WALL;
    }
}
