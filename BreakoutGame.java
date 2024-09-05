import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BreakoutGame extends JPanel implements ActionListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 15;
    private final int BALL_SIZE = 20;
    private final int PADDLE_SPEED = 20;
    private final int BALL_SPEED_X = 5;
    private final int BALL_SPEED_Y = 5;

    private int paddleX;
    private int ballX, ballY;
    private int ballVelX = BALL_SPEED_X;
    private int ballVelY = BALL_SPEED_Y;
    private Timer timer;

    public BreakoutGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;

        timer = new Timer(16, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
                    paddleX -= PADDLE_SPEED;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < WIDTH - PADDLE_WIDTH) {
                    paddleX += PADDLE_SPEED;
                }
            }
        });

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballVelX;
        ballY += ballVelY;

        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            ballVelX = -ballVelX;
        }
        if (ballY <= 0) {
            ballVelY = -ballVelY;
        }
        if (ballY >= HEIGHT - BALL_SIZE) {
            if (ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
                ballVelY = -ballVelY;
            } else {
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout Game");
        BreakoutGame game = new BreakoutGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}