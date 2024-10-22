package backend.academy.maze.service.generator.handler.chain.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.SurfaceRequest;
import backend.academy.maze.model.type.SurfaceType;

public class DefaultSurfaceHandlerImpl extends SurfaceHandlerChainImpl {

    @Override
    public Surface handle(SurfaceRequest request) {
        return new Surface(SurfaceType.NORMAL);
    }
}
