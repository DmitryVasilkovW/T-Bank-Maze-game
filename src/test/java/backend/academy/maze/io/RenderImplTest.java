package backend.academy.maze.io;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.service.io.impl.RenderImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenderImplTest {
    @Mock
    private PassageObjectForCellHandlerChain passageChain;
    @Mock
    private Maze maze;
    @Mock
    private Cell cell1;
    @Mock
    private Cell cell2;

    @InjectMocks
    RenderImpl render;

    @Test
    @DisplayName("check drawing of a maze without a path")
    void testRenderMazeWithoutPath() {
        when(maze.height()).thenReturn(2);
        when(maze.width()).thenReturn(2);
        when(maze.getCell(0, 0)).thenReturn(cell1);
        when(maze.getCell(0, 1)).thenReturn(cell2);
        when(maze.getCell(1, 0)).thenReturn(cell1);
        when(maze.getCell(1, 1)).thenReturn(cell2);

        when(passageChain.handle(any())).thenReturn(new ObjectForCell('#'));

        String expectedOutput = "##\n##\n";

        String result = render.render(maze);

        assertEquals(expectedOutput, result);
    }

    @Test
    @DisplayName("check drawing of a maze with a path")
    void testRenderMazeWithPath() {
        when(maze.height()).thenReturn(2);
        when(maze.width()).thenReturn(2);
        when(maze.getCell(0, 0)).thenReturn(cell1);
        when(maze.getCell(0, 1)).thenReturn(cell2);
        when(maze.getCell(1, 0)).thenReturn(cell1);
        when(maze.getCell(1, 1)).thenReturn(cell2);

        when(passageChain.handle(any())).thenReturn(new ObjectForCell('*'));
        var path = new ArrayList<Coordinate>();
        path.add(new Coordinate(0, 0));
        path.add(new Coordinate(0, 1));


        String resultWithPath = render.render(maze, path);

        String expectedOutputWithPath = "**\n**\n";

        assertEquals(expectedOutputWithPath, resultWithPath);
    }
}
