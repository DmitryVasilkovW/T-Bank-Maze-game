package backend.academy.maze.service.generator.handler.chain.impl;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.SurfaceRequest;
import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;

public abstract class SurfaceHandlerChainImpl implements SurfaceHandlerChain {
    protected SurfaceHandlerChain next;

    @Override
    public SurfaceHandlerChain addNext(SurfaceHandlerChain link) {
        if (next == null) {
            next = link;
        } else {
            next.addNext(link);
        }

        return this;
    }

    public abstract Surface handle(SurfaceRequest request);
}
