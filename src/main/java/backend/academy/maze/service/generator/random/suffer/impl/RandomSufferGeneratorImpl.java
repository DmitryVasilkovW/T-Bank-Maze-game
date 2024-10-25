package backend.academy.maze.service.generator.random.suffer.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.request.SurfaceRequest;
import backend.academy.maze.model.type.SurfaceType;
import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;
import backend.academy.maze.service.generator.random.suffer.RandomSufferGenerator;
import java.util.Random;

public class RandomSufferGeneratorImpl implements RandomSufferGenerator {
    private final SurfaceHandlerChain surfaceHandlerChain;
    private final Random random;
    private final int upperBorderForRandomValue;

    public RandomSufferGeneratorImpl(SurfaceHandlerChain surfaceHandlerChain,
                                     Random random,
                                     int upperBorderForRandomValue) {
        this.surfaceHandlerChain = surfaceHandlerChain;
        this.random = random;
        this.upperBorderForRandomValue = upperBorderForRandomValue;
    }

    @Override
    public SurfaceType getRandomSurface() {
        int randomValue = random.nextInt(upperBorderForRandomValue);
        Surface surface = surfaceHandlerChain.handle(new SurfaceRequest(randomValue));
        return surface.surfaceType();
    }
}
