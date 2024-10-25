package backend.academy.maze.model;

import backend.academy.maze.model.type.PassageType;
import backend.academy.maze.model.type.SurfaceType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {
    private final int row;
    private final int col;
    @Getter
    @Setter
    private PassageType type;
    @Getter
    @Setter
    private SurfaceType surface;

    public Cell(int row, int col, PassageType type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.surface = SurfaceType.NORMAL;
    }
}
