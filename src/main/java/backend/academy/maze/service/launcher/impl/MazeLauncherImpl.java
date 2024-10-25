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
import backend.academy.maze.service.io.UserInputValidator;
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
    private final UserInputValidator userInputValidator;
    private Generator generator;
    private Solver solver;
    private static final String WARNING_FOR_YES_OR_NO_REQUEST = "Please enter 'yes/y' or 'no/n' to proceed.";
    private static final String MESSAGE_FOR_LIST_OF_DIRECTIONS = "Should we build a maze with narrow passageways?";
    private static final String MESSAGE_FOR_MATRIX_OF_DIRECTIONS = "Can the hero fit into narrow passageways?";
    private static final String MESSAGE_FOR_GETTING_GENERATOR =
            "Do we need to create more dead ends and long corridors?";
    private static final String MESSAGE_FOR_GETTING_SOLVER = "Does the hero need to pay attention to surfaces?";
    private static final String WARNING_FOR_GETTING_POINT = "Incorrect input. It must minimum 1 x 1";
    private static final String MESSAGE_FOR_GETTING_POINT_OF_START = "Enter point for start. Format: row column (1 1)";
    private static final String MESSAGE_FOR_GETTING_POINT_OF_END = "Enter point for end. Format: row column (13 13)";
    private static final String WARNING_FOR_GETTING_SIZE = "Incorrect input. It must be minimum 3 x 3.";
    private static final String MESSAGE_FOR_GETTING_SIZE =
            "Enter height and weight for maze. Format: height weight (15 15)";
    private static final String MESSAGE_FOR_SUCCESSFULL_SOLUTION = "Path found:";
    private static final String MESSAGE_FOR_UNSUCCESSFULL_SOLUTION = "No path found.";


    public MazeLauncherImpl(
            DirectionFactory directionFactory,
            MazeChainFactory mazeChainFactory,
            Printer printer,
            Reader reader,
            Render render,
            UserInputValidator userInputValidator) {
        this.directionFactory = directionFactory;
        this.mazeChainFactory = mazeChainFactory;
        this.printer = printer;
        this.reader = reader;
        this.render = render;
        this.userInputValidator = userInputValidator;
    }

    @Override
    public void launch() {
        List<Coordinate> directionsForGenerator = getDirectionsForGenerator();
        int[][] directionsForSolver = getDirectionsForSolver();

        generator = getGenerator(directionsForGenerator);

        Coordinate start = getCorrectPoint(MESSAGE_FOR_GETTING_POINT_OF_START);
        Coordinate end = getCorrectPoint(MESSAGE_FOR_GETTING_POINT_OF_END);
        Maze maze = createCorrectMaze(start, end);

        solver = getSolver(directionsForSolver, maze);

        showMaze(maze);
        showSolution(maze, start, end);
    }

    private void showMaze(Maze maze) {
        printer.println(render.render(maze));
    }

    private void showSolution(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> path = solver.solve(start, end);
        if (path.isEmpty()) {
            printer.println(MESSAGE_FOR_UNSUCCESSFULL_SOLUTION);
            return;
        }

        printer.println(MESSAGE_FOR_SUCCESSFULL_SOLUTION);
        printer.println(render.render(maze, path));
    }

    private List<Coordinate> getDirectionsForGenerator() {
        return getCorrectParameter(
                MESSAGE_FOR_LIST_OF_DIRECTIONS,
                () -> directionFactory.createDirectionAsList(true),
                () -> directionFactory.createDirectionAsList(false));
    }

    private int[][] getDirectionsForSolver() {
        return getCorrectParameter(
                MESSAGE_FOR_MATRIX_OF_DIRECTIONS,
                () -> directionFactory.createDirectionAsMatrix(true),
                () -> directionFactory.createDirectionAsMatrix(false)
        );
    }

    private Generator getGenerator(List<Coordinate> directionsForGenerator) {
        return getCorrectParameter(
                MESSAGE_FOR_GETTING_GENERATOR,
                () -> new DFSGenerator(mazeChainFactory.createSurfaceHandlerChain(), directionsForGenerator),
                PrimGenerator::new
        );
    }

    private Solver getSolver(int[][] directionsForSolver, Maze maze) {
        return getCorrectParameter(
                MESSAGE_FOR_GETTING_SOLVER,
                () -> new AStarSolver(directionsForSolver, mazeChainFactory.createCostHandlerChain(),
                        maze),
                () -> new BFSSolver(maze)
        );

    }

    private <T> T getCorrectParameter(String message, Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        printer.println(message);
        Optional<T> parameterO = tryToCreateByUserResponse(trueSupplier, falseSupplier);
        while (parameterO.isEmpty()) {
            printer.println(WARNING_FOR_YES_OR_NO_REQUEST);
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

    private Maze createCorrectMaze(Coordinate start, Coordinate end) {
        printer.println(MESSAGE_FOR_GETTING_SIZE);
        Optional<Maze> mazeO = tryToCreateMaze(start, end);
        while (mazeO.isEmpty()) {
            printer.println(WARNING_FOR_GETTING_SIZE);
            mazeO = tryToCreateMaze(start, end);
        }

        return mazeO.get();
    }

    private Optional<Maze> tryToCreateMaze(Coordinate start, Coordinate end) {
        String line = reader.readLineAsString();
        int height = Integer.parseInt(line.split(" ")[0]);
        int width = Integer.parseInt(line.split(" ")[1]);

        if (!userInputValidator.isMazeValid(start, end, height, width)) {
            return Optional.empty();
        }

        return Optional.of(generator.generate(height, width, start, end));
    }

    private Coordinate getCorrectPoint(String message) {
        printer.println(message);
        Optional<Coordinate> pointO = tryToGetPoint();
        while (pointO.isEmpty()) {
            printer.println(WARNING_FOR_GETTING_POINT);
            pointO = tryToGetPoint();
        }

        return pointO.get();
    }

    private Optional<Coordinate> tryToGetPoint() {
        String line = reader.readLineAsString();
        int row = Integer.parseInt(line.split(" ")[0]);
        int column = Integer.parseInt(line.split(" ")[1]);

        if (!userInputValidator.isPointValid(row, column)) {
            return Optional.empty();
        }

        return Optional.of(new Coordinate(row, column));
    }

}
