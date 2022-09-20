import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUIEndgameData {
    private final boolean won;
    private final String timeTakenInSeconds;
    private final int numberOfMoves;
    private final String configuration;
    private final GUIGame guiGame;

    public GUIEndgameData(boolean won, String timeTakenInSeconds, int numberOfMoves, String configuration, GUIGame guiGame) {
        this.won = won;
        this.timeTakenInSeconds = timeTakenInSeconds;
        this.numberOfMoves = numberOfMoves;
        this.configuration = configuration;
        this.guiGame = guiGame;
    }

    public void show() {
        JFrame frame = new JFrame(won ? "Congratulations" : "Better Luck next time");
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "resources/images/Header_" + (won ? "Won.png" : "Lost.png")))).getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 200);

        JPanel textPanel = new JPanel();
        JLabel text = new JLabel(
                String.format("<html>%s<br> Game configuration: %s<br> You took %s seconds and %d moves</html>",
                        won ? "Congratulations" : "You Lost :(", configuration, timeTakenInSeconds, numberOfMoves)
        );
        text.setFont(text.getFont().deriveFont(20.0F));
        textPanel.add(text);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Show Replay");
        button.addActionListener(e -> guiGame.replay());
        buttonPanel.add(button);
//        button.setSize(-1, 500);

        frame.getContentPane().add(BorderLayout.NORTH, textPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        //frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}
