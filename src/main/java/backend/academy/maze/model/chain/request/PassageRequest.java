package backend.academy.maze.model.chain.request;

import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;

public record PassageRequest(SurfaceObjectForCellHandlerChain chain, CellRequest request) {
}
