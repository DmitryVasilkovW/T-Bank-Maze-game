package backend.academy.maze.service.generator.handler.chain.factory.impl;

import backend.academy.maze.service.generator.handler.chain.SurfaceHandlerChain;
import backend.academy.maze.service.generator.handler.chain.factory.SurfaceTypeHandlerChainFactory;
import backend.academy.maze.service.generator.handler.chain.impl.CoinSurfaceHandlerImpl;
import backend.academy.maze.service.generator.handler.chain.impl.DefaultSurfaceHandlerImpl;
import backend.academy.maze.service.generator.handler.chain.impl.MudSurfaceHandlerImpl;
import backend.academy.maze.service.generator.handler.chain.impl.SandSurfaceHandlerImpl;

public class SurfaceTypeHandlerChainFactoryImpl implements SurfaceTypeHandlerChainFactory {

    @Override
    public SurfaceHandlerChain create() {
        return new MudSurfaceHandlerImpl()
                .addNext(new SandSurfaceHandlerImpl()
                        .addNext(new CoinSurfaceHandlerImpl()
                                .addNext(new DefaultSurfaceHandlerImpl())));
    }
}
