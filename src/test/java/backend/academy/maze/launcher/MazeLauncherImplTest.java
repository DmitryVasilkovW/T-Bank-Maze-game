package backend.academy.maze.launcher;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.service.generator.Generator;
import backend.academy.maze.service.generator.factory.GeneratorFactory;
import backend.academy.maze.service.io.Printer;
import backend.academy.maze.service.io.Reader;
import backend.academy.maze.service.io.Render;
import backend.academy.maze.service.io.UserInputValidator;
import backend.academy.maze.service.launcher.direction.factory.DirectionFactory;
import backend.academy.maze.service.launcher.impl.MazeLauncherImpl;
import backend.academy.maze.service.solver.handler.chain.factory.CostHandlerChainFactory;
import backend.academy.maze.service.solver.handler.chain.factory.impl.CostHandlerChainFactoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MazeLauncherImplTest {

    @Mock
    private DirectionFactory directionFactory;
    @Mock
    private CostHandlerChainFactory costHandlerChainFactory;
    @Mock
    private GeneratorFactory generatorFactory;
    @Mock
    private Printer printer;
    @Mock
    private Reader reader;
    @Mock
    private Render render;
    @Mock
    private UserInputValidator userInputValidator;
    @Mock
    private Generator generator;

    @InjectMocks
    private MazeLauncherImpl mazeLauncher;

    @Test
    @DisplayName("check for path acquisition in case of correct data")
    void testLaunchSuccess() {
        var maze = new Maze(5, 5);
        maze.getCell(1, 1).type(PassageType.PASSAGE);
        maze.getCell(1, 2).type(PassageType.PASSAGE);
        maze.getCell(2, 2).type(PassageType.PASSAGE);
        maze.getCell(3, 2).type(PassageType.PASSAGE);

        when(reader.readLineAsString())
                .thenReturn("yes", "y", "y", "1 1", "3 2", "5 5", "y");
        when(userInputValidator.isLineValid(anyString())).thenReturn(true);
        when(userInputValidator.isPointValid(anyInt(), anyInt())).thenReturn(true);
        when(userInputValidator.isMazeValid(any(Coordinate.class), any(Coordinate.class), anyInt(), anyInt())).thenReturn(true);

        when(directionFactory.createDirectionAsList(true)).thenReturn(List.of(new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
                new Coordinate(-1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, -1),
                new Coordinate(-1, 1),
                new Coordinate(-1, -1)));
        when(directionFactory.createDirectionAsMatrix(true)).thenReturn(new int[][]{{0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}});

        when(generatorFactory.createDFSGenerator(anyList())).thenReturn(generator);
        when(generator.generate(anyInt(), anyInt(), any(Coordinate.class), any(Coordinate.class)))
                .thenReturn(maze);

        when(costHandlerChainFactory.create()).thenReturn(new CostHandlerChainFactoryImpl().create());

        when(render.render(any(Maze.class))).thenReturn("Maze rendered");
        when(render.render(any(Maze.class), anyList())).thenReturn("Maze with path");

        mazeLauncher.launch();

        InOrder inOrder = inOrder(printer);
        inOrder.verify(printer).println("Maze rendered");
        inOrder.verify(printer).println("Maze with path");
    }

}
