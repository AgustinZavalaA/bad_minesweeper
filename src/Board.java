import java.util.*;

public class Board {
    private final int size;
    private final int numberOBombs;
    private final Cell[][] board;

    public Board(int size, int numberOBombs) {
        this.size = size;
        this.numberOBombs = numberOBombs;
        this.board = new Cell[size][size];
        generateBoard();
    }

    private void generateBoard() {
        // Fill the board with empty cells
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = new Cell();

        // Generate random locations for the bombs
        Set<List<Integer>> bombs = new HashSet<>();
        Random random = new Random(42);
        while (bombs.size() < numberOBombs)
            bombs.add(new ArrayList<>(Arrays.asList(random.nextInt(size), random.nextInt(size))));

        // Put bombs in the board
        for (List<Integer> bomb : bombs) {
            board[bomb.get(0)][bomb.get(1)].setBomb(true);
        }

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j].setBombCount(calculateBombNeighbors(i, j));

    }

    private int calculateBombNeighbors(int row, int col) {
        int neighbors = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                try {
                    if (board[row + i][col + j].isBomb())
                        neighbors++;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        return neighbors;
    }

    public void printBoard() {
        System.out.print("   ");
        for (int i = 0; i < size; i++)
            System.out.print(i + "  ");
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }
}
