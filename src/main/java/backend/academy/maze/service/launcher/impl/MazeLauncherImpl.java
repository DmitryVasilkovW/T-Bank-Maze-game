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
import java.util.function.Supplier;

public class MazeLauncherImpl implements MazeLauncher {
    private final DirectionFactory directionFactory;
    private final MazeChainFactory mazeChainFactory;
    private final Printer printer;
    private final Reader reader;
    private final Render render;
    private Generator generator;
    private static final String WARNING_FOR_YES_OR_NO_REQUEST = "Please enter 'yes/y' or 'no/n' to proceed.";
    private static final String MESSAGE_FOR_LIST_OF_DIRECTIONS = "Should we build a maze with narrow passageways?";
    private static final String MESSAGE_FOR_MATRIX_OF_DIRECTIONS = "Can the hero fit into narrow passageways?";
    private static final String MESSAGE_FOR_GETTING_GENERATOR = "Do we need to create more dead ends and long corridors?";
    private static final String MESSAGE_FOR_GETTING_SOLVER = "Does the hero need to pay attention to surfaces?";


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
        List<Coordinate> directionsForGenerator = getCorrectParameter(
                MESSAGE_FOR_LIST_OF_DIRECTIONS,
                WARNING_FOR_YES_OR_NO_REQUEST,
                () -> directionFactory.createDirectionAsList(true),
                () -> directionFactory.createDirectionAsList(false));

        int[][] directionsForSolver = getCorrectParameter(
                MESSAGE_FOR_MATRIX_OF_DIRECTIONS,
                WARNING_FOR_YES_OR_NO_REQUEST,
                () -> directionFactory.createDirectionAsMatrix(true),
                () -> directionFactory.createDirectionAsMatrix(false)
        );

        generator = getCorrectParameter(
                MESSAGE_FOR_GETTING_GENERATOR,
                WARNING_FOR_YES_OR_NO_REQUEST,
                () -> new DFSGenerator(mazeChainFactory.createSurfaceHandlerChain(), directionsForGenerator),
                PrimGenerator::new
        );

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

        Solver solver = getCorrectParameter(
                MESSAGE_FOR_GETTING_SOLVER,
                WARNING_FOR_YES_OR_NO_REQUEST,
                () -> new AStarSolver(directionsForSolver, mazeChainFactory.createCostHandlerChain(),
                        mazeO.get()),
                () -> new BFSSolver(mazeO.get())
        );

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

    private <T> T getCorrectParameter(String message, String warning, Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        printer.println(message);
        Optional<T> parameterO = tryToCreateByUserResponse(trueSupplier, falseSupplier);
        while (parameterO.isEmpty()) {
            printer.println(warning);
            parameterO = tryToCreateByUserResponse(falseSupplier, trueSupplier);
        }

        return parameterO.get();
    }

    private <T> Optional<T> tryToCreateByUserResponse(Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        String line = reader.readLineAsString().toLowerCase();
        if (line.equals("y") || line.equals("yes")) {
            return Optional.ofNullable(trueSupplier.get());
        } else if (line.equals("n") || line.equals("no")) {
            return Optional.ofNullable(falseSupplier.get());
        }

        return Optional.empty();
    }

    private Optional<Maze> tryToCreateMaze(Coordinate start, Coordinate end) {
        String line = reader.readLineAsString();
        int height = Integer.parseInt(line.split(" ")[0]);
        int width = Integer.parseInt(line.split(" ")[1]);

        if ((height < 2 || width < 2) || !isValidCoordinate(start, height, width) ||
                !isValidCoordinate(end, height, width)) {
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
