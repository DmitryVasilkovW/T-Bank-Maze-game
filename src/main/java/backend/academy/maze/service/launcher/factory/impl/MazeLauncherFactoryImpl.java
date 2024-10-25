package backend.academy.maze.service.launcher.factory.impl;

import backend.academy.maze.service.chain.factory.impl.MazeChainFactoryImpl;
import backend.academy.maze.service.generator.handler.chain.factory.impl.SurfaceTypeHandlerChainFactoryImpl;
import backend.academy.maze.service.io.impl.PrintStreamPrinterImpl;
import backend.academy.maze.service.io.impl.RenderImpl;
import backend.academy.maze.service.io.impl.ScannerReaderImpl;
import backend.academy.maze.service.io.impl.UserInputValidatorImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.factory.impl.PassageHandlerChainFactoryImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.factory.impl.SurfaceHandlerChainFactoryImpl;
import backend.academy.maze.service.launcher.MazeLauncher;
import backend.academy.maze.service.launcher.direction.factory.impl.DirectionFactoryImpl;
import backend.academy.maze.service.launcher.factory.MazeLauncherFactory;
import backend.academy.maze.service.launcher.impl.MazeLauncherImpl;
import backend.academy.maze.service.solver.handler.chain.factory.impl.CostHandlerChainFactoryImpl;

public class MazeLauncherFactoryImpl implements MazeLauncherFactory {

    @Override
    public MazeLauncher create() {
        var directionFactory = new DirectionFactoryImpl();

        var surfaceTypeChainFactory = new SurfaceTypeHandlerChainFactoryImpl();
        var passageForObjectChainFactory = new PassageHandlerChainFactoryImpl();
        var surfaceForObjectChainFactory = new SurfaceHandlerChainFactoryImpl();
        var costChainFactory = new CostHandlerChainFactoryImpl();

        var mazeChainFactory = new MazeChainFactoryImpl(
                surfaceTypeChainFactory,
                surfaceForObjectChainFactory,
                passageForObjectChainFactory,
                costChainFactory);

        var printer = new PrintStreamPrinterImpl(System.out);
        var reader = new ScannerReaderImpl(System.in);
        var render = new RenderImpl(
                mazeChainFactory.createPassageObjectForCellHandlerChain(),
                mazeChainFactory.createSurfaceObjectForCellHandlerChain()
        );
        var inputValidator = new UserInputValidatorImpl();

        return new MazeLauncherImpl(
                directionFactory,
                mazeChainFactory,
                printer,
                reader,
                render,
                inputValidator);
    }
}