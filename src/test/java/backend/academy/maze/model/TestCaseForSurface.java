package backend.academy.maze.model;

import backend.academy.maze.model.type.SurfaceType;

public record TestCaseForSurface(
        Coordinate start,
        Coordinate end,
        Coordinate dirtCoordinate,
        SurfaceType surface) {}
