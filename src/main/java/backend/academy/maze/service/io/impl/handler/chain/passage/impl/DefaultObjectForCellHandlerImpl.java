package backend.academy.maze.service.io.impl.handler.chain.passage.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.chain.request.PassageRequest;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;

public class DefaultObjectForCellHandlerImpl extends PassageObjectForCellHandlerChainImpl {

    @Override
    public ObjectForCell handle(PassageRequest request) {
        SurfaceObjectForCellHandlerChain chain = request.chain();
        CellRequest cellRequest = request.request();
        return chain.handle(cellRequest);
    }
}
