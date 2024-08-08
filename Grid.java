import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Grid {
    private int gridRows;
    private int numBombs;
    private GridItem[][] grid;

    public Grid(int gridRows) {
        this.gridRows = gridRows;
        this.numBombs = gridRows; // Same num bombs as there are grid rows E.g. 10x10 = 10 bombs
        this.grid = new GridItem[gridRows][gridRows];
        setupGrid();
    }

    private void setupGrid() {
        // Create a fresh grid
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridRows; j++) {
                grid[i][j] = new GridItem(GridItemState.BLANK, i, j);
            }
        }

        Random random = new Random();
        Set<String> bombLocations = new HashSet<String>();

        // Set bombs randomly
        for (int i = 0; i < numBombs; i++) {
            int row = random.nextInt(gridRows);
            int col = random.nextInt(gridRows);
            String location = row + "," + col;

            if (!bombLocations.contains(location)) {
                bombLocations.add(location);
                grid[row][col].setState(GridItemState.BOMB);
            }
        }
    }
}
