import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    // To do:
    // -- (MAYBE) Reveal surrounding squares that are also empty (cascading effect)

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("Welcome to Minesweeper!");

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
                System.out.println("Enter a co-ordinate to reveal a square (E.g: B4) or 'q' to quit.\n");
                String chosenSquare = scanner.next();

                if (chosenSquare.equalsIgnoreCase("q")) {
                    System.out.println("You chose to quit the game...\n");
                    gameRunning = false;
                    playAgain = false;
                    break;
                }

                if (chosenSquare.length() >= 2) {

                    try {
                        int row = Integer.parseInt(chosenSquare.substring(1)) - 1;
                        int col = Character.toUpperCase(chosenSquare.charAt(0)) - 'A';

                        if (GridUtils.isValidCoordinate(row, col, grid.getGridRows())) {
                            grid.revealItem(row, col);
                            GridItem item = grid.getGridItem(row, col);
                            if (item.getState() == GridItemState.BOMB) {
                                grid.displayGrid();
                                System.out.println("Boom! You hit a bomb!\n");
                                gameRunning = false;
                            } else if (grid.checkIfWon() == true) {
                                grid.displayGrid();
                                System.out.println("No more squares to reveal...\n");
                                System.out.println("CONGRATULATIONS, YOU'VE WON!\n");
                                gameRunning = false;
                            }
                        } else {
                            System.out.println("Invalid square selected. Please try again.");
                        }

                    } catch (Exception e) {
                        System.out
                                .println("Invalid input. Please enter a valid coordinate like 'H2' or 'q' to quit.\n");
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
