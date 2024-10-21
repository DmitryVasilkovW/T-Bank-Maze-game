package backend.academy.maze.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {
    private final int row;
    private final int col;
    private Type type;
    private Surface surface;  // Тип поверхности в клетке

    public enum Type {
        WALL, PASSAGE
    }

    // Новый enum для типов поверхностей
    public enum Surface {
        NORMAL,  // Обычная поверхность
        MUD,     // Болото (ухудшает движение)
        SAND,    // Песок (замедляет движение)
        COIN,
        START,
        END,// Монеты (бонусная поверхность)

    }

    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.surface = Surface.NORMAL;  // По умолчанию нормальная поверхность
    }

    // Геттеры и сеттеры для типа и поверхности
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
