import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GUIGame {
    private final Board board;
    private final ButtonInfo[][] buttonInfos;
    private final long initialTime;
    private final String configurationBoardString;
    private final List<Integer[]> moveList;

    public GUIGame(int size, int numberOfBombs, long seed, boolean userSeedInputted) {
        if (userSeedInputted)
            board = new Board(size, numberOfBombs, seed);
        else
            board = new Board(size, numberOfBombs);
        buttonInfos = new ButtonInfo[size][size];
        configurationBoardString = String.format("%d Bombs %dx%d Board with Seed %d", numberOfBombs, size, size, board.getSeed());
        moveList = new ArrayList<>();

        JFrame frame = new JFrame("Minesweeper");
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "resources/images/Header_Game.png"))).getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setLayout(new GridLayout(size, size, 5, 5));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                constraints.gridx = i;
                constraints.gridy = j;

                JButton button = new JButton("");
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setFont(button.getFont().deriveFont(19.0F));

                buttonInfos[i][j] = new ButtonInfo(i, j, button);

                ButtonInfo bi = buttonInfos[i][j];
                bi.button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.applyMove(e.getButton() == MouseEvent.BUTTON3 ? 1 : 0, bi.x, bi.y);
//                        board.printBoard();
                        moveList.add(new Integer[]{e.getButton() == MouseEvent.BUTTON3 ? 1 : 0, bi.x, bi.y});
                        updateButtons(true);
                    }
                });
                panel.add(button, constraints);
            }
        }

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        initialTime = System.currentTimeMillis();
    }

    private void updateButtons(boolean showEndgameWindow) {
        Cell[][] cells = board.getBoard();

        List<Color> colors = new ArrayList<>(Arrays.asList(
                Color.WHITE,
                Color.BLUE,
                Color.RED,
                Color.MAGENTA,
                Color.PINK,
                Color.GREEN,
                Color.YELLOW,
                Color.CYAN
        ));

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell cell = cells[i][j];
                ButtonInfo buttonInfo = buttonInfos[i][j];

                buttonInfo.button.setText(cell.toStringWithoutColor());

                if (cell.isCheckedByPlayer())
                    buttonInfo.button.setForeground(Color.RED);

                buttonInfo.button.setContentAreaFilled(true);
                if (cell.isVisible()) {
                    buttonInfo.button.setForeground(colors.get(cell.getBombCount()));
                    buttonInfo.button.setContentAreaFilled(false);
                }
            }
        }

        if (!showEndgameWindow)
            return;

        if (board.won()) {
            String timeTaken = String.valueOf((double) (System.currentTimeMillis() - initialTime) / 1_000);
            GUIEndgameData won = new GUIEndgameData(true, timeTaken, moveList.size(), configurationBoardString, this);
            won.show();
        }
        if (board.lost()) {
            String timeTaken = String.valueOf((double) (System.currentTimeMillis() - initialTime) / 1_000);
            GUIEndgameData lost = new GUIEndgameData(false, timeTaken, moveList.size(), configurationBoardString, this);
            lost.show();
        }
    }

    public void replay() {
        board.generateBoard();
        updateButtons(false);
        Timer timer = new Timer(500, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Integer[] move = moveList.get(index);

                board.applyMove(move[0], move[1], move[2]);
                updateButtons(false);

                index++;
                if (index == moveList.size())
                    ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private static class ButtonInfo {
        public final int x, y;
        public final JButton button;

        public ButtonInfo(int x, int y, JButton button) {
            this.x = x;
            this.y = y;
            this.button = button;
        }
    }
}
