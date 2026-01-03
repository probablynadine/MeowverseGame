package ui;

import javax.swing.*;
import java.awt.*;

public class StoryPanel extends JPanel {

    public StoryPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ===== TEXTE DE L'HISTOIRE =====
        JTextArea storyText = new JTextArea();
        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setFont(new Font("Serif", Font.PLAIN, 16));
        storyText.setForeground(Color.WHITE);
        storyText.setBackground(Color.BLACK);

        storyText.setText(
            "🐱 LE VILLAGE DES CHATS 🐱\n\n" +

            "Dans un village paisible vivaient quatre chats légendaires.\n" +
            "Leur monde était divisé en quatre régions, chacune avec sa spécialité.\n\n" +

            "🌿 RÉGION DE FLORA (Soins)\n" +
            "Une région verte et calme, connue pour la guérison.\n" +
            "Flora peut se soigner pendant le combat.\n\n" +

            "🛡 RÉGION DE COOKIE (Force)\n" +
            "Une région robuste, forgée par les armes et l'endurance.\n" +
            "Cookie résiste mieux aux attaques ennemies.\n\n" +

            "⚡ RÉGION DE GINGER (Vitesse)\n" +
            "Une région sportive et rapide.\n" +
            "Ginger attaque plus vite que les autres.\n\n" +

            "🧠 RÉGION DE NOVA (Intelligence)\n" +
            "Une région avancée en technologie et stratégie.\n" +
            "Nova inflige de lourds dégâts au monstre.\n\n" +

            "👾 LA MENACE\n" +
            "Un monstre est apparu pour détruire le village.\n" +
            "Les quatre chats ont décidé de s'unir pour l'affronter.\n\n" +

            "🎮 COMMENT JOUER\n" +
            "- Déplace le chat : ← →\n" +
            "- Sauter : ↑\n" +
            "- Attaquer : ESPACE\n" +
            "- Se soigner (Flora uniquement) : H\n\n" +

            "📦 AVANT LE COMBAT\n" +
            "Ramasse la nourriture (et potions pour Flora)\n" +
            "afin de remplir ta barre de vie.\n\n" + "mais attention le monstre a mit des pieges (des bombes) si on les touche on explose"+
            
            

            "⚔️ LE COMBAT\n" +
            "Une fois le temps écoulé, le combat commence.\n" +
            "Si ta vie tombe à 0 : GAME OVER.\n" +
            "Si le monstre tombe à 0 : VICTOIRE.\n\n" +

            "Bonne chance, héros du village ! 🐾"
        );

        JScrollPane scroll = new JScrollPane(storyText);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // ===== BOUTON COMMENCER =====
        JButton startBtn = new JButton("Commencer l'aventure");
        startBtn.setFont(new Font("Arial", Font.BOLD, 18));
        startBtn.setBackground(Color.DARK_GRAY);
        startBtn.setForeground(Color.WHITE);

        startBtn.addActionListener(e -> {
            SelectChatPanel select = new SelectChatPanel();

            Main.frame.getContentPane().removeAll();
            Main.frame.setContentPane(select);
            Main.frame.revalidate();
            Main.frame.repaint();
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.BLACK);
        bottom.add(startBtn);

        add(bottom, BorderLayout.SOUTH);
    }
}
