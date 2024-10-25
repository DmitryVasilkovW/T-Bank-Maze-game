package backend.academy.maze.io;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.chain.ObjectForCell;
import backend.academy.maze.service.io.impl.RenderImpl;
import backend.academy.maze.service.io.impl.handler.chain.passage.PassageObjectForCellHandlerChain;
import backend.academy.maze.service.io.impl.handler.chain.surface.SurfaceObjectForCellHandlerChain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RenderImplTest {

    @Test
    @DisplayName("check drawing of a maze without a path")
    void testRenderMazeWithoutPath() {
        PassageObjectForCellHandlerChain passageChain = mock(PassageObjectForCellHandlerChain.class);
        SurfaceObjectForCellHandlerChain surfaceChain = mock(SurfaceObjectForCellHandlerChain.class);
        RenderImpl render = new RenderImpl(passageChain, surfaceChain);

        Maze maze = mock(Maze.class);
        Cell cell1 = mock(Cell.class);
        Cell cell2 = mock(Cell.class);

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
        PassageObjectForCellHandlerChain passageChain = mock(PassageObjectForCellHandlerChain.class);
        SurfaceObjectForCellHandlerChain surfaceChain = mock(SurfaceObjectForCellHandlerChain.class);

        RenderImpl render = new RenderImpl(passageChain, surfaceChain);

        Maze maze = mock(Maze.class);
        Cell cell1 = mock(Cell.class);
        Cell cell2 = mock(Cell.class);

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
