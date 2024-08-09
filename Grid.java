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
        // Create a fresh grid
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridRows; j++) {
                grid[i][j] = new GridItem(GridItemState.BLANK, i, j);
            }
        }

        Random random = new Random();
        Set<String> bombLocations = new HashSet<String>();

        // Set bombs randomly
        while (bombLocations.size() < numBombs) {
            int row = random.nextInt(gridRows);
            int col = random.nextInt(gridRows);
            String location = row + "," + col;

            if (!bombLocations.contains(location)) {
                bombLocations.add(location);
                grid[row][col].setState(GridItemState.BOMB);
            }
        }
    }

    private void calculateAdjacentBombs() {
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridRows; col++) {

                // Skip bombs
                if (grid[row][col].getState() == GridItemState.BOMB) {
                    continue;
                }

                int count = 0;

                // Top-left
                if (row > 0 && col > 0 && grid[row - 1][col - 1].getState() == GridItemState.BOMB)
                    count++;
                // Top
                if (row > 0 && grid[row - 1][col].getState() == GridItemState.BOMB)
                    count++;
                // Top-right
                if (row > 0 && col < gridRows - 1 && grid[row - 1][col + 1].getState() == GridItemState.BOMB)
                    count++;
                // Left
                if (col > 0 && grid[row][col - 1].getState() == GridItemState.BOMB)
                    count++;
                // Right
                if (col < gridRows - 1 && grid[row][col + 1].getState() == GridItemState.BOMB)
                    count++;
                // Bottom-left
                if (row < gridRows - 1 && col > 0 && grid[row + 1][col - 1].getState() == GridItemState.BOMB)
                    count++;
                // Bottom
                if (row < gridRows - 1 && grid[row + 1][col].getState() == GridItemState.BOMB)
                    count++;
                // Bottom-right
                if (row < gridRows - 1 && col < gridRows - 1 && grid[row + 1][col + 1].getState() == GridItemState.BOMB)
                    count++;

                if (count > 0) {
                    grid[row][col].setNumAdjacentBombs(count);
                    grid[row][col].setState(GridItemState.NUM);
                }
            }
        }

    }

    public void displayGrid() {

        // Columns
        System.out.print(AnsiColours.RESET + "     ");
        for (int i = 0; i < gridRows; i++) {
            System.out.print(AnsiColours.YELLOW + (char) ('A' + i) + " " + AnsiColours.RESET);
        }
        System.out.println();

        // Border - top
        System.out.print("    +");
        for (int i = 0; i < gridRows; i++) {
            System.out.print(AnsiColours.LIGHT_GRAY + "-+" + AnsiColours.RESET);
        }
        System.out.println();

        // Print Rows
        for (int i = 0; i < gridRows; i++) {
            System.out.printf(AnsiColours.YELLOW + "%2d " + AnsiColours.RESET, i + 1);

            // Border - left
            System.out.print(AnsiColours.LIGHT_GRAY + "| " + AnsiColours.RESET);

            for (int j = 0; j < gridRows; j++) {
                if (grid[i][j].getRevealed()) {
                    if (grid[i][j].getState() == GridItemState.BOMB) {
                        System.out.print(AnsiColours.RED + "❊ " + AnsiColours.RESET);
                    } else if (grid[i][j].getState() == GridItemState.NUM) {
                        String numColour = switch (grid[i][j].getNumAdjacentBombs()) {
                            case 1 -> AnsiColours.BLUE;
                            case 2 -> AnsiColours.GREEN;
                            case 3 -> AnsiColours.RED;
                            case 4 -> AnsiColours.RED;
                            case 5 -> AnsiColours.RED;
                            default -> AnsiColours.CYAN;
                        };

                        System.out
                                .print(numColour + grid[i][j].getNumAdjacentBombs() + " " + AnsiColours.RESET);
                    } else {
                        System.out.print(AnsiColours.DARK_GRAY + "* " + AnsiColours.RESET);
                    }
                } else {
                    System.out.print(AnsiColours.WHITE + "■ " + AnsiColours.RESET);
                }
            }

            // Border - right
            System.out.print(AnsiColours.LIGHT_GRAY + "|" + AnsiColours.RESET);
            System.out.println();
        }

        // Border - bottom
        System.out.print("    +");
        for (int i = 0; i < gridRows; i++) {
            System.out.print(AnsiColours.LIGHT_GRAY + "-+" + AnsiColours.RESET);
        }
        System.out.println();

    }

    public void revealItem(int row, int col) {
        if (GridUtils.isValidCoordinate(row, col, gridRows)) {
            grid[row][col].setRevealed(true);
        }
    }

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
                if (grid[i][j].getRevealed() && grid[i][j].getState() != GridItemState.BOMB) {
                    numRevealedSquares++;
                }
            }
        }

        if (numRevealedSquares == totalNonBombSquares) {
            return true;
        } else
            return false;
    }
}
