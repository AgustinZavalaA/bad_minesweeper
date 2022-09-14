public class ConsoleGame {
    private final Board board;

    public ConsoleGame(int size, int numberOfBombs) {
        board = new Board(size, numberOfBombs);
    }


    public void play() {
        board.printBoard();
    }
}
