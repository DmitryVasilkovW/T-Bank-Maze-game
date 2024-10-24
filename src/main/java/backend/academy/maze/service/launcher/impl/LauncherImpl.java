package backend.academy.maze.service.launcher.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.chain.factory.MazeChainFactory;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.handler.chain.factory.impl.SurfaceTypeHandlerChainFactoryImpl;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import backend.academy.maze.service.io.Printer;
import backend.academy.maze.service.io.Reader;
import backend.academy.maze.service.io.Render;
import backend.academy.maze.service.launcher.Launcher;
import backend.academy.maze.service.launcher.direction.factory.DirectionFactory;
import backend.academy.maze.service.solver.Solver;
import backend.academy.maze.service.solver.impl.AStarSolver;
import java.util.List;

public class LauncherImpl implements Launcher {
    private final DirectionFactory directionFactory;
    private final MazeChainFactory mazeChainFactory;
    private final Printer printer;
    private final Reader reader;
    private final Render render;

    public LauncherImpl(
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
        var directionsForGen = directionFactory.createDirectionAsList(false);
        int[][] directions = directionFactory.createDirectionAsMatrix(false);

        Generator generator = new DFSGenerator(new SurfaceTypeHandlerChainFactoryImpl().create(), directionsForGen);

        printer.println("enter height and weight for maze. Format: height weight (15 15)");
        String line = reader.readLineAsString();
        int height = Integer.parseInt(line.split(" ")[0]);
        int width = Integer.parseInt(line.split(" ")[1]);

        if (height < 2 || width < 2) {
            printer.println("incorrect input. It must minimum 2 x 2");
            return;
        }

        printer.println("enter point for start. Format: row column (1 1)");
        line = reader.readLineAsString();
        int startRow = Integer.parseInt(line.split(" ")[0]);
        int startColumn = Integer.parseInt(line.split(" ")[1]);

        if ((startRow < 1 || startColumn < 1) || (startRow > height - 2 || startColumn > width - 2)) {
            printer.println("incorrect input. It must minimum 1 x 1 and maximum height - 2 weight - 2");
            return;
        }

        printer.println("enter point for end. Format: row column (13 13)");
        line = reader.readLineAsString();
        int endRow = Integer.parseInt(line.split(" ")[0]);
        int endColumn = Integer.parseInt(line.split(" ")[1]);

        if ((endColumn < 1 || endRow < 1) || (endColumn > height - 2 || endRow > width - 2)) {
            printer.println("incorrect input. It must minimum 1 x 1 and maximum height - 2 weight - 2");
            return;
        }

        var start = new Coordinate(startRow, startColumn);
        var end = new Coordinate(endRow, endColumn);
        Maze maze = generator.generate(height, width, start, end);

        Solver solver = new AStarSolver(directions, mazeChainFactory.createCostHandlerChain(), maze);

        printer.println(render.render(maze));

        List<Coordinate> path = solver.solve(start, end);

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            System.out.println(render.render(maze, path));
        }
        else {
            System.out.println("No path found.");
        }
    }
}
