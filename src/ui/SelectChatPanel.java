package ui;

import javax.swing.*;
import java.awt.*;
import model.TypeChat;

public class SelectChatPanel extends JPanel {

    public SelectChatPanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridLayout(2, 2));

        add(createChat("Ginger", "ginger.png", TypeChat.GINGER));
        add(createChat("Nova", "nova.png", TypeChat.NOVA));
        add(createChat("Flora", "flora.png", TypeChat.FLORA));
        add(createChat("Cookie", "cookie.png", TypeChat.COOKIE));
    }

    private JPanel createChat(String name, String img, TypeChat type) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JButton btn = new JButton(new ImageIcon("src/images/" + img));
        btn.addActionListener(e -> {
        	GamePanel game = new GamePanel(type);
            Main.frame.setContentPane(game);
            Main.frame.revalidate();
            Main.frame.repaint();
            game.requestFocusInWindow();
            
        });

        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);

        panel.add(btn, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        return panel;
    }
}
