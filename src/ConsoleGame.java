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
        System.out.println("Enter mark or see, row and column to see, separated with spaces\n eg. input >> 1 0 0 to mark the first row col as a bomb");

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
            int mode = Integer.parseInt(playerMove[0]);
            int row = Integer.parseInt(playerMove[1]);
            int col = Integer.parseInt(playerMove[2]);
            board.applyMove(mode, row, col);
            moveList.add(new Integer[]{mode, row, col});
            System.out.print("\033[H\033[2J");
            System.out.flush();
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
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        board.printBoard();
    }
}
