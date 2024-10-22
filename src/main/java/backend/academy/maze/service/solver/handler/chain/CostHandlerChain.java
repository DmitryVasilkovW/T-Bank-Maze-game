package backend.academy.maze.service.solver.handler.chain;

import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.CostRequest;

public interface CostHandlerChain {

    CostHandlerChain addNext(CostHandlerChain link);

    Cost handle(CostRequest request);
}
