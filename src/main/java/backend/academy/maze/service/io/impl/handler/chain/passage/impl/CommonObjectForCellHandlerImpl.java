package backend.academy.maze.service.io.impl.handler.chain.passage.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.chain.request.PassageRequest;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;

public class CommonObjectForCellHandlerImpl extends PassageObjectForCellHandlerChainImpl{
    private static final PassageType TYPE_OF_COMMON_PASSAGE = PassageType.PASSAGE;

    @Override
    public ObjectForCell handle(PassageRequest request) {
        PassageType type = request.request().cell().type();
        if (type.equals(TYPE_OF_COMMON_PASSAGE)) {
            SurfaceObjectForCellHandlerChain chain = request.chain();
            CellRequest cellRequest = request.request();

            return chain.handle(cellRequest);
        }
        return next.handle(request);
    }
}
