package backend.academy.maze.service.io.impl.handler.chain.surface.factory.impl;

import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.factory.SurfaceHandlerChainFactory;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.CoinObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.DefaultObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.EndObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.MudObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.SandObjectForCellHandlerImpl;
import backend.academy.maze.service.io.impl.handler.chain.surface.impl.StartObjectForCellHandlerImpl;

public class SurfaceHandlerChainFactoryImpl implements SurfaceHandlerChainFactory {

    @Override
    public SurfaceObjectForCellHandlerChain create() {
        return new CoinObjectForCellHandlerImpl()
                .addNext(new MudObjectForCellHandlerImpl()
                        .addNext(new SandObjectForCellHandlerImpl()
                                .addNext(new StartObjectForCellHandlerImpl()
                                        .addNext(new EndObjectForCellHandlerImpl()
                                                .addNext(new DefaultObjectForCellHandlerImpl())))));
    }
}
