package backend.academy.maze.model;

public record TestCaseForSurface(
        Coordinate start,
        Coordinate end,
        Coordinate dirtCoordinate,
        Cell.Surface surface) {}
