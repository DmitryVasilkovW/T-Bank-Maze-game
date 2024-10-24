package backend.academy.maze.service.launcher.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.chain.factory.MazeChainFactory;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import backend.academy.maze.service.generator.impl.PrimGenerator;
import backend.academy.maze.service.io.Printer;
import backend.academy.maze.service.io.Reader;
import backend.academy.maze.service.io.Render;
import backend.academy.maze.service.launcher.MazeLauncher;
import backend.academy.maze.service.launcher.direction.factory.DirectionFactory;
import backend.academy.maze.service.solver.Solver;
import backend.academy.maze.service.solver.impl.AStarSolver;
import backend.academy.maze.service.solver.impl.BFSSolver;
import java.util.List;
import java.util.Optional;

public class MazeLauncherImpl implements MazeLauncher {

    private final DirectionFactory directionFactory;
    private final MazeChainFactory mazeChainFactory;
    private final Printer printer;
    private final Reader reader;
    private final Render render;
    private Generator generator;

    public MazeLauncherImpl(
            DirectionFactory directionFactory,
            MazeChainFactory mazeChainFactory,
            Printer printer, Reader reader, Render render) {
        this.directionFactory = directionFactory;
        this.mazeChainFactory = mazeChainFactory;
        this.printer = printer;
        this.reader = reader;
        this.render = render;
    }

    @Override
    public void launch() {
        printer.println("should we build a maze with narrow passageways?");
        String line = reader.readLineAsString().toLowerCase();
        List<Coordinate> directionsForGenerator;
        if (line.equals("y") || line.equals("yes")) {
            directionsForGenerator = directionFactory.createDirectionAsList(true);
        } else {
            directionsForGenerator = directionFactory.createDirectionAsList(false);
        }

        printer.println("Can the hero fit into narrow passageways?");
        line = reader.readLineAsString().toLowerCase();
        int[][] directionsForSolver;
        if (line.equals("y") || line.equals("yes")) {
            directionsForSolver = directionFactory.createDirectionAsMatrix(true);
        } else {
            directionsForSolver = directionFactory.createDirectionAsMatrix(false);
        }

        printer.println("Do we need to create more dead ends and long corridors?");
        line = reader.readLineAsString().toLowerCase();
        if (line.equals("y") || line.equals("yes")) {
            generator = new DFSGenerator(mazeChainFactory.createSurfaceHandlerChain(), directionsForGenerator);
        } else {
            generator = new PrimGenerator();
        }

        printer.println("enter point for start. Format: row column (1 1)");
        Optional<Coordinate> startO = tryToGetPoint();
        if (startO.isEmpty()) {
            printer.println("incorrect input. It must minimum 1 x 1 and maximum height - 2 weight - 2");
            return;
        }

        printer.println("enter point for end. Format: row column (13 13)");
        Optional<Coordinate> endO = tryToGetPoint();
        if (endO.isEmpty()) {
            printer.println("incorrect input. It must minimum 1 x 1 and maximum height - 2 weight - 2");
            return;
        }

        printer.println("enter height and weight for maze. Format: height weight (15 15)");
        Optional<Maze> mazeO = tryToCreateMaze(startO.get(), endO.get());
        if (mazeO.isEmpty()) {
            printer.println("Incorrect input. It must be minimum 3 x 3.");
            return;
        }

        Solver solver;
        printer.println("Does the hero need to pay attention to surfaces?");
        line = reader.readLineAsString().toLowerCase();
        if (line.equals("y") || line.equals("yes")) {
            solver = new AStarSolver(directionsForSolver, mazeChainFactory.createCostHandlerChain(), mazeO.get());
        } else {
            solver = new BFSSolver(mazeO.get());
        }

        printer.println(render.render(mazeO.get()));
        List<Coordinate> path = solver.solve(startO.get(), endO.get());

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            System.out.println(render.render(mazeO.get(), path));
        }
        else {
            System.out.println("No path found.");
        }
    }

    private Optional<Maze> tryToCreateMaze(Coordinate start, Coordinate end) {
        String line = reader.readLineAsString();
        int height = Integer.parseInt(line.split(" ")[0]);
        int width = Integer.parseInt(line.split(" ")[1]);

        if ((height < 2 || width < 2) || !isValidCoordinate(start, height, width) || !isValidCoordinate(end, height, width)) {
            return Optional.empty();
        }

        return Optional.of(generator.generate(height, width, start, end));
    }

    private boolean isValidCoordinate(Coordinate coordinate, int height, int width) {
        return isValidPoint(coordinate.row(), width) && isValidPoint(coordinate.col(), height);
    }

    private boolean isValidPoint(int point, int size) {
        return point > 0 && point < size;
    }

    private Optional<Coordinate> tryToGetPoint() {
        String line = reader.readLineAsString();
        int row = Integer.parseInt(line.split(" ")[0]);
        int column = Integer.parseInt(line.split(" ")[1]);

        if ((column < 1 || row < 1)) {
            return Optional.empty();
        }

        return Optional.of(new Coordinate(row, column));
    }
}
