import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean isVisible;
    private boolean isBomb;
    private int bombCount;
    private boolean isCheckedByPlayer;
    private final List<String> colors;

    public Cell() {
        bombCount = 0;
        isBomb = false;
        isVisible = false;
        isCheckedByPlayer = false;

        colors = new ArrayList<>();
        colors.add("\u001B[0m"); // WHITE
        colors.add("\u001B[34m"); // BLUE
        colors.add("\u001B[32m"); // GREEN
        colors.add("\u001B[31m"); // RED
        colors.add("\u001B[35m"); // PURPLE
        colors.add("\u001B[33m"); // YELLOW
        colors.add("\u001B[36m"); // CYAN
        colors.add("\u001B[95m"); // BRIGHT CYAN
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public boolean isCheckedByPlayer() {
        return isCheckedByPlayer;
    }

    public void setCheckedByPlayer(boolean checkedByPlayer) {
        isCheckedByPlayer = checkedByPlayer;
    }

    @Override
    public String toString() {
        if (isCheckedByPlayer)
            return "\uD83D\uDEA9"; // Flag emoji 🚩
        if (isVisible) {
            if (isBomb)
                return "\uD83D\uDCA3"; // Bomb emoji 💣

            if (bombCount == 0)
                return "  ";

            String COLOR_RESET = "\u001B[0m";
            return " " + colors.get(bombCount) + bombCount + COLOR_RESET;
        }
        return "XX";
    }
}
