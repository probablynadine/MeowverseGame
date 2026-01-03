package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import model.TypeChat;

public class GamePanel extends JPanel implements KeyListener {

    // ================= STATES =================
    private enum State { COLLECT, COMBAT }
    private State state = State.COLLECT;

    // ================= IMAGES =================
    private Image bg, chatImg, foodImg, potionImg, bombImg, bossImg;

    // ================= CHAT =================
    private int chatX = 360, chatY = 420;
    private final int CHAT_W = 80, CHAT_H = 80;
    private int chatSpeed = 6;

    // Jump
    private boolean jumping = false;
    private int velY = 0;
    private final int GRAVITY = 1;
    private final int JUMP_POWER = -18;
    private final int GROUND_Y = 420;

    // Life
    private int life = 0;
    private final int MAX_LIFE = 100;

    // ================= CHAT TYPE =================
    private TypeChat chatType;
    private int chatDamage = 10;
    private int bossDamage = 5;
    private int shootCooldown = 500;
    private long lastShotTime = 0;

    // ================= ITEMS =================
    private int itemX, itemY;
    private boolean itemActive = false;

    private int bombX, bombY;
    private boolean bombActive = false;

    private boolean isFlora = false;
    
    private int healCount = 0;
    private final int MAX_HEAL = 5;

    private long lastHealTime = 0;
    private final int HEAL_COOLDOWN = 5000; // 5 secondes


    // ================= BOSS =================
    private int bossX = 330, bossY = 420;
    private final int BOSS_W = 120, BOSS_H = 120;
    private int bossLife = 150;
    private final int MAX_BOSS_LIFE = 150;

    // Boss attack
    private boolean bossAtk = false;
    private double atkX, atkY, atkDX, atkDY;
    private long lastBossAttack = 0;

    // Chat shot
    private boolean chatShot = false;
    private double shotX, shotY, shotDX, shotDY;

    // ================= TIME =================
    private int timeLeft = 60 ;

    // ================= LOOP =================
    private Timer gameLoop;
    private long lastItemSpawn = 0;
    private long lastBombSpawn = 0;

    private final Random rand = new Random();

    // ================= CONSTRUCTOR =================
    public GamePanel(TypeChat type) {

        this.chatType = type;

        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(this);

        bg = new ImageIcon("src/images/background.png").getImage();
        foodImg = new ImageIcon("src/images/food.png").getImage();
        potionImg = new ImageIcon("src/images/potion.png").getImage();
        bombImg = new ImageIcon("src/images/bomb.png").getImage();
        bossImg = new ImageIcon("src/images/boss.png").getImage();

        switch (type) {
            case GINGER -> chatImg = new ImageIcon("src/images/ginger.png").getImage();
            case NOVA -> chatImg = new ImageIcon("src/images/nova.png").getImage();
            case FLORA -> {
                chatImg = new ImageIcon("src/images/flora.png").getImage();
                isFlora = true;
            }
            case COOKIE -> chatImg = new ImageIcon("src/images/cookie.png").getImage();
        }

        applyChatAbilities();
        startGame();
    }

    // ================= CHAT ABILITIES =================
    private void applyChatAbilities() {
        switch (chatType) {
            case FLORA -> chatDamage = 7;
            case COOKIE -> bossDamage = 3;
            case NOVA -> chatDamage = 15;
            case GINGER -> shootCooldown = 250;
        }
    }

