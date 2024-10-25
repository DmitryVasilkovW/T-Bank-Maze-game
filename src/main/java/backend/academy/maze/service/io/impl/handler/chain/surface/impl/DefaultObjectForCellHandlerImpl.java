package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;

public class DefaultObjectForCellHandlerImpl extends SurfaceObjectForCellHandlerChainImpl {
    private static final char DEFAULT_CHARACTER = ' ';

    @Override
    public ObjectForCell handle(CellRequest request) {
        return new ObjectForCell(DEFAULT_CHARACTER);
    }
}
