package ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    private Image background;

    public StartPanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        background = new ImageIcon("src/images/background.png").getImage();

        JLabel title = new JLabel("MEOWVERSE", SwingConstants.CENTER);
        title.setBounds(200, 150, 400, 60);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        add(title);

        JButton start = new JButton("Commencer");
        start.setBounds(300, 300, 200, 50);
        add(start);

        start.addActionListener(e -> {
            StoryPanel story = new StoryPanel();

            Main.frame.getContentPane().removeAll();
            Main.frame.setContentPane(story);
            Main.frame.revalidate();
            Main.frame.repaint();
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}