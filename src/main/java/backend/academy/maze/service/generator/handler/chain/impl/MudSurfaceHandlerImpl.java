package backend.academy.maze.service.generator.handler.chain.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.request.SurfaceRequest;
import backend.academy.maze.model.type.SurfaceType;

public class MudSurfaceHandlerImpl extends SurfaceHandlerChainImpl {
    private static final int UPPER_BORDER_FOR_MUD = 2;

    @Override
    public Surface handle(SurfaceRequest request) {
        if (request.randomNumber() < UPPER_BORDER_FOR_MUD) {
            return new Surface(SurfaceType.MUD);
        }
        return next.handle(request);
    }
}
