package backend.academy.maze.service.generator.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.random.suffer.RandomSufferGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFSGenerator implements Generator {
    private final RandomSufferGenerator sufferGenerator;
    private final List<Coordinate> directions;
    private Maze maze;
    private static final PassageType TYPE_OF_WALL = PassageType.WALL;
    private static final int DISTANCE_MULTIPLIER = 2;

    public DFSGenerator(RandomSufferGenerator sufferGenerator, List<Coordinate> directions) {
        this.sufferGenerator = sufferGenerator;
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
        current.surface(sufferGenerator.getRandomSurface());

        setSurfaces(row, col);
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
