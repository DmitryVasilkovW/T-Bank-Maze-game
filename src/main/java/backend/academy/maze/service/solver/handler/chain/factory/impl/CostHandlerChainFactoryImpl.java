package backend.academy.maze.service.solver.handler.chain.factory.impl;

import backend.academy.maze.service.solver.handler.chain.CostHandlerChain;
import backend.academy.maze.service.solver.handler.chain.impl.CoinSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.chain.impl.CommonSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.chain.impl.DefaultSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.chain.impl.MudSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.chain.impl.SandSurfaceCostHandlerImpl;

public class CostHandlerChainFactoryImpl {

    public CostHandlerChain createCostHandlerChain() {
        return new CommonSurfaceCostHandlerImpl()
                .addNext(new CoinSurfaceCostHandlerImpl()
                        .addNext(new SandSurfaceCostHandlerImpl()
                                .addNext(new MudSurfaceCostHandlerImpl()
                                        .addNext(new DefaultSurfaceCostHandlerImpl()))));
    }
}
