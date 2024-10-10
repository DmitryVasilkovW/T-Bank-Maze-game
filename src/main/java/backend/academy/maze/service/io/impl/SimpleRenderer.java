package backend.academy.maze.service.io.impl;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.service.io.Renderer;
import java.util.List;

public class SimpleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
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
            int index = coord.row() * (maze.getWidth() + 1) + coord.col();
            sb.setCharAt(index, '*'); // Путь отображаем символом '*'
        }
        return sb.toString();
    }

    private char getSymbolForCell(Cell cell) {
        switch (cell.getType()) {
            case WALL:
                return '#'; // Стена
            case PASSAGE:
                switch (cell.getSurface()) {
                    case MUD:
                        return 'M'; // Болото
                    case SAND:
                        return 'S'; // Песок
                    case COIN:
                        return 'C'; // Монета
                    default:
                        return ' '; // Обычный проход
                }
            default:
                return ' '; // На случай, если тип клетки не определен
        }
    }
}
