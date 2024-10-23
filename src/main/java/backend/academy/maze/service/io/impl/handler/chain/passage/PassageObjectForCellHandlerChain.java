package backend.academy.maze.service.io.impl.handler.chain.passage;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.PassageRequest;

public interface PassageObjectForCellHandlerChain {

    PassageObjectForCellHandlerChain addNext(PassageObjectForCellHandlerChain link);

    ObjectForCell handle(PassageRequest request);
}
