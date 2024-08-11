public class GridUtils {

    // ANSI Colours
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String BOLD = "\033[1m";
    public static final String LIGHT_GRAY = "\033[37m";
    public static final String DARK_GRAY = "\033[90m";

    public static boolean isValidCoordinate(int row, int col, int gridRows) {
        // Row and Col value should be > 0 + less than gridRows to be in valid range
        return row >= 0 && row < gridRows && col >= 0 && col < gridRows;
    }

    public static void printColumnHeaders(int gridRows) {
        System.out.print(RESET + "     ");
        for (int i = 0; i < gridRows; i++) {
            System.out.print(YELLOW + (char) ('A' + i) + " " + RESET);
        }
        System.out.println();
    }

    public static void printTopBottomBorder(int gridRows) {
        System.out.print("    +");
        for (int i = 0; i < gridRows; i++) {
            System.out.print(LIGHT_GRAY + "-+" + RESET);
        }
        System.out.println();
    }

    public static void printRow(GridItem[] gridRowItems, int rowIndex) {
        // Print the row number on left
        System.out.printf(YELLOW + "%2d " + RESET, rowIndex + 1);

        // Border - left
        System.out.print(LIGHT_GRAY + "| " + RESET);

        // Print each row item
        for (GridItem item : gridRowItems) {
            printGridItem(item);
        }

        // Border - right
        System.out.print(LIGHT_GRAY + "|" + RESET);
        System.out.println();
    }

    public static void printGridItem(GridItem item) {
        if (item.getRevealed()) {

            // Print special star for a bomb
            if (item.getState() == GridItemState.BOMB) {
                System.out.print(RED + "❊ " + RESET);

                // Print number if numbered item
            } else if (item.getState() == GridItemState.NUM) {
                printNumberedItem(item);

                // Print asterisk if blank (0) square
            } else {
                System.out.print(DARK_GRAY + "* " + RESET);
            }

            // Un-revealed squares
        } else {
            System.out.print(WHITE + "■ " + RESET);
        }
    }

    // Change colour for different numbers
    public static void printNumberedItem(GridItem item) {
        String numColour = switch (item.getNumAdjacentBombs()) {
            case 1 -> BLUE;
            case 2 -> GREEN;
            case 3 -> RED;
            case 4 -> RED;
            case 5 -> RED;
            default -> CYAN;
        };

        System.out
                .print(numColour + item.getNumAdjacentBombs() + " " + RESET);
    }
}
