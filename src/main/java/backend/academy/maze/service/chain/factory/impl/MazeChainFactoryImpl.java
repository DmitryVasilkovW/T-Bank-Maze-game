package backend.academy.maze.service.chain.factory.impl;

import backend.academy.maze.service.chain.factory.MazeChainFactory;
import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;
import backend.academy.maze.service.generator.handler.chain.factory.SurfaceTypeHandlerChainFactory;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.passage.factory.PassageHandlerChainFactory;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.factory.SurfaceHandlerChainFactory;
import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;
import backend.academy.maze.service.solver.handler.chain.factory.CostHandlerChainFactory;

public class MazeChainFactoryImpl implements MazeChainFactory {
    private final SurfaceTypeHandlerChainFactory surfaceTypeHandlerChainFactory;
    private final SurfaceHandlerChainFactory surfaceHandlerChainFactory;
    private final PassageHandlerChainFactory passageHandlerChainFactory;
    private final CostHandlerChainFactory costHandlerChainFactory;

    public MazeChainFactoryImpl(
            SurfaceTypeHandlerChainFactory surfaceTypeHandlerChainFactory,
            SurfaceHandlerChainFactory surfaceHandlerChainFactory,
            PassageHandlerChainFactory passageHandlerChainFactory, CostHandlerChainFactory costHandlerChainFactory) {
        this.surfaceTypeHandlerChainFactory = surfaceTypeHandlerChainFactory;
        this.surfaceHandlerChainFactory = surfaceHandlerChainFactory;
        this.passageHandlerChainFactory = passageHandlerChainFactory;
        this.costHandlerChainFactory = costHandlerChainFactory;
    }

    @Override
    public SurfaceHandlerChain createSurfaceHandlerChain() {
        return surfaceTypeHandlerChainFactory.create();
    }

    @Override
    public PassageObjectForCellHandlerChain createPassageObjectForCellHandlerChain() {
        return passageHandlerChainFactory.create();
    }

    @Override
    public SurfaceObjectForCellHandlerChain createSurfaceObjectForCellHandlerChain() {
        return surfaceHandlerChainFactory.create();
    }

    @Override
    public CostHandlerChain createCostHandlerChain() {
        return costHandlerChainFactory.create();
    }
}
