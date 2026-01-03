package ui;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {

    public EndPanel(boolean victory) {

        setLayout(null);

        Image bg;
        if (victory) {
            bg = new ImageIcon("src/images/victory.png").getImage();
        } else {
            bg = new ImageIcon("src/images/defeat.png").getImage();
        }

        JLabel image = new JLabel(new ImageIcon(bg));
        image.setBounds(0, 0, 800, 600);
        add (image);

        JButton replay = new JButton("Rejouer");
        replay.setBounds(300, 450, 200, 40);
        replay.addActionListener(e -> {
            Main.frame.setContentPane(new StartPanel());
            Main.frame.revalidate();
            Main.frame.repaint();
        });

        image.add(replay);
    }
}
