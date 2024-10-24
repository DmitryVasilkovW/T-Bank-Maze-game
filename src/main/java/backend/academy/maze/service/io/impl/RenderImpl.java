package backend.academy.maze.service.io.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.model.chain.request.CellRequest;
import backend.academy.maze.model.chain.request.PassageRequest;
import backend.academy.maze.service.io.Render;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import java.util.List;

public class RenderImpl implements Render {
    private final PassageObjectForCellHandlerChain passageObjectForCellHandlerChain;
    private final SurfaceObjectForCellHandlerChain surfaceObjectForCellHandlerChain;
    private final static char CHARACTER_FOR_PATH = '*';

    public RenderImpl(PassageObjectForCellHandlerChain passageObjectForCellHandlerChain,
                      SurfaceObjectForCellHandlerChain surfaceObjectForCellHandlerChain) {
        this.passageObjectForCellHandlerChain = passageObjectForCellHandlerChain;
        this.surfaceObjectForCellHandlerChain = surfaceObjectForCellHandlerChain;
    }

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                sb.append(getSymbolForCell(cell));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder(render(maze));
        for (Coordinate coord : path) {
            int index = coord.row() * (maze.width() + 1) + coord.col();
            sb.setCharAt(index, CHARACTER_FOR_PATH);
        }

        return sb.toString();
    }

    private char getSymbolForCell(Cell cell) {
        PassageRequest request = new PassageRequest(surfaceObjectForCellHandlerChain, new CellRequest(cell));
        ObjectForCell object = passageObjectForCellHandlerChain.handle(request);
        return object.object();
    }
}
