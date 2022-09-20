import javax.swing.*;
import java.util.Objects;

public class GameConfigurator {
    private JPanel mainPanel;
    private JTextField boardSizeInput;
    private JTextField numberOfBombsInput;
    private JTextField randomSeedInput;
    private JButton generateBoardButton;

    public GameConfigurator() {
        generateBoardButton.addActionListener(e -> {
            int size = Integer.parseInt(boardSizeInput.getText());
            int numberOfBombs = Integer.parseInt(numberOfBombsInput.getText());

            boolean userSeedInputted = false;
            long seed = 0;
            if (!randomSeedInput.getText().isBlank()) {
                seed = Long.parseLong(randomSeedInput.getText());
                userSeedInputted = true;
            }

            new GUIGame(size, numberOfBombs, seed, userSeedInputted);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Configuration");
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(GameConfigurator.class.getClassLoader().getResource(
                "resources/images/Header_Conf.png"))).getImage());
        frame.setContentPane(new GameConfigurator().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
