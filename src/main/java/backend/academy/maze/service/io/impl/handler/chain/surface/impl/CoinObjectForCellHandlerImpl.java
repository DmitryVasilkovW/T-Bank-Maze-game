package backend.academy.maze.service.io.impl.handler.chain.surface.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.type.SurfaceType;

public class CoinObjectForCellHandlerImpl extends SurfaceObjectForCellHandlerChainImpl {
    private static final SurfaceType TYPE_OF_COIN = SurfaceType.COIN;
    private static final char CHARACTER_OF_COIN = 'C';

    @Override
    public ObjectForCell handle(CellRequest request) {
        SurfaceType type = request.cell().surface();
        if (type.equals(TYPE_OF_COIN)) {
            return new ObjectForCell(CHARACTER_OF_COIN);
        }
        return next.handle(request);
    }
}
