package backend.academy.maze.service.chain.factory;

import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;

public interface MazeChainFactory {
    SurfaceHandlerChain createSurfaceHandlerChain();
    PassageObjectForCellHandlerChain createPassageObjectForCellHandlerChain();
    SurfaceObjectForCellHandlerChain createSurfaceObjectForCellHandlerChain();
    CostHandlerChain createCostHandlerChain();
}
