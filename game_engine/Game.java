import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.xml.crypto.Data;

public class Game extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    public ArrayList<Texture> textures;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public static int[][] map = {
        {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
        {1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
        {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
        {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
        {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
        {1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
        {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
        {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}   
    };


    public static void main(String [] args) {
        Game game = new Game();
    }

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
        setSize(640, 480);
        setResizable(false);
        setTitle("Sample 3D Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        }   catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        } 
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }

    public void run(){
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running){
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;
            while(delta >= 1){
                delta--;
            }
            render();
        }
    }

}