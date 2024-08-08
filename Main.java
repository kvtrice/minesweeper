import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Minesweeper!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of grid rows you'd like to play: ");
        int userInput = scanner.nextInt();

       Grid gameGrid = new Grid(userInput);

        scanner.close();
    }
}