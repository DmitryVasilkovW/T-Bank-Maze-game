package backend.academy.maze.service.solver.handler.factory;

import backend.academy.maze.service.solver.handler.CostHandlerChain;

public interface CostHandlerChainFactory {
    CostHandlerChain createCostHandlerChain();
}
