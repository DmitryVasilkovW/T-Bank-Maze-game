package backend.academy.maze.service.io.impl.handler.chain.passage.factory.impl;

import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.passage.factory.PassageHandlerChainFactory;
import backend.academy.maze.service.io.impl.handler.chain.passage.impl.CommonObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.impl.DefaultObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.impl.WallObjectForCellHandlerImpl;

public class PassageHandlerChainFactoryImpl implements PassageHandlerChainFactory {

    @Override
    public PassageObjectForCellHandlerChain create() {
        return new CommonObjectForCellHandlerImpl()
                .addNext(new WallObjectForCellHandlerImpl()
                        .addNext(new DefaultObjectForCellHandlerImpl()));
    }
}
