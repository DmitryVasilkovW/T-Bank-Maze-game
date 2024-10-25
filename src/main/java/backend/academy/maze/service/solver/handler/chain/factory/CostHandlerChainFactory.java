package backend.academy.maze.service.solver.handler.chain.factory;

import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;

public interface CostHandlerChainFactory {
    CostHandlerChain create();
}
