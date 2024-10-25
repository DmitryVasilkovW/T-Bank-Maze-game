package backend.academy.maze.service.generator.factory.impl;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.factory.GeneratorFactory;
import backend.academy.maze.service.generator.handler.chain.factory.SurfaceTypeHandlerChainFactory;
import backend.academy.maze.service.generator.impl.DFSGenerator;
import backend.academy.maze.service.generator.impl.PrimGenerator;
import backend.academy.maze.service.generator.service.impl.RandomSufferGeneratorImpl;
import java.util.List;
import java.util.Random;

public class GeneratorFactoryImpl implements GeneratorFactory {
    private final SurfaceTypeHandlerChainFactory surfaceHandlerChainFactory;
    private final Random random = new Random();
    private final static int UPPER_VALUE_FOR_RANDOM = 10;

    public GeneratorFactoryImpl(SurfaceTypeHandlerChainFactory surfaceHandlerChainFactory) {
        this.surfaceHandlerChainFactory = surfaceHandlerChainFactory;
    }

    @Override
    public Generator createDFSGenerator(List<Coordinate> directions) {
        return new DFSGenerator(new RandomSufferGeneratorImpl(
                surfaceHandlerChainFactory.create(),
                random,
                UPPER_VALUE_FOR_RANDOM
        ), directions);
    }

    @Override
    public Generator createPrimeGenerator(List<Coordinate> directions) {
        return new PrimGenerator(new RandomSufferGeneratorImpl(
                surfaceHandlerChainFactory.create(),
                random,
                UPPER_VALUE_FOR_RANDOM
        ), directions);
    }
}
