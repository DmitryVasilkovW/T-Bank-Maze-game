package backend.academy.maze.service.generator.handler.chain.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.request.SurfaceRequest;
import backend.academy.maze.model.type.SurfaceType;

public class CoinSurfaceHandlerImpl extends SurfaceHandlerChainImpl {
    private static final int BORDER_FOR_COIN = 9;

    @Override
    public Surface handle(SurfaceRequest request) {
        if (request.randonNumber() == BORDER_FOR_COIN) {
            return new Surface(SurfaceType.COIN);
        }
        return next.handle(request);
    }
}
