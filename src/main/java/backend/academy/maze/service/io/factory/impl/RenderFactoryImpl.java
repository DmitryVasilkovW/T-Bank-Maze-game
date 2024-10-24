package backend.academy.maze.service.io.factory.impl;

import backend.academy.maze.service.io.Render;
import backend.academy.maze.service.io.factory.RenderFactory;
import backend.academy.maze.service.io.impl.RenderImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.passage.factory.PassageHandlerChainFactory;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.factory.SurfaceHandlerChainFactory;

public class RenderFactoryImpl implements RenderFactory {
    private final PassageHandlerChainFactory phaseHandlerChainFactory;
    private final SurfaceHandlerChainFactory surfaceHandlerChainFactory;

    public RenderFactoryImpl(
            PassageHandlerChainFactory phaseHandlerChainFactory, SurfaceHandlerChainFactory surfaceHandlerChainFactory) {
        this.phaseHandlerChainFactory = phaseHandlerChainFactory;
        this.surfaceHandlerChainFactory = surfaceHandlerChainFactory;
    }

    @Override
    public Render create() {
        PassageObjectForCellHandlerChain passageHandlerChain = phaseHandlerChainFactory.create();
        SurfaceObjectForCellHandlerChain surfaceHandlerChain = surfaceHandlerChainFactory.create();

        return new RenderImpl(passageHandlerChain, surfaceHandlerChain);
    }
}
