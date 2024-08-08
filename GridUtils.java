public class GridUtils {
    public static boolean isValidCoordinate(int row, int col, int gridSize) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }
}
