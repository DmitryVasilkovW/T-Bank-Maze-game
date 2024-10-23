package backend.academy.maze.service.io.impl.handler.chain.passage.impl;

import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.PassageRequest;
import backend.academy.maze.model.type.PassageType;

public class WallObjectForCellHandlerImpl extends PassageObjectForCellHandlerChainImpl{
    private static final PassageType TYPE_OF_WALL = PassageType.WALL;
    private static final char CHARACTER_OF_WALL = '#';

    @Override
    public ObjectForCell handle(PassageRequest request) {
        PassageType type = request.request().cell().type();
        if (type.equals(TYPE_OF_WALL)) {
            return new ObjectForCell(CHARACTER_OF_WALL);
        }
        return next.handle(request);
    }
}
