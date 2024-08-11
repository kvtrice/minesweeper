import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);

    public void startGame() {
        // Loop so user can easily play again without having to restart
        boolean playAgain = true;
        while (playAgain) {
            System.out.println("Welcome to Minesweeper!");

            Grid grid = initialiseGrid();
            playGame(grid);

            playAgain = askPlayAgain();
        }
    }

    private Grid initialiseGrid() {
        // Initialise the grid so it's in scope for gameRunning loop
        Grid grid = null;

        // Loop so if the user enteres an invalid number they can try again with a new
        // number
        while (grid == null) {
            System.out.println("Enter the number of grid rows you'd like to play: ");

            // Try to ge the number of grid rows from user
            try {
                int userInput = scanner.nextInt();

                if (userInput >= 2 && userInput <= 15) {
                    grid = new Grid(userInput);
                } else {
                    System.out.println("Invalid number. Please enter a number between 2 and 15.");
                }

                // If invalid number catch & return message
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
            }
        }

        return grid;
    }

    private void playGame(Grid grid) {
        // Loop so user can continue entering coordinated until the game is won or lost
        boolean gameRunning = true;
        while (gameRunning) {
            grid.displayGrid();
            System.out.println("Enter a co-ordinate to reveal a square (E.g: B4) or 'q' to quit.\n");
            String chosenSquare = scanner.next();

            // Logic to quit the game at any point if q entered
            if (chosenSquare.equalsIgnoreCase("q")) {
                System.out.println("You chose to quit the game...\n");
                gameRunning = false;
                break;
            } else {
                gameRunning = processCoordinate(grid, chosenSquare, gameRunning);
            }
        }
    }

    private boolean processCoordinate(Grid grid, String chosenSquare, boolean gameRunning) {
        // Prevent less than 2 characters being entered
        if (chosenSquare.length() >= 2) {

            try {
                int row = Integer.parseInt(chosenSquare.substring(1)) - 1;
                int col = Character.toUpperCase(chosenSquare.charAt(0)) - 'A';

                // Check that the coordinate entered is valid / exists on the board
                if (GridUtils.isValidCoordinate(row, col, grid.getGridRows())) {
                    grid.revealItem(row, col);
                    GridItem item = grid.getGridItem(row, col);

                    // End game if bomb hit and return message
                    if (item.getState() == GridItemState.BOMB) {
                        grid.displayGrid();
                        System.out.println("Boom! You hit a bomb!\n");
                        gameRunning = false;

                        // End game if game is won and return message
                    } else if (grid.checkIfWon() == true) {
                        grid.displayGrid();
                        System.out.println("No more squares to reveal...\n");
                        System.out.println("CONGRATULATIONS, YOU'VE WON!\n");
                        gameRunning = false;
                    }

                    // If correct format of coordinate but is invalid for this board then return
                    // message
                } else {
                    System.out.println("Invalid square selected. Please try again.");
                }

                // If coordinate is not in the correct format then return message to try again
            } catch (Exception e) {
                System.out
                        .println("Invalid input. Please enter a valid coordinate like 'H2' or 'q' to quit.\n");
            }

            // If less than 2 character entered then instantly return a message to user
        } else {
            System.out.println("Invalid input. Please try again.");
        }

        return gameRunning;
    }

    private boolean askPlayAgain() {
        boolean validPlayAgainResponse = false;
        boolean playAgain = true;

        while (!validPlayAgainResponse) {
            // Play again prompt
            System.out.println("Would you like to play again? (y/n) \n");
            String playAgainResponse = scanner.next();

            // quit if useres selects n
            if (playAgainResponse.equalsIgnoreCase("n")) {
                System.out.println("Thanks for playing, see you next time! \n");
                playAgain = false;
                validPlayAgainResponse = true;
                scanner.close();

                // If y continue
            } else if (playAgainResponse.equalsIgnoreCase("y")) {
                System.out.println("Starting a new game...");
                validPlayAgainResponse = true;

                // If anything else stay in loop and try again
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        return playAgain;
    }
}
