import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    // To do:
    // -- How to know if won?
    // -- Need to check each time how many squares have been discovered
    // -- If all undiscovered squares are only bombs then the user wins
    // -- Make the grid prettier with clearer delineation between fried and axes
    // names
    // -- How to add colours or ASCII art?
    // -- (MAYBE) Reveal surrounding squares that are also empty (cascading effect)

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("Welcome to Minesweeper!");

            // Initialise grid so it's in scope
            Grid grid = null;

            while (grid == null) {
                System.out.println("Enter the number of grid rows you'd like to play: ");
                try {
                    int userInput = scanner.nextInt();

                    if (userInput >= 2 && userInput <= 15) {
                        grid = new Grid(userInput);
                    } else {
                        System.out.println("Invalid number. Please enter a number between 2 and 15.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.next();
                }
            }

            boolean gameRunning = true;

            while (gameRunning) {

                grid.displayGrid();
                System.out.println("Enter a co-ordinate to reveal a square (E.g: B4)\n");
                String chosenSquare = scanner.next();

                if (chosenSquare.length() >= 2) {
                    int row = Integer.parseInt(chosenSquare.substring(1)) - 1;
                    int col = Character.toUpperCase(chosenSquare.charAt(0)) - 'A';

                    if (GridUtils.isValidCoordinate(row, col, grid.getGridRows())) {
                        grid.revealItem(row, col);
                        GridItem item = grid.getGridItem(row, col);
                        if (item.getState() == GridItemState.BOMB) {
                            grid.displayGrid(); // Display final state
                            System.out.println("Oh no, you hit a bomb!\n");
                            gameRunning = false;
                        }
                    } else {
                        System.out.println("Invalid square selected. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }

            System.out.println("Would you like to play again? (y/n) \n");
            String playAgainResponse = scanner.next();

            if (playAgainResponse.equalsIgnoreCase("n")) {
                System.out.println("Thanks for playing, see you next time! \n");
                playAgain = false;
                scanner.close();
            } else if (!playAgainResponse.equalsIgnoreCase("y")) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
