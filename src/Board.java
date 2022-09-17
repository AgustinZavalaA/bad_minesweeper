import java.util.*;

public class Board {
    private final int size;
    private final int numberOBombs;
    private final Cell[][] board;
    private final long seed;

    public Board(int size, int numberOBombs, long seed) {
        this.size = size;
        this.numberOBombs = numberOBombs;
        this.board = new Cell[size][size];
        
        Random random = new Random(seed);
        this.seed = random.nextLong();

        generateBoard();
    }

    public void generateBoard() {
        // Fill the board with empty cells
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = new Cell();

        // Generate random locations for the bombs
        Set<List<Integer>> bombs = new HashSet<>();
        Random random = new Random(seed);
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

    public void applyMove(int mode, int row, int col) {
        if (board[row][col].isVisible())
            return;

        if (mode == 1) {
            board[row][col].setCheckedByPlayer(true);
            return;
        }

        if (board[row][col].isCheckedByPlayer())
            return;

        clearCells(row, col);
    }

    private void clearCells(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            return;

        if (board[row][col].isVisible())
            return;

        board[row][col].setVisible(true);

        if (board[row][col].getBombCount() != 0) {
            return;
        }

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                clearCells(row + i, col + j);
            }
    }

    public boolean lost() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j].isBomb() && board[i][j].isVisible())
                    return true;
        return false;
    }

    public boolean won() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (!board[i][j].isVisible() && !board[i][j].isBomb()) {
                    return false;
                }
        return true;
    }
}
