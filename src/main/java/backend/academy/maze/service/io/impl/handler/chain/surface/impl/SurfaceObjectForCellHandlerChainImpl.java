package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;

public abstract class SurfaceObjectForCellHandlerChainImpl implements SurfaceObjectForCellHandlerChain {
    protected SurfaceObjectForCellHandlerChain next;

    @Override
    public SurfaceObjectForCellHandlerChain addNext(SurfaceObjectForCellHandlerChain link) {
        if (next == null) {
            next = link;
        }
        else {
            next.addNext(link);
        }

        return this;
    }

    public abstract ObjectForCell handle(CellRequest request);
}
