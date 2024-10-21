package backend.academy.maze.service.solver.handler.factory.impl;

import backend.academy.maze.service.solver.handler.CostHandlerChain;
import backend.academy.maze.service.solver.handler.impl.CoinSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.impl.CommonSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.impl.MudSurfaceCostHandlerImpl;
import backend.academy.maze.service.solver.handler.impl.SandSurfaceCostHandlerImpl;

public class CostHandlerChainFactoryImpl {

    public CostHandlerChain createCostHandlerChain() {
        return new CommonSurfaceCostHandlerImpl()
                .addNext(new CoinSurfaceCostHandlerImpl()
                        .addNext(new SandSurfaceCostHandlerImpl()
                                .addNext(new MudSurfaceCostHandlerImpl())));
    }
}
