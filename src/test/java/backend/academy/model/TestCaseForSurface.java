package backend.academy.model;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.type.SurfaceType;

public record TestCaseForSurface(
        Coordinate start,
        Coordinate end,
        Coordinate dirtCoordinate,
        SurfaceType surface) {}
