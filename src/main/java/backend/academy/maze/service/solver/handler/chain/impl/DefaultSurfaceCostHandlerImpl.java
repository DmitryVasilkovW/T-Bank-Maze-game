package backend.academy.maze.service.solver.handler.chain.impl;

import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.CostRequest;

public class DefaultSurfaceCostHandlerImpl extends CostHandlerChainImpl {
    private static final double DEFAULT_SURFACE_COST = 1;

    @Override
    public Cost handle(CostRequest request) {
        return new Cost(DEFAULT_SURFACE_COST);
    }
}
