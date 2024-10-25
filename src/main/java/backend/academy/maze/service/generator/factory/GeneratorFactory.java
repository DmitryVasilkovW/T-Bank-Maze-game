package backend.academy.maze.service.generator.factory;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.service.generator.Generator;
import java.util.List;

public interface GeneratorFactory {

    Generator createDFSGenerator(List<Coordinate> directions);

    Generator createPrimeGenerator(List<Coordinate> directions);
}
