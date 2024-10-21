package backend.academy.maze.service.solver.handler.impl;

import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.CostRequest;
import backend.academy.maze.model.type.SurfaceType;

public class CoinSurfaceCostHandlerImpl extends CostHandlerChainImpl {
    private static final double COST_FOR_COIN_SURFACE = -2;
    private static final SurfaceType EXPECTED_SURFACE_TYPE = SurfaceType.COIN;

    @Override
    public Cost handle(CostRequest request) {
        if (request.surfaceType().equals(EXPECTED_SURFACE_TYPE)) {
            return new Cost(COST_FOR_COIN_SURFACE);
        }
        return next.handle(request);
    }
}
