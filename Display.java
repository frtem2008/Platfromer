import javax.swing.*;
import java.net.URL;

public class Display extends JFrame {   
    static {
        System.setProperty("sun.java2d.opengl", "True");
    }
    public static JFrame frame = new JFrame("Platformer");
    public static boolean isFullScreen = true;
    public static int w = 1000;
    public static int h = 800;
    public static void main(String[] args) throws InterruptedException {
        //Downloader.fileCheck();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Display.frame.setExtendedState(6);

        ImageIcon img = new ImageIcon("resources/Images/Icon.png");
        frame.setIconImage(img.getImage());
        frame.setUndecorated(true);
        frame.setVisible(true);
        Player.spawnpointsX.add(Player.spawnX);
        Player.spawnpointsX.add(Player.spawnX);
        Player.spawnpointsY.add(Player.spawnY);
        Player.spawnpointsY.add(Player.spawnY);
        Main.fillBarriers();
        Main m = new Main();
        //Main.saveLevel("bruh", Main.level);
        m.startDrawing(frame);
    }

    protected static ImageIcon createIcon(String path) {
        URL imgURL = Display.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + path);
            return null;
        }
    }
}
