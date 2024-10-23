package backend.academy.maze.service.io.impl.handler.chain.surface;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;

public interface SurfaceObjectForCellHandlerChain {

    SurfaceObjectForCellHandlerChain addNext(SurfaceObjectForCellHandlerChain link);

    ObjectForCell handle(CellRequest request);
}
