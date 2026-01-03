package ui;

import javax.swing.*;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Meowverse");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // ⚠️ ON COMMENCE TOUJOURS PAR L'ACCUEIL
            frame.setContentPane(new StartPanel());

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
