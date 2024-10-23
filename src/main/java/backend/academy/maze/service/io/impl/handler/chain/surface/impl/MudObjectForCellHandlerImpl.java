package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.type.SurfaceType;

public class MudObjectForCellHandlerImpl extends SurfaceObjectForCellHandlerChainImpl {
    private static final SurfaceType TYPE_OF_MUD = SurfaceType.MUD;
    private static final char CHARACTER_OF_MUD = 'M';

    @Override
    public ObjectForCell handle(CellRequest request) {
        SurfaceType type = request.cell().surface();
        if (type.equals(TYPE_OF_MUD)) {
            return new ObjectForCell(CHARACTER_OF_MUD);
        }
        return next.handle(request);
    }
}
