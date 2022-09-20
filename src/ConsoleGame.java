import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {
    private final Board board;
    private final List<Integer[]> moveList;

    public ConsoleGame(int size, int numberOfBombs, long seed) {
        board = new Board(size, numberOfBombs, seed);
        moveList = new ArrayList<>();
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mark or see, row and column to see, separated with spaces\n" +
                " eg. input >> 1 0 0 to mark the first row col as a bomb");

        while (true) {
            board.printBoard();
            if (board.won()) {
                System.out.println("YOU WON !!!");
                break;
            }
            if (board.lost()) {
                System.out.println("YOU LOST. :(");
                break;
            }

            System.out.print("\n>> ");
            String[] playerMove = scanner.nextLine().trim().split("\\s+");

            boolean userInputIsAValidNumber = false;
            int mode = 0, row = 0, col = 0;

            while (playerMove.length != 3 || !userInputIsAValidNumber) {
                if (playerMove.length != 3)
                    System.out.println("\u001B[32mERROR: Invalid number of arguments in line\u001B[0m");
                else
                    try {
                        mode = Integer.parseInt(playerMove[0]);
                        row = Integer.parseInt(playerMove[1]);
                        col = Integer.parseInt(playerMove[2]);
                        userInputIsAValidNumber = true;
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("\u001B[32mERROR: Invalid arguments type, should be ints\u001B[0m");
                    }

                if (playerMove.length != 3 || !userInputIsAValidNumber) {
                    System.out.print("\n>> ");
                    playerMove = scanner.nextLine().trim().split("\\s+");
                }
            }

            board.applyMove(mode, row, col);
            moveList.add(new Integer[]{mode, row, col});
        }
        scanner.nextLine();
        replay();
    }

    private void replay() {
        board.generateBoard();
        for (Integer[] move : moveList) {
            board.printBoard();
            board.applyMove(move[0], move[1], move[2]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
        board.printBoard();
    }
}
