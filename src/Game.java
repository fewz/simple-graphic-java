import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener {
    private static final int WIDTH = 500; // Width of the game window
    private static final int HEIGHT = 500; // Height of the game window
    private static final int PLAYER_SIZE = 20; // Size of the player
    private static final int PLAYER_SPEED = 5; // Speed of the player movement

    private int playerX; // X-coordinate of the player
    private int playerY; // Y-coordinate of the player

    private boolean leftPressed; // Is the left arrow key pressed
    private boolean rightPressed; // Is the right arrow key pressed
    private boolean upPressed; // Is the up arrow key pressed
    private boolean downPressed; // Is the down arrow key pressed

    private List<Wall> walls; // List of walls

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        // Initial position of the player
        playerX = WIDTH / 2;
        playerY = HEIGHT / 2;

        walls = new ArrayList<>();
        walls.add(new Wall(200, 100, 20, 200));
        walls.add(new Wall(0, 0, 20, 200));
        walls.add(new Wall(400, 100, 20, 200));

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the player
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        // Draw the walls
        g.setColor(Color.BLACK);
        for (Wall wall : walls) {
            g.fillRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        }
    }

    public void movePlayer() {
        int newPlayerX = playerX;
        int newPlayerY = playerY;

        if (leftPressed) {
            newPlayerX -= PLAYER_SPEED;
        }
        if (rightPressed) {
            newPlayerX += PLAYER_SPEED;
        }
        if (upPressed) {
            newPlayerY -= PLAYER_SPEED;
        }
        if (downPressed) {
            newPlayerY += PLAYER_SPEED;
        }

        // Check for collision with walls
        Rectangle playerRect = new Rectangle(newPlayerX, newPlayerY, PLAYER_SIZE, PLAYER_SIZE);

        for (Wall wall : walls) {
            Rectangle wallRect = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (playerRect.intersects(wallRect)) {
                // Collision detected, don't update player's position
                return;
            }
        }

        // Update player's position if no collision
        playerX = newPlayerX;
        playerY = newPlayerY;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {
            game.movePlayer();
            game.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
