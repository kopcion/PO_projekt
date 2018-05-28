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

    public boolean withEnemy;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean leftEnemyDirection = true;
    private boolean rightEnemyDirection = false;
    private boolean upEnemyDirection = false;
    private boolean downEnemyDirection = false;
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
        for(int i=1; i <= B_HEIGHT/DOT_SIZE; i++){
            for(int j=1; j <= B_WIDTH/DOT_SIZE; j++){
                moves[i][j] = -1;
            }
        }
        for(int i=0; i < dots; i++){
            moves[y[i]/DOT_SIZE][x[i]/DOT_SIZE] = -2;
        }
        for(int i=0; i < B_HEIGHT/DOT_SIZE+2; i++){
            moves[0][i] = -2;
            moves[B_HEIGHT/DOT_SIZE+1][i] = -2;
            moves[i][0] = -2;
            moves[i][B_WIDTH/DOT_SIZE+1] = -2;
        }
        System.out.println(apple_x/DOT_SIZE);
        System.out.println(apple_y/DOT_SIZE);
        moves[apple_y/DOT_SIZE+1][apple_x/DOT_SIZE+1] = 0;
//        for(int i=0; i < 32; i++){
//            for(int j=0; j < 32; j++){
//                System.out.print(moves[i][j]+" ");
//            }
//            System.out.print('\n');
//        }
        bfs(apple_y/DOT_SIZE+1, apple_x/DOT_SIZE+1);

//        System.out.print('\n');
//        System.out.print(count++);
//        System.out.print('\n');
//        System.out.print('\n');

//        for(int i=0; i < 32; i++){
//            for(int j=0; j < 32; j++){
//                System.out.print(moves[i][j]+" ");
//            }
//            System.out.print('\n');
//        }

        rightEnemyDirection = false;
        upEnemyDirection = false;
        downEnemyDirection = false;
        leftEnemyDirection = false;
//        System.out.println("x: " + x_enemy[0]/DOT_SIZE);
//        System.out.println("y: " + y_enemy[0]/DOT_SIZE);
//        System.out.println("move is " + moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1]);
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 2){
//            System.out.println("move up");
            downEnemyDirection = true;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 1){
//            System.out.println("move down");
            upEnemyDirection = true;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 3){
//            System.out.println("move left");
            leftEnemyDirection = true;
        }
        if(moves[y_enemy[0]/DOT_SIZE+1][x_enemy[0]/DOT_SIZE+1] == 4){
//            System.out.println("move right");
            rightEnemyDirection = true;
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
//            ball = ImageIO.read(new File("./dot.png"));
            ball = ImageIO.read(link);
            link=Board.class.getClassLoader().getResource("img/apple.png");
//            apple = ImageIO.read(new File("./apple.png"));
            apple = ImageIO.read(link);
            link=Board.class.getClassLoader().getResource("img/head.png");
//            head = ImageIO.read(new File("./head.png"));
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
        if (withEnemy) enemyMove();
//        System.out.print(x[0]/10 + " ");
//        System.out.println(y[0]/10);
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (withEnemy) for (int z = dots_enemy; z > 0; z--) {
            x_enemy[z] = x_enemy[(z - 1)];
            y_enemy[z] = y_enemy[(z - 1)];
        }


        if (leftDirection) {
            x[0] -= DOT_SIZE;
        } else if (rightDirection) {
            x[0] += DOT_SIZE;
        } else if (upDirection) {
            y[0] -= DOT_SIZE;
        } else if (downDirection) {
            y[0] += DOT_SIZE;
        }

        if (leftEnemyDirection) {
            x_enemy[0] -= DOT_SIZE;
        } else if (rightEnemyDirection) {
            x_enemy[0] += DOT_SIZE;
        } else if (upEnemyDirection) {
            y_enemy[0] -= DOT_SIZE;
        } else if (downEnemyDirection) {
            y_enemy[0] += DOT_SIZE;
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

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}