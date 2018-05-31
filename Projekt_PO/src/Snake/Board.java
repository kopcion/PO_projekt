package Snake;
import javafx.util.Pair;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    public int DELAY;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    private final int x_enemy[] = new int[ALL_DOTS];
    private final int y_enemy[] = new int[ALL_DOTS];

    private int dots;
    private int dots_enemy;
    private int apple_x;
    private int apple_y;

    private enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    private Direction enemy = Direction.LEFT;
    private Direction player = Direction.RIGHT;
    public boolean withEnemy;
    private boolean inGame = true;
    private boolean victory = false;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board(int Delay, boolean withEnemy) {
        this.DELAY = Delay;
        this.withEnemy = withEnemy;
        initBoard();
    }

    private int[][] moves = new int[B_HEIGHT/DOT_SIZE+2][B_WIDTH/DOT_SIZE+2];

    private void enemyMove(){
        for (int i=1; i <= B_HEIGHT/DOT_SIZE; i++){
            for(int j=1; j <= B_WIDTH/DOT_SIZE; j++){
                moves[i][j] = -1;
            }
        }
        for(int i=0; i < dots; i++){
            moves[y[i]/DOT_SIZE+1][x[i]/DOT_SIZE+1] = -2;
        }
        for(int i=1; i < dots_enemy; i++){
            moves[y_enemy[i]/DOT_SIZE+1][x_enemy[i]/DOT_SIZE+1] = -2;
        }

        for(int i=0; i < B_HEIGHT/DOT_SIZE+2; i++){
            moves[0][i] = -2;
            moves[B_HEIGHT/DOT_SIZE+1][i] = -2;
            moves[i][0] = -2;
            moves[i][B_WIDTH/DOT_SIZE+1] = -2;
        }

        moves[apple_y/DOT_SIZE+1][apple_x/DOT_SIZE+1] = 0;

        bfs(apple_y/DOT_SIZE+1, apple_x/DOT_SIZE+1);

        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 2){
            enemy = Direction.DOWN;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 1){
            enemy = Direction.UP;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 3){
            enemy = Direction.LEFT;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 4){
            enemy = Direction.RIGHT;
        }
    }

    private void bfs(int x, int y){
        Queue<Pair<Integer,Integer>> queue = new LinkedList();
        if(moves[x][y]==0){
            if(x < B_HEIGHT/DOT_SIZE){
                moves[x+1][y] = 1;
                queue.add(new Pair<>(x+1,y));
            }
            if(x > 1){
                moves[x-1][y] = 2;
                queue.add(new Pair<>(x-1,y));
            }
            if(y < B_WIDTH/DOT_SIZE){
                moves[x][y+1] = 3;
                queue.add(new Pair<>(x,y+1));
            }
            if(y > 1){
                moves[x][y-1] = 4;
                queue.add(new Pair<>(x,y-1));
            }
        }

        while(!queue.isEmpty()){
            Pair<Integer,Integer> pair = queue.remove();
            x = pair.getKey();
            y = pair.getValue();

            if(moves[x+1][y] == -1){
                moves[x+1][y] = 1;
                queue.add(new Pair<>(x+1, y));
            }
            if(moves[x-1][y] == -1){
                moves[x-1][y] = 2;
                queue.add(new Pair<>(x-1, y));
            }
            if(moves[x][y+1] == -1){
                moves[x][y+1] = 3;
                queue.add(new Pair<>(x, y+1));
            }
            if(moves[x][y-1] == -1){
                moves[x][y-1] = 4;
                queue.add(new Pair<>(x, y-1));
            }
        }
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        try {
            URL link=Board.class.getClassLoader().getResource("img/dot.png");
            ball = ImageIO.read(link);
            link=Board.class.getClassLoader().getResource("img/apple.png");
            apple = ImageIO.read(link);
            link=Board.class.getClassLoader().getResource("img/head.png");
            head = ImageIO.read(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initGame() {

        dots = 3;
        dots_enemy = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        if(withEnemy) for (int z = 0; z < dots_enemy; z++) {
            x_enemy[z] = 250 + z * 10;
            y_enemy[z] = 250;
        }
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            if(withEnemy){
                for (int z = 0; z < dots_enemy; z++) {
                    if (z == 0) {
                        g.drawImage(head, x_enemy[z], y_enemy[z], this);
                    } else {
                        g.drawImage(ball, x_enemy[z], y_enemy[z], this);
                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg;
        if(victory)
            msg = "Victory!";
        else
            msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        } else if(withEnemy && (x_enemy[0] == apple_x) && (y_enemy[0] == apple_y)){
            dots_enemy++;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        switch (player){
            case LEFT:
                x[0] -= DOT_SIZE;
                break;
            case RIGHT:
                x[0] += DOT_SIZE;
                break;
            case UP:
                y[0] -= DOT_SIZE;
                break;
            case DOWN:
                y[0] += DOT_SIZE;
                break;
        }
        if (withEnemy){
            enemyMove();

            for (int z = dots_enemy; z > 0; z--) {
                x_enemy[z] = x_enemy[(z - 1)];
                y_enemy[z] = y_enemy[(z - 1)];
            }
            switch (enemy){
                case LEFT:
                    x_enemy[0] -= DOT_SIZE;
                    break;
                case RIGHT:
                    x_enemy[0] += DOT_SIZE;
                    break;
                case UP:
                    y_enemy[0] -= DOT_SIZE;
                    break;
                case DOWN:
                    y_enemy[0] += DOT_SIZE;
                    break;
            }
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                return;
            }
        }

        if(withEnemy) for (int z = dots_enemy; z > 0; z--) {
            if ((z > 4) && (x_enemy[0] == x_enemy[z]) && (y_enemy[0] == y_enemy[z])) {
                inGame = false;
                victory = true;
                return;
            }
        }

        if(withEnemy) for(int z = dots; z > 0; z--){
            if(x_enemy[0]==x[z] && y_enemy[0] == y[z]){
                inGame = false;
                victory = true;
                return;
            }
        }
        if(withEnemy) for(int z = dots_enemy; z > 0; z--){
            if(x[0]==x_enemy[z] && y[0] == y_enemy[z]){
                inGame = false;
                victory = false;
                return;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();

            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (player != Direction.RIGHT)) {
                player = Direction.LEFT;
            }

            if ((key == KeyEvent.VK_RIGHT) && (player != Direction.LEFT)) {
                player = Direction.RIGHT;
            }

            if ((key == KeyEvent.VK_UP) && (player != Direction.DOWN)) {
                player = Direction.UP;
            }

            if ((key == KeyEvent.VK_DOWN) && (player != Direction.UP)) {
                player = Direction.DOWN;
            }
        }
    }
}