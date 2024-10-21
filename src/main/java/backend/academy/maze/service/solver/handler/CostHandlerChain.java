package backend.academy.maze.service.solver.handler;

import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.CostRequest;

public interface CostHandlerChain {
    CostHandlerChain addNext(CostHandlerChain link);
    Cost handle(CostRequest request);
}
