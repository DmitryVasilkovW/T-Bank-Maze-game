package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.type.SurfaceType;

public class SandObjectForCellHandlerImpl extends SurfaceObjectForCellHandlerChainImpl {
    private static final SurfaceType TYPE_OF_SAND = SurfaceType.SAND;
    private static final char CHARACTER_OF_SAND = 'S';

    @Override
    public ObjectForCell handle(CellRequest request) {
        SurfaceType type = request.cell().surface();
        if (type.equals(TYPE_OF_SAND)) {
            return new ObjectForCell(CHARACTER_OF_SAND);
        }
        return next.handle(request);
    }
}
