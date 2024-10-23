package backend.academy.maze.service.generator.handler.chain;

import backend.academy.maze.model.chain.Surface;
import backend.academy.maze.model.chain.request.SurfaceRequest;

public interface SurfaceHandlerChain {

    SurfaceHandlerChain addNext(SurfaceHandlerChain link);

    Surface handle(SurfaceRequest request);
}