    // ================= START GAME =================
    private void startGame() {
        gameLoop = new Timer(16, e -> update());
        gameLoop.start();
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    // ================= UPDATE =================
    private void update() {
        long now = System.currentTimeMillis();

        applyJump();

        if (state == State.COLLECT) {
            updateCollect(now);
        } else {
            updateCombat(now);
        }

        repaint();
    }

    // ================= COLLECT =================
    private void updateCollect(long now) {
if (now % 1000 < 20) {
            timeLeft--;
            if (timeLeft <= 0) {
                state = State.COMBAT;
                itemActive = false;
                bombActive = false;
                return;
            }
        }

        if (!itemActive && now - lastItemSpawn > 2000) {
            itemX = rand.nextInt(740);
            itemY = -40;
            itemActive = true;
            lastItemSpawn = now;
        }

        if (!bombActive && now - lastBombSpawn > 2500) {
            bombX = rand.nextInt(740);
            bombY = -40;
            bombActive = true;
            lastBombSpawn = now;
        }

        Rectangle chat = new Rectangle(chatX, chatY, CHAT_W, CHAT_H);

        if (itemActive) {
            itemY += 4;
            Rectangle item = new Rectangle(itemX, itemY, 32, 32);
            if (item.intersects(chat)) {
                life = Math.min(MAX_LIFE, life + 5);
                itemActive = false;
            }
            if (itemY > getHeight()) itemActive = false;
        }

        if (bombActive) {
            bombY += 6;
            Rectangle bomb = new Rectangle(bombX, bombY, 32, 32);
            if (bomb.intersects(chat)) {
                JOptionPane.showMessageDialog(this, "💥 GAME OVER");
                System.exit(0);
            }
            if (bombY > getHeight()) bombActive = false;
        }
    }

    // ================= COMBAT =================
    private void updateCombat(long now) {

        if (chatX < bossX) bossX -= 2;
        else if (chatX > bossX) bossX += 2;

        if (!bossAtk && now - lastBossAttack > 2000) {
            atkX = bossX + BOSS_W / 2.0;
            atkY = bossY;
            double dx = chatX - atkX;
            double dy = chatY - atkY;
            double len = Math.sqrt(dx * dx + dy * dy);
            if (len != 0) {
                atkDX = (dx / len) * 8;
                atkDY = (dy / len) * 8;
                bossAtk = true;
                lastBossAttack = now;
            }
        }

        Rectangle chat = new Rectangle(chatX, chatY, CHAT_W, CHAT_H);

        if (bossAtk) {
            atkX += atkDX;
            atkY += atkDY;
            Rectangle atk = new Rectangle((int) atkX, (int) atkY, 20, 20);
            if (atk.intersects(chat)) {
                life = Math.max(0, life - bossDamage);
                bossAtk = false;
                if (life == 0) {
                	gameLoop.stop();
                	Main.frame.setContentPane(new EndPanel(false));
                	Main.frame.revalidate();
                	return;
                }
            }
            if (atkX < 0 || atkX > getWidth() || atkY < 0 || atkY > getHeight()) {
                bossAtk = false;
            }
        }

        if (chatShot) {
            shotX += shotDX;
            shotY += shotDY;
            Rectangle shot = new Rectangle((int) shotX, (int) shotY, 10, 10);
            Rectangle boss = new Rectangle(bossX, bossY, BOSS_W, BOSS_H);
            if (shot.intersects(boss)) {
                bossLife = Math.max(0, bossLife - chatDamage);
                chatShot = false;
                if (bossLife == 0) {
                	gameLoop.stop();
                	Main.frame.setContentPane(new EndPanel(true));
                	Main.frame.revalidate();
                	return;

                }
            }
            if (shotX < 0 || shotX > getWidth() || shotY < 0 || shotY > getHeight()) {
                chatShot = false;
            }
        }
    }

    // ================= JUMP =================
    private void applyJump() {
        if (jumping) {
            velY += GRAVITY;
            chatY += velY;
            if (chatY >= GROUND_Y) {
                chatY = GROUND_Y;
                jumping = false;
                velY = 0;
            }
        }
    }

    // ================= DRAW =================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(chatImg, chatX, chatY, CHAT_W, CHAT_H, null);

        if (itemActive)
            g.drawImage(isFlora ? potionImg : foodImg, itemX, itemY, 32, 32, null);
if (bombActive)
            g.drawImage(bombImg, bombX, bombY, 32, 32, null);

        // Chat life bar
        g.setColor(Color.RED);
        g.fillRect(20, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(20, 20, life * 2, 20);
        g.setColor(Color.WHITE);
        g.drawRect(20, 20, 200, 20);
        g.drawString("Chat", 20, 15);

        // Boss life bar
        g.setColor(Color.RED);
        g.fillRect(580, 20, 200, 20);
        g.setColor(Color.ORANGE);
        g.fillRect(580, 20, (int)(200.0 * bossLife / MAX_BOSS_LIFE), 20);
        g.setColor(Color.WHITE);
        g.drawRect(580, 20, 200, 20);
        g.drawString("Boss", 580, 15);

        g.drawString("Temps : " + timeLeft + "s", 20, 60);

        if (state == State.COMBAT)
            g.drawImage(bossImg, bossX, bossY, BOSS_W, BOSS_H, null);

        if (bossAtk) g.fillOval((int) atkX, (int) atkY, 20, 20);
        if (chatShot) g.fillOval((int) shotX, (int) shotY, 10, 10);
        if (chatType == TypeChat.FLORA) {
            g.setColor(Color.GREEN);
            g.drawString("Soins : " + (MAX_HEAL - healCount), 20, 85);
        }
    }

    // ================= INPUT =================
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) chatX -= chatSpeed;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) chatX += chatSpeed;

        if (e.getKeyCode() == KeyEvent.VK_UP && !jumping) {
            velY = JUMP_POWER;
            jumping = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && !chatShot && state == State.COMBAT) {
            long now = System.currentTimeMillis();
            if (now - lastShotTime > shootCooldown) {
                shotX = chatX + CHAT_W / 2.0;
                shotY = chatY;
                double dx = bossX - shotX;
                double dy = bossY - shotY;
                double len = Math.sqrt(dx * dx + dy * dy);
                if (len != 0) {
                    shotDX = (dx / len) * 12;
                    shotDY = (dy / len) * 12;
                    chatShot = true;
                    lastShotTime = now;
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_H
                && chatType == TypeChat.FLORA
                && healCount < MAX_HEAL) {

            long now = System.currentTimeMillis();

            if (now - lastHealTime >= HEAL_COOLDOWN) {
                life = Math.min(MAX_LIFE, life + 15);
                healCount++;
                lastHealTime = now;
            }
        }

    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
