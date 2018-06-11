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
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    private Control control;

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
    private int apple_x2;
    private int apple_y;
    private int apple_y2;
    private int starvation;
    private int last_apple;
    private int last_apple_enemy;
    private int time;
    private boolean bonusApple;

    private enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    private Direction enemy;
    private Direction player;
    public boolean withEnemy;
    private boolean inGame = true;
    private boolean victory = false;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board(int Delay, boolean withEnemy, int starvation, Control control, boolean bonusApple) {
        this.DELAY = Delay;
        this.withEnemy = withEnemy;
        this.control = control;
        this.starvation = starvation;
        this.bonusApple = bonusApple;

        inGame = true;
        victory = false;
        last_apple = 0;
        last_apple_enemy = 0;
        this.starvation = starvation;
        time = 0;
        enemy = Direction.LEFT;
        player = Direction.RIGHT;
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
        if((Math.abs(y_enemy[0]-apple_y) + Math.abs(x_enemy[0]-apple_x)) <= (Math.abs(y_enemy[0]-apple_y2) + Math.abs(x_enemy[0]-apple_x2))){
            moves[apple_y/DOT_SIZE+1][apple_x/DOT_SIZE+1] = 0;
            bfs(apple_y/DOT_SIZE+1, apple_x/DOT_SIZE+1);
        } else {
            moves[apple_y2/DOT_SIZE+1][apple_x2/DOT_SIZE+1] = 0;
            bfs(apple_y2/DOT_SIZE+1, apple_x2/DOT_SIZE+1);
        }



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
            time++;
            g.drawImage(apple, apple_x, apple_y, this);
            if(bonusApple) g.drawImage(apple, apple_x2, apple_y2, this);

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
        int score = 160/DELAY * (dots-3);
        if(starvation != 0){
            score = (time-starvation)/20;
        }
        if(withEnemy){
            score = score * (160/DELAY) * 2;
        }
        String msg;
        if(victory)
            msg = "Victory!";
        else
            msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        FontMetrics metr1 = getFontMetrics(new Font("Helvetica", Font.BOLD, 9));

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 - 16);
        int place = 0;
        try {
            control.getDatabase().addToDB(score);
            place = control.getDatabase().biggerThan(score)+1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.drawString("You placed " + Integer.toString(place) + " with " + Integer.toString(score) + " points.", (B_WIDTH - metr.stringWidth("You placed " + Integer.toString(place) + " with " + Integer.toString(score) + " points.")) / 2, B_HEIGHT / 2);
        g.setFont(new Font("Helvetica", Font.BOLD, 9));
        g.drawString("Press [backspace] to return to menu", (B_WIDTH - metr1.stringWidth("Press [backspace] to return to menu")) / 2, B_HEIGHT / 2+32);
        timer.stop();
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y) || (bonusApple && x[0] == apple_x2) && (y[0] == apple_y2)) {
            dots++;
            last_apple = time;
            locateApple();
        } else if(withEnemy && ((x_enemy[0] == apple_x) && (y_enemy[0] == apple_y) || (bonusApple && x_enemy[0] == apple_x2) && (y_enemy[0] == apple_y2))){
            dots_enemy++;
            last_apple_enemy = time;
            locateApple();
        }
    }

    private void move() {
        if(starvation!=0){
            if(time - last_apple > starvation) {
                dots--;
                last_apple = time;
            }
            if(withEnemy && time - last_apple_enemy > starvation){
                dots_enemy--;
                last_apple_enemy = time;
            }
            if(dots == 0){
                victory = false;
                inGame = false;
//                gameOver(g);
                return;
            }
            if(withEnemy && dots_enemy == 0){
                victory = true;
                inGame = false;
//                gameOver(g);
                return;
            }
        }
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

    void bruteAppleLoc(){
        int pos = (int) (Math.random() * RAND_POS * RAND_POS - dots - dots_enemy),pos2 = pos;
        int board[][] = new int[B_HEIGHT][B_WIDTH];
        for(int i=0; i < B_HEIGHT; i++){
            for(int j=0; j < B_WIDTH; j++){
                board[i][j] = 0;
            }
        }
        for(int i=0; i < dots; i++){
            board[x[i]][y[i]] = 1;
        }
        if(withEnemy) for(int i=0; i < dots_enemy; i++){
            board[x_enemy[i]][y_enemy[i]] = 1;
        }
        for(int i=0; i < B_HEIGHT*B_WIDTH; i++){
            if(board[i/B_HEIGHT][i%B_WIDTH] == 0)
                pos--;
            if(pos == 0){
                apple_x = i/B_HEIGHT * DOT_SIZE;
                apple_y = i%B_WIDTH * DOT_SIZE;
            }
        }
        for(int i=B_HEIGHT*B_WIDTH-1; i >= 0 ; i--){
            if(board[i/B_HEIGHT][i%B_WIDTH] == 0)
                pos2--;
            if(pos2 == 0){
                apple_x2 = i/B_HEIGHT * DOT_SIZE;
                apple_y2 = i%B_WIDTH * DOT_SIZE;
            }
        }
    }

    void randomAppleLoc(){
        int board[][] = new int[B_HEIGHT][B_WIDTH];
        for(int i=0; i < B_HEIGHT; i++){
            for(int j=0; j < B_WIDTH; j++){
                board[i][j] = 0;
            }
        }
        for(int i=0; i < dots; i++){
            board[x[i]][y[i]] = 1;
        }
        if(withEnemy) for(int i=0; i < dots_enemy; i++){
            board[x_enemy[i]][y_enemy[i]] = 1;
        }

        while(true){
            int r = (int) (Math.random() * RAND_POS);
            apple_x = r * DOT_SIZE;

            int r1 = (int) (Math.random() * RAND_POS);
            apple_y = r1 * DOT_SIZE;

            if(board[apple_x/DOT_SIZE][apple_y/DOT_SIZE] == 1){
                continue;
            } else if(bonusApple){
                apple_x2 = (RAND_POS-r) * DOT_SIZE;
                apple_y2 = (RAND_POS-r1) * DOT_SIZE;
                if(board[apple_x2/DOT_SIZE][apple_y2/DOT_SIZE] == 1){
                    continue;
                }
            }
            return;
        }
    }

    private void locateApple() {
        if(dots + dots_enemy > RAND_POS*RAND_POS / 7){
            bruteAppleLoc();
            System.out.println("apple located with brute");
        } else {
            randomAppleLoc();
            System.out.println("apple located with random");
        }
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

            if(inGame){
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
            } else {
                if(key == KeyEvent.VK_BACK_SPACE){
                    control.getSnake().setVisible(false);
                    control.getGameMode().setVisible(true);
                }

                if(key == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }

        }
    }
}