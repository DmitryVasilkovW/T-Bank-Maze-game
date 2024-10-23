package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.type.SurfaceType;

public class StartObjectForCellHandlerImpl extends SurfaceObjectForCellHandlerChainImpl {
    private static final SurfaceType TYPE_OF_START = SurfaceType.START;
    private static final char CHARACTER_OF_START = 'A';

    @Override
    public ObjectForCell handle(CellRequest request) {
        SurfaceType type = request.cell().surface();
        if (type.equals(TYPE_OF_START)) {
            return new ObjectForCell(CHARACTER_OF_START);
        }
        return next.handle(request);
    }
}