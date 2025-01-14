package backend.academy.maze.service.launcher.factory.impl;

import backend.academy.maze.service.generator.factory.impl.GeneratorFactoryImpl;
import backend.academy.maze.service.generator.handler.chain.factory.impl.SurfaceTypeHandlerChainFactoryImpl;
import backend.academy.maze.service.io.factory.impl.RenderFactoryImpl;
import backend.academy.maze.service.io.impl.PrintStreamPrinterImpl;
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

        var printer = new PrintStreamPrinterImpl(System.out);
        var reader = new ScannerReaderImpl(System.in);
        var render = new RenderFactoryImpl(passageForObjectChainFactory, surfaceForObjectChainFactory).create();
        var inputValidator = new UserInputValidatorImpl();
        var generationFactory = new GeneratorFactoryImpl(surfaceTypeChainFactory);

        return new MazeLauncherImpl(
                directionFactory,
                costChainFactory,
                generationFactory,
                printer,
                reader,
                render,
                inputValidator);
    }
}
