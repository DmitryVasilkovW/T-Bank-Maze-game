package backend.academy.maze.service.generator.handler.chain.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.request.SurfaceRequest;
import backend.academy.maze.model.type.SurfaceType;

public class SandSurfaceHandlerImpl extends SurfaceHandlerChainImpl {
    private static final int UPPER_BORDER_FOR_SAND = 4;

    @Override
    public Surface handle(SurfaceRequest request) {
        if (request.randonNumber() < UPPER_BORDER_FOR_SAND) {
            return new Surface(SurfaceType.SAND);
        }
        return next.handle(request);
    }
}
