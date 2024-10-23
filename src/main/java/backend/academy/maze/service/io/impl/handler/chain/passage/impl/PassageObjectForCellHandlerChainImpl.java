package backend.academy.maze.service.io.impl.handler.chain.passage.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.PassageRequest;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;

public abstract class PassageObjectForCellHandlerChainImpl implements PassageObjectForCellHandlerChain {
    protected PassageObjectForCellHandlerChain next;

    @Override
    public PassageObjectForCellHandlerChain addNext(PassageObjectForCellHandlerChain link) {
        if (next == null) {
            next = link;
        } else {
            next.addNext(link);
        }

        return this;
    }

    public abstract ObjectForCell handle(PassageRequest request);
}
