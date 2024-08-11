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
        calculateAdjacentBombs();
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public GridItem[][] getGrid() {
        return grid;
    }

    private void setupGrid() {
        generateNewGrid();
        placeRandomBombs();
    }

    private void generateNewGrid() {
        // Create a fresh grid
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridRows; j++) {
                // Grid items start as blank
                grid[i][j] = new GridItem(GridItemState.BLANK, i, j);
            }
        }
    }

    private void placeRandomBombs() {
        Random random = new Random();
        // Hashset to prevent duplicate bomb locations
        Set<String> bombLocations = new HashSet<String>();

        // Continue placing bombs until we have the correct amount
        while (bombLocations.size() < numBombs) {

            // Get a random row and col from a max number based on the gridRows
            int row = random.nextInt(gridRows);
            int col = random.nextInt(gridRows);

            // String for distinct bomb location
            String location = row + "," + col;

            // If at the locaiton there's not already a bomb, add that bomb to the hashset
            if (!bombLocations.contains(location)) {
                bombLocations.add(location);
                // Set that gridItem state to be BOMB
                grid[row][col].setState(GridItemState.BOMB);
            }
        }
    }

    private void calculateAdjacentBombs() {
        int[][] directions = {
                // {x, y} values
                { -1, -1 }, // top-left (removing 1 row and 1 col from the current position)
                { -1, 0 }, // top
                { -1, 1 }, // top-right
                { 0, -1 }, // left
                { 0, 1 }, // right
                { 1, -1 }, // bottom-left square
                { 1, 0 }, // bottom
                { 1, 1 }, // bottom-right (adding +1 row and +1 col to current position)
        };

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridRows; col++) {

                // Skip if bomb
                if (grid[row][col].getState() == GridItemState.BOMB) {
                    continue;
                }

                // Get the count of all adjacent bombs relative to current location
                int count = countAdjacentBombs(row, col, directions);

                if (count > 0) {
                    grid[row][col].setNumAdjacentBombs(count);
                    grid[row][col].setState(GridItemState.NUM);
                }
            }
        }
    }

    private int countAdjacentBombs(int row, int col, int[][] directions) {
        // Setup counter
        int count = 0;

        // For each direction get a new row x col coordinate based on current location
        for (int[] direction : directions) {
            int newRowCoord = row + direction[0]; // First index is x coord (row)
            int newColCoord = col + direction[1]; // Second index is y coord (col)

            // If is valid coordinate for this grid & it's a bomb, then increase the counter
            if (GridUtils.isValidCoordinate(newRowCoord, newColCoord, gridRows)
                    && grid[newRowCoord][newColCoord].getState() == GridItemState.BOMB) {
                count++;
            }
        }

        return count;
    }

    // Print the grid out
    public void displayGrid() {
        GridUtils.printColumnHeaders(gridRows);
        GridUtils.printTopBottomBorder(gridRows);

        // Print Rows
        for (int i = 0; i < gridRows; i++) {
            GridUtils.printRow(grid[i], i);
        }

        GridUtils.printTopBottomBorder(gridRows);

    }

    public void revealItem(int row, int col) {
        // Check if item is valid, if it is then 'reveal' it
        if (GridUtils.isValidCoordinate(row, col, gridRows)) {
            grid[row][col].setRevealed(true);
        }
    }

    // Return gridItem if valid coordinates
    public GridItem getGridItem(int row, int col) {
        if (GridUtils.isValidCoordinate(row, col, gridRows)) {
            return grid[row][col];
        }
        return null;
    }

    public boolean checkIfWon() {
        int numRevealedSquares = 0;
        int totalNonBombSquares = gridRows * gridRows - numBombs;

        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridRows; j++) {
                // If gridItem has been revealed and isn't a bomb, increase the count of the
                // number of revealed squares
                if (grid[i][j].getRevealed() && grid[i][j].getState() != GridItemState.BOMB) {
                    numRevealedSquares++;
                }
            }
        }

        // If the number of revealed squares is the same as how many non bomb squares
        // we're expecting, return true, otherwise return false
        return numRevealedSquares == totalNonBombSquares;
    }
}
