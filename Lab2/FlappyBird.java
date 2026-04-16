package Lab2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // --- BÀI 1: Cấu hình kích thước ---
    int boardWidth = 360;
    int boardHeight = 640;

    // Hình ảnh
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // --- BÀI 2: Lớp Bird (Chim) ---
    int birdX = 100;
    int birdY = 320;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX, y = birdY;
        int width = birdWidth, height = birdHeight;
        Image img;
        Bird(Image img) { this.img = img; }
    }

    // --- BÀI 3: Lớp Pipe (Ống) ---
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX, y = pipeY;
        int width = pipeWidth, height = pipeHeight;
        Image img;
        boolean passed = false; // Dùng để kiểm tra cộng điểm
        Pipe(Image img) { this.img = img; }
    }

    // Khởi tạo các đối tượng và logic trò chơi
    Bird bird;
    ArrayList<Pipe> pipes;

    Timer gameLoop; // Vòng lặp chính của game
    Timer placePipeTimer; // Vòng lặp tạo ống mới

    int velocityY = 0;  // Tốc độ di chuyển trục Y của chim
    int gravity = 1;    // Trọng lực kéo xuống
    int velocityX = -4; // Tốc độ ống trôi sang trái

    // --- BÀI 4: Biến trạng thái ---
    boolean gameOver = false;
    double score = 0;

    public FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Tải hình ảnh 
        backgroundImg = new ImageIcon("flappybirdbg.png").getImage();
        birdImg = new ImageIcon("flappybird.png").getImage();
        topPipeImg = new ImageIcon("toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("bottompipe.png").getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<>();

        // Timer tạo ống định kỳ (mỗi 2 giây = 2000 ms)
        placePipeTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipeTimer.start();

        // Game Loop (60 FPS = 1000/60 ~ 16ms)
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // Hàm tạo ống ngẫu nhiên
    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; // Khoảng trống giữa 2 ống trên/dưới

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Hàm vẽ đồ họa UI
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Vẽ hình nền
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Vẽ chim
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // Vẽ ống
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Vẽ Điểm số và thông báo Game Over
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 70, 320);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Nhấn Space hoặc Enter để chơi lại", 30, 360);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Hàm xử lý vật lý và logic mỗi khung hình
    public void move() {
        // Rớt thẳng đứng (Bài 2)
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); // Ngăn chim bay lố trần

        // Cập nhật vị trí ống và xét va chạm
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // Tính điểm
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // Do mỗi mốc có 2 ống (trên + dưới), nên mỗi ống cộng 0.5
            }

            // Xét Game Over (Bài 4)
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Rớt chạm đất
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    // Hàm kiểm tra va chạm bằng Box Collider
    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   
               a.x + a.width > b.x &&   
               a.y < b.y + b.height &&  
               a.y + a.height > b.y;    
    }

    // Được gọi liên tục bởi Game Loop Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint(); // Vẽ lại khung hình
        } else {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    // Bắt sự kiện bàn phím
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                // TÍNH NĂNG RESTART
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipeTimer.start();
            } else {
                // Nhảy lên (Bài 2)
                velocityY = -10; 
            }
        }
    }

    // Các phương thức thừa kế nhưng không sử dụng
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // ==========================================
    // KHỞI CHẠY GIAO DIỆN CỬA SỔ THEO YÊU CẦU BÀI 1
    // ==========================================
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");       // Tên cửa sổ
        FlappyBird flappyBirdPanel = new FlappyBird();
        
        frame.add(flappyBirdPanel);
        frame.pack();
        frame.setSize(360, 640);                        // Kích thước 360x640
        frame.setLocationRelativeTo(null);              // Canh giữa màn hình
        frame.setResizable(false);                      // Không thể thay đổi kích thước
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}