package backend.academy.maze.service.solver.handler.chain.impl;

import backend.academy.maze.model.chain.Cost;
import backend.academy.maze.model.chain.request.CostRequest;
import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;

public abstract class CostHandlerChainImpl implements CostHandlerChain {
    protected CostHandlerChain next;

    @Override
    public CostHandlerChain addNext(CostHandlerChain link) {
        if (next == null) {
            next = link;
        } else {
            next.addNext(link);
        }

        return this;
    }

    public abstract Cost handle(CostRequest request);
}
