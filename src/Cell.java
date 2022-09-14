public class Cell {
    private boolean isVisible;
    private boolean isBomb;
    private int bombCount;

    public Cell() {
        bombCount = 0;
        isBomb = false;
        isVisible = true;
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

    @Override
    public String toString() {
        if (isVisible)
            return isBomb ? "\uD83D\uDCA3" : " " + bombCount;
        return "XX";
    }
}
