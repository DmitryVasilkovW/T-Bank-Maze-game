package backend.academy.maze.service.generator;

import backend.academy.maze.model.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
