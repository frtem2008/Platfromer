//Version 3.0

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static final int a = 10000;
    public static final int b = -10000;
    public static final Platform learn1 = new Platform(-12800, 10600, 1200, 50);
    public static final Platform learn2 = new Platform(-11400, 10600, 300, 50);
    public static final Platform learn3 = new Platform(-10880, 10600, 1400, 50);
    public static final Platform learn4 = new Platform(-9160, 10600, 700, 50, true);
    public static final Platform learn5 = new Platform(-8200, 11000, 700, 50);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static boolean JAR = true;
    public static String LEARN, NAME, RESTORE;
    public static int fonOffset = 0;
    public static int fonX = 0;
    public static int openedWindows = 0;
    public static boolean restore = false;
    public static int frameCounter = 1;
    public static int backgrNum = 1;
    public static String endMessage = "";
    public static int playerSkin = 3;
    //
    public static Object[] level;
    public static boolean isHardcore = false;
    public static boolean Textures3d = false;
    //
    public static Platform first;
    public static Platform firstJump = new Platform(-100, -430, 50, 50);
    public static boolean begScene = true;
    public static Platform[] platforms = new Platform[1];
    public static Barrier[] barriers = new Barrier[0];
    public static Barrier[] platformBarriers;
    public static Arrow[] arrows = new Arrow[1];
    public static MovingPlatform[] movingPlatforms = new MovingPlatform[1];
    public static Player player = new Player();
    public static Bird[] birds = new Bird[20];
    public static File console = new File("resources/console.txt");

    static {
        Scanner rest = null;
        try {
            rest = new Scanner(new File("resources/Levels/restore.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert rest != null;
        String res = rest.nextLine();
        restore = res.equals("true");
        Scanner n = null;
        try {
            n = new Scanner(new File("resources/Levels/name.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert n != null;
        String name = n.nextLine();
        if (restore) {
            level = restoreLevel(name);
        } else {
            level = Generator.generateLevel(-1);
        }
        platforms[0] = (Platform) level[0];
        for (int i = 0; i < level.length; i++) {
            if (level[i] instanceof Platform) {
                platforms = addAll((Platform) level[i]);
            } else if (level[i] instanceof Arrow) {
                arrows = addAll((Arrow) level[i]);
            } else if (level[i] instanceof Barrier) {
                barriers = addAll((Barrier) level[i]);
            } else if (level[i] instanceof MovingPlatform) {
                movingPlatforms = addAll((MovingPlatform) level[i]);
            }
        }
        movingPlatforms[0] = new MovingPlatform(new Platform(0, 0, 0, 0, false), 0, 0, 0, false, 0, 0, 0, false);
        arrows[0] = new Arrow(0, 0, 0, 0);
        platformBarriers = new Barrier[platforms.length * 2];
        player = new Player();
        birds[0] = new Bird(3000, 80);
        int lastBird = 3000;
        for (int i = 1; i < birds.length; i++) {
            birds[i] = new Bird(lastBird + Math.random() * 1000 + 200, Math.random() * 80 + 50);
            lastBird = (int) birds[i].x;
        }
    }

    public Image Birdd;
    public ImageIcon birdLeft1;
    public ImageIcon birdLeft2;
    public ImageIcon birdLeft3;
    public ImageIcon birdLeft4;
    public ImageIcon birdLeft5;
    public ImageIcon birdLeft6;
    public ImageIcon birdLeft7;
    public ImageIcon birdLeft8;
    public ImageIcon birdLeft9;
    public ImageIcon birdLeft10;
    public ImageIcon eye;
    public ImageIcon emptyPlayer;
    public ImageIcon PLAYER2;
    public ImageIcon fon1;
    public ImageIcon fon2;
    public ImageIcon fon3;
    public ImageIcon fon4;
    public ImageIcon pl1;
    public ImageIcon pl2;
    public ImageIcon pl3;
    public ImageIcon pl4;

    JFrame frame;
    private Image platform;
    private Image arrow;
    private Image fon;
    private Image PLAYER;

    public Main() {
    }

    public static double toScreenX(double x) {
        return (double) (Display.frame.getWidth() / 2) + (x - player.getX());
    }

    public static double toScreenY(double y) {
        return (double) (Display.frame.getHeight() / 2) + (y - player.getY());
    }

    public static void fillBarriers() {
        for (int i = 0; i < platformBarriers.length; ++i) {
            Platform cur;
            if (i % 2 == 0) {
                cur = platforms[i / 2];
                platformBarriers[i] = new Barrier(cur.getPlatformX(), cur.getPlatformY() + 10.0D, cur.getPlatformY() + cur.getHeight() - 10.0D);
            } else {
                cur = platforms[(i - 1) / 2];
                platformBarriers[i] = new Barrier(cur.getPlatformX() + cur.getWidth(), cur.getPlatformY() + 10.0D, cur.getPlatformY() + cur.getHeight() - 10.0D);
            }
        }

    }

    public static Platform[] addAll(Platform... a) {
        ArrayList<Platform> pl = new ArrayList<>(Arrays.asList(platforms));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Collections.singletonList(a[i]));
        }
        Platform[] platforms = pl.toArray(new Platform[pl.size()]);
        return platforms;
    }

    public static MovingPlatform[] addAll(MovingPlatform... a) {
        ArrayList<MovingPlatform> pl = new ArrayList<>(Arrays.asList(movingPlatforms));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        MovingPlatform[] platforms = pl.toArray(new MovingPlatform[pl.size()]);
        return platforms;
    }

    public static Arrow[] addAll(Arrow... a) {
        ArrayList<Arrow> pl = new ArrayList<>(Arrays.asList(arrows));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Arrow[] platforms = pl.toArray(new Arrow[pl.size()]);
        return platforms;
    }

    public static Barrier[] addAll(Barrier... a) {
        ArrayList<Barrier> pl = new ArrayList<>(Arrays.asList(barriers));
        for (int i = 0; i < a.length; i++) {
            pl.addAll(Arrays.asList(a[i]));
        }
        Barrier[] Barriers = pl.toArray(new Barrier[pl.size()]);
        return Barriers;
    }

    public static void saveLevel(String name) {

        appendStrToFile("resources/Levels/Levels.txt", name + "\n");
        File level = new File("resources/Levels/" + name);
        level.mkdir();
        File Platforms = new File("resources/Levels/" + name + "/platforms.txt");
        File MovingPlatforms = new File("resources/Levels/" + name + "/movingPlatforms.txt");
        File Barriers = new File("resources/Levels/" + name + "/barriers.txt");
        File Arrows = new File("resources/Levels/" + name + "/arrows.txt");
        File playerData = new File("resources/Levels/" + name + "/player data.txt");

        try {
            Platforms.createNewFile();
            MovingPlatforms.createNewFile();
            Barriers.createNewFile();
            Arrows.createNewFile();
            playerData.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearFile("resources/Levels/" + name + "/platforms.txt");
        clearFile("resources/Levels/" + name + "/barriers.txt");
        clearFile("resources/Levels/" + name + "/arrows.txt");
        clearFile("resources/Levels/" + name + "/movingPlatforms.txt");
        clearFile("resources/Levels/" + name + "/player data.txt");
        for (int i = 0; i < platforms.length; i++) {
            Platform cur = platforms[i];
            appendStrToFile("resources/Levels/" + name + "/platforms.txt", cur.getPlatformX() + "~" + cur.getPlatformY() + "~" + cur.getWidth() + "~" + cur.getHeight() + "~" + cur.isSpawnpoint() + "\n");
        }
        for (int i = 0; i < movingPlatforms.length; i++) {
            MovingPlatform cur = movingPlatforms[i];
            appendStrToFile("resources/Levels/" + name + "/movingPlatforms.txt", cur.platform.getPlatformX() + "~" + cur.platform.getPlatformY() + "~" + cur.platform.getWidth() + "~" + cur.platform.getHeight() + "~" + cur.platform.isSpawnpoint() + "~" + cur.x1 + "~" + cur.x2 + "~" + cur.defaultXSpeed / 2 + "~" + cur.XDirection + "~" + cur.y1 + "~" + cur.y2 + "~" + cur.defaultYSpeed / 2 + "~" + cur.YDirection + "\n");
        }
        for (int i = 0; i < arrows.length; i++) {
            Arrow cur = arrows[i];
            appendStrToFile("resources/Levels/" + name + "/arrows.txt", cur.getArrowX() + "~" + cur.getArrowX() + "~" + cur.getLength() + "~" + cur.getHeight() + "\n");
        }
        for (int i = 0; i < barriers.length; i++) {
            Barrier cur = barriers[i];
            appendStrToFile("resources/Levels/" + name + "/barriers.txt", cur.getX() + "~" + cur.getY1() + "~" + cur.getY2() + "\n");
        }
        appendStrToFile("resources/Levels/" + name + "/player data.txt", Player.x + "~" + Player.y);
    }

    public static Object[] restoreLevel(String name) {
        Scanner platformReader = null, barrierReader = null, arrowReader = null, playerDataReader = null, movingReader = null;
        File Platforms = new File("resources/Levels/" + name + "/platforms.txt");
        File Barriers = new File("resources/Levels/" + name + "/barriers.txt");
        File Arrows = new File("resources/Levels/" + name + "/arrows.txt");
        File MovingPlatforms = new File("resources/Levels/" + name + "/movingPlatforms.txt");
        File playerData = new File("resources/Levels/" + name + "/player data.txt");
        try {
            platformReader = new Scanner(Platforms);
            barrierReader = new Scanner(Barriers);
            arrowReader = new Scanner(Arrows);
            movingReader = new Scanner(MovingPlatforms);
            playerDataReader = new Scanner(playerData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Platform> platformS = new ArrayList<>();
        ArrayList<Arrow> arrowS = new ArrayList<>();
        ArrayList<Barrier> barrierS = new ArrayList<>();
        ArrayList<MovingPlatform> movingPlatformS = new ArrayList<>();
        String plData = playerDataReader.nextLine();
        String[] plDataSplit = plData.split("~");
        double plX, plY;
        plX = Double.parseDouble(plDataSplit[0]);
        plY = Double.parseDouble(plDataSplit[1]);
        Player.SetSpawnpoint(plX, plY);

        while (platformReader.hasNextLine()) {
            String data = platformReader.nextLine();
            String[] dataSplit = data.split("~");
            int x, y, w, h;
            boolean isSpawnpoint;
            x = (int) Double.parseDouble(dataSplit[0]);
            y = (int) Double.parseDouble(dataSplit[1]);
            w = (int) Double.parseDouble(dataSplit[2]);
            h = (int) Double.parseDouble(dataSplit[3]);
            isSpawnpoint = Boolean.parseBoolean(dataSplit[4]);
            Platform pl = new Platform(x, y, w, h, isSpawnpoint);
            platformS.add(pl);
        }
        while (movingReader.hasNextLine()) {
            String data = movingReader.nextLine();
            String[] dataSplit = data.split("~");
            int x, y, w, h, x1, x2, y1, y2;
            double XSpeed, YSpeed;
            boolean isSpawnpoint, XDirection, YDirection;
            x = (int) Double.parseDouble(dataSplit[0]);
            y = (int) Double.parseDouble(dataSplit[1]);
            w = (int) Double.parseDouble(dataSplit[2]);
            h = (int) Double.parseDouble(dataSplit[3]);
            isSpawnpoint = Boolean.parseBoolean(dataSplit[4]);
            x1 = (int) Double.parseDouble(dataSplit[5]);
            x2 = (int) Double.parseDouble(dataSplit[6]);
            XSpeed = Double.parseDouble(dataSplit[7]);
            XDirection = Boolean.parseBoolean(dataSplit[8]);
            y1 = (int) Double.parseDouble(dataSplit[9]);
            y2 = (int) Double.parseDouble(dataSplit[10]);
            YSpeed = Double.parseDouble(dataSplit[11]);
            YDirection = Boolean.parseBoolean(dataSplit[12]);
            Platform a = new Platform(x, y, w, h, isSpawnpoint);
            MovingPlatform pl = new MovingPlatform(a, x1, x2, XSpeed, XDirection, y1, y2, YSpeed, YDirection);

            movingPlatformS.add(pl);
        }
        while (arrowReader.hasNextLine()) {
            String data = arrowReader.nextLine();
            String[] dataSplit = data.split("~");
            int x, y, w, h;
            x = (int) Double.parseDouble(dataSplit[0]);
            y = (int) Double.parseDouble(dataSplit[1]);
            w = (int) Double.parseDouble(dataSplit[2]);
            h = (int) Double.parseDouble(dataSplit[3]);
            Arrow ar = new Arrow(x, y, w, h);
            arrowS.add(ar);
        }

        while (barrierReader.hasNextLine()) {
            String data = barrierReader.nextLine();
            String[] dataSplit = data.split("~");
            int x, y1, y2;
            x = (int) Double.parseDouble(dataSplit[0]);
            y1 = (int) Double.parseDouble(dataSplit[1]);
            y2 = (int) Double.parseDouble(dataSplit[2]);
            Barrier bar = new Barrier(x, y1, y2);
            barrierS.add(bar);
        }
        ArrayList<Object> levels = new ArrayList<>();
        levels.addAll(platformS);
        levels.addAll(barrierS);
        levels.addAll(arrowS);
        levels.addAll(movingPlatformS);
        Object[] level = levels.toArray();
        platforms = new Platform[platforms.length];
        arrows = new Arrow[arrows.length];
        barriers = new Barrier[barriers.length];
        movingPlatforms = new MovingPlatform[movingPlatforms.length];

        platforms[0] = (Platform) level[0];
        for (int i = 0; i < level.length; i++) {
            if (level[i] instanceof Platform) {
                platforms = addAll((Platform) level[i]);
            } else if (level[i] instanceof Arrow) {
                arrows = addAll((Arrow) level[i]);
            } else if (level[i] instanceof Barrier) {
                barriers = addAll((Barrier) level[i]);
            } else if (level[i] instanceof MovingPlatform) {
                movingPlatforms = addAll((MovingPlatform) level[i]);
            }
        }
        clearFile("resources/Levels/restore.txt");
        appendStrToFile("resources/Levels/restore.txt", "true");
        clearFile("resources/Levels/name.txt");
        appendStrToFile("resources/Levels/name.txt", name);
        System.out.println("level " + name + " restored");
        return level;
    }

    public static void appendStrToFile(String fileName, String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(str);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }

    public static void clearFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
            out.write("");
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    if (!Files.isSymbolicLink(f.toPath())) {
                        deleteFile(f.getPath());
                    }
                }
            }
            file.delete();
        }
    }

    public void startDrawing(JFrame frame) throws InterruptedException {
        int fps = 60;
        Scanner b = new Scanner("resources/Levels/name.txt");
        RESTORE = restore + "";
        NAME = b.nextLine();
        if (NAME.equals("learn") && RESTORE.equals("true")) {
            LEARN = "true";
        } else {
            LEARN = "false";
        }
        File levelList = new File("resources/Levels");
        String[] Levels = levelList.list();
        clearFile("resources/Levels/Levels.txt");
        for (int i = 0; i < (Levels != null ? Levels.length : 0); i++) {
            if (!Levels[i].contains(".txt"))
                appendStrToFile("resources/Levels/Levels.txt", Levels[i] + "\n");
        }
        AudioThread music = new AudioThread();
        Thread musicPLayer = new Thread(music);
        musicPLayer.start();
        this.frame = frame;
        frame.createBufferStrategy(2);
        this.loadImages();

        while (true) {
            frameCounter++;
            if (frameCounter >= fps) {
                frameCounter = 1;
            }
            long frameLength = 1000 / fps;
            long start = System.currentTimeMillis();
            BufferStrategy bs = frame.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
            this.updateImages();
            this.draw(g);

            Player.keyboard.update();
            player.move();
            Bird.move(birds);
            bs.show();
            g.dispose();
            long end = System.currentTimeMillis();
            long len = end - start;

            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }

            Keyboard keyboard = Player.keyboard;
            frame.addKeyListener(keyboard);
            if (keyboard.getB() && keyboard.getCtrl()) {
                if (Textures3d) {
                    Textures3d = false;
                } else {
                    Textures3d = true;
                }
                updateImages();
            }
            if (keyboard.getS() && keyboard.getCtrl()) {
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();
                String real = time.toString().substring(0, time.toString().indexOf(".") - 1);
                real = real.replaceAll(":", "_");
                File screenShot = new File("resources/screenshots/" + date + " " + real + ".png");
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                assert robot != null;
                BufferedImage bi = robot.createScreenCapture(new java.awt.Rectangle(1600, 900));
                try {
                    ImageIO.write(bi, "png", new File("resources/screenshots/" + date + real + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (keyboard.getM() && keyboard.getShift() && keyboard.getCtrl()) {
                String name = JOptionPane.showInputDialog(frame, "Введите название уровня для сохранения");
                saveLevel(name);
                player.move();
                Player.keyboard.update();
                System.out.println("level saved as " + name);

            }

            if (keyboard.getN() && keyboard.getShift() && keyboard.getCtrl()) {
                /*String name = JOptionPane.showInputDialog(frame, "Введите название уровня для восстановления");
                level = restoreLevel(name);
                System.out.println("restore mode on");
                System.exit(10);*/
                if (openedWindows == 0) {
                    openedWindows++;
                    EventQueue.invokeLater(() -> {
                        ComboBoxFrame jframe = new ComboBoxFrame();
                        jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        jframe.setVisible(true);
                    });
                }

            }
            if (keyboard.getU() && keyboard.getShift() && keyboard.getCtrl()) {
                clearFile("resources/Levels/restore.txt");
                appendStrToFile("resources/Levels/restore.txt", "false");
            }
        }
    }

    public void draw(Graphics g) {
        if (Player.keyboard.getH()) {
            System.out.println("hgjjvbrjkbv");
            g.setColor(Color.white);
            g.drawRect(0, 0, 1600, 900);
            g.setColor(Color.blue);
            Font myFont = new Font("new Font", Font.BOLD, 25);
            drawCenteredString(g, "Controls help", new Rectangle(1600, 100), new Font("new Font", Font.BOLD, 40));
            drawCenteredString(g, "save level: ctrl + shift + m", new Rectangle(1600, 200), myFont);
            drawCenteredString(g, "restore level: ctrl + shift + n", new Rectangle(1600, 250), myFont);
            drawCenteredString(g, "respawn on last spawnpoint: v", new Rectangle(1600, 300), myFont);
            drawCenteredString(g, "exit program: q", new Rectangle(1600, 350), myFont);
            drawCenteredString(g, "get screenshot: ctrl + s (in resources\\screenshots)", new Rectangle(1600, 400), myFont);
            drawCenteredString(g, "switch to 3d textures: ctrl + b", new Rectangle(1600, 450), myFont);
            drawCenteredString(g, "new level will be random: ctrl + shift + u", new Rectangle(1600, 500), myFont);
            drawCenteredString(g, "clear chat: x", new Rectangle(1600, 550), myFont);
            drawCenteredString(g, "delete previous spawnpoint: c", new Rectangle(1600, 600), myFont);
            g.setColor(new Color(59, 12, 134, 255));
            drawCenteredString(g, "all songs:", new Rectangle(1600, 650), myFont);
            drawCenteredString(g, "stay inside me by ocularNebula", new Rectangle(1600, 700), myFont);
            drawCenteredString(g, "deadlocked by F777", new Rectangle(1600, 750), myFont);
            drawCenteredString(g, "the seven seas by F777", new Rectangle(1600, 800), myFont);
            drawCenteredString(g, "airborne robots by F777", new Rectangle(1600, 850), myFont);
            drawCenteredString(g, "back on track by DJVI", new Rectangle(1600, 900), myFont);
            drawCenteredString(g, "base after base by DJVI", new Rectangle(1600, 950), myFont);
            drawCenteredString(g, "club step by DJNATE", new Rectangle(1600, 1000), myFont);
            drawCenteredString(g, "cycles by DJVI", new Rectangle(1600, 1050), myFont);
            g.setColor(new Color(184, 146, 8, 255));
            drawCenteredString(g, "HAVE FUN!", new Rectangle(1600, 1600), new Font("new Font", Font.BOLD, 75));

            g.setFont(myFont);
            g.setColor(new Color(0, 128, 255, 255));
            g.drawString("frtem 2020-2021 all rights reserved", 50, 800);
        } else {
            JProgressBar jpb = new JProgressBar();
            jpb.setMinimum(0);
            jpb.setMaximum(100);
            jpb.setStringPainted(true);
            jpb.setBounds(500, 100, Display.frame.getWidth(), 50);
            Platform f = (Platform) Main.level[0];
            Platform last = Main.platforms[Main.platforms.length - 1];
            int length = (int) (last.getPlatformX() + last.getWidth() - f.getWidth() - f.getPlatformX());
            jpb.setValue((int) (player.getX() / length * 100));
            Scanner sa = null;
            try {
                sa = new Scanner(new File("resources/Levels/name.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (!(sa.nextLine().equals("learn") && restore)) {
                if (jpb.getValue() >= 0 && jpb.getValue() < 25) {
                    backgrNum = 1;
                } else if (jpb.getValue() > 25 && jpb.getValue() < 50) {
                    backgrNum = 2;
                } else if (jpb.getValue() > 50 && jpb.getValue() < 75) {
                    backgrNum = 3;
                } else if (jpb.getValue() > 75 && jpb.getValue() < 100) {
                    backgrNum = 4;
                }
            } else {
                backgrNum = 5;
            }
            if (jpb.getValue() % 25 == 0) {
                g.setColor(Color.black);
                g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            }
            try {
                Scanner s = new Scanner(new File("resources/Levels/name.txt"));
                if (s.nextLine().equals("learn") && restore) {
                    g.drawImage(this.fon, 0, 0, Display.frame.getWidth(), Display.frame.getHeight(), null);
                } else {
                    fonOffset = -3200;
                    double fonSpeedDiv = 10d;
                    g.drawImage(this.fon, (int) toScreenX(fonX), 0, Display.frame.getWidth(), Display.frame.getHeight(), null);
                    for (int i = 0; i < 100 / fonSpeedDiv; i++) {
                        g.drawImage(this.fon, (int) ((int) toScreenX(fonX + fonOffset) / fonSpeedDiv), 0, Display.frame.getWidth(), Display.frame.getHeight(), null);
                        fonOffset += 1600 * fonSpeedDiv;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            frame.add(jpb);
            jpb.update(g);
            jpb.setVisible(true);

            for (int i = 0; i < barriers.length; ++i) {
                Barrier Barrier = barriers[i];
                g.setColor(Color.red);
                g.drawLine((int) toScreenX(Barrier.getX()), (int) toScreenY(Barrier.getY1()), (int) toScreenX(Barrier.getX()), (int) toScreenY(Barrier.getY2()));
            }
            first = new Platform(-1000, -350, 900, 50);
            g.drawImage(platform, (int) toScreenX(first.getPlatformX()), (int) toScreenY(first.getPlatformY()), (int) first.getWidth(), 50, null);
            for (int i = 0; i < platforms.length; i++) {
                Platform Platform1 = platforms[i];
                if (Math.sqrt(Math.pow(Platform1.getPlatformX() + Platform1.getWidth() / 2 - player.getX(), 2) + Math.pow(Platform1.getPlatformY() - player.getY(), 2)) <= 2000) {
                    g.drawImage(this.platform, (int) toScreenX(Platform1.getPlatformX()), (int) toScreenY(Platform1.getPlatformY()), (int) Platform1.getWidth(), (int) Platform1.getHeight(), null);
                }
            }
            for (int i = 0; i < movingPlatforms.length; i++) {
                Platform Platform1 = movingPlatforms[i].platform;
                if (Math.sqrt(Math.pow(Platform1.getPlatformX() + Platform1.getWidth() / 2 - player.getX(), 2) + Math.pow(Platform1.getPlatformY() - player.getY(), 2)) <= 2000)
                    g.drawImage(this.platform, (int) toScreenX(Platform1.getPlatformX()), (int) toScreenY(Platform1.getPlatformY()), (int) Platform1.getWidth(), (int) Platform1.getHeight(), null);

            }

            for (int i = 0; i < arrows.length; ++i) {
                Arrow arrow = arrows[i];
                g.drawImage(this.arrow, (int) toScreenX(arrow.getArrowX()), (int) toScreenY(arrow.getArrowY()), arrow.getLength(), arrow.getHeight(), null);
            }
            g.setColor(Color.BLACK);
            //g.fillOval((int) toScreenX(player.getX()), (int) toScreenY(player.getY()), (int) player.getSize(), (int) player.getSize());
            g.drawImage(PLAYER, (int) toScreenX(player.getX()), (int) toScreenY(player.getY()), (int) player.getSize(), (int) player.getSize(), null);
            int x1 = (int) toScreenX(player.getX());
            int y1 = (int) toScreenY(player.getY());
            int x2 = x1;
            int y2 = y1;
            int size = (int) player.getSize() / 8;
            if (playerSkin == 3) {
                x1 += 16;
                x2 += 44;
                y1 += 12;
                y2 += 12;
            } else if (playerSkin == 4) {
                x1 += 16;
                x2 += 44;
                y1 += 16;
                y2 += 16;
            } else if (playerSkin == 5) {
                x1 += 12;
                x2 += 40;
                y1 += 12;
                y2 += 12;
            } else if (playerSkin == 6) {
                x1 += 12;
                x2 += 40;
                y1 += 16;
                y2 += 16;
            }

            g.drawImage(eye.getImage(), x1, y1, size, size, null);
            g.drawImage(eye.getImage(), x2, y2, size, size, null);
            g.setColor(Color.red);
            g.setFont(new Font("bruh", Font.BOLD, 24));
            g.drawString(Player.ohh, (int) toScreenX(player.getX()), (int) toScreenY(player.getY() + player.getSize() + 10.0D));
            double h = Player.fallingHeight;
            if (h <= 100.0D) {
                g.setColor(new Color(2127910));
            } else if (100.0D < h && h <= 400.0D) {
                g.setColor(new Color(16754722));
            } else if (h > 400.0D) {
                g.setColor(Color.red);
            }
            for (int i = 0; i < birds.length; i++) {
                Bird bird = birds[i];
                g.drawImage(Birdd, (int) bird.x, (int) bird.y, 600, 300, null);
            }
            g.drawString(String.valueOf((int) Player.fallingHeight), 100, 100);
            g.setColor(Color.red);
            g.drawString("Deaths: " + Player.deaths, 100, 150);
            g.setColor(new Color(50, 120, 170));
            g.setColor(new Color(3178108));
            String[] chat = Player.chat;

            for (int i = 0; i < Player.chatLast; ++i) {
                g.drawString(chat[i], 15, 350 + i * 50);
            }

            g.setColor(new Color(0, 128, 255, 255));
            g.drawString("To go left/right use a/d or left arrow/ right arrow", (int) toScreenX(-12850.0D), (int) toScreenY(10400.0D));
            g.drawString("To jump use w/space/up arrow", (int) toScreenX(-12250.0D), (int) toScreenY(10400.0D));
            g.drawString("Jump here", (int) toScreenX(-11695.0D), (int) toScreenY(10540.0D));
            g.drawString("And here", (int) toScreenX(-11195.0D), (int) toScreenY(10540.0D));
            g.drawString("This red line is a barrier", (int) toScreenX(-10680.0D), (int) toScreenY(10300.0D));
            g.drawString("You can't go through it", (int) toScreenX(-10680.0D), (int) toScreenY(10350.0D));
            g.drawString("but if you press left when you jump from the left side", (int) toScreenX(-10880.0D), (int) toScreenY(10400.0D));
            g.drawString("you can push off it", (int) toScreenX(-10680.0D), (int) toScreenY(10450.0D));
            g.drawString("Jump", (int) toScreenX(-10000.0D), (int) toScreenY(10540.0D));
            g.drawString("Press left", (int) toScreenX(-9925.0D), (int) toScreenY(10428.0D));
            g.drawString("Go --->", (int) toScreenX(-9900.0D), (int) toScreenY(10560.0D));
            g.drawString("Jump", (int) toScreenX(-9504.0D), (int) toScreenY(10560.0D));
            g.drawString("This is the arrow", (int) toScreenX(-9404.0D), (int) toScreenY(10400.0D));
            g.drawString("It doubles your speed", (int) toScreenX(-9404.0D), (int) toScreenY(10450.0D));
            g.drawString("So you can jump longer", (int) toScreenX(-9100.0D), (int) toScreenY(10450.0D));
            g.drawString("Jump", (int) toScreenX(-8854.0D), (int) toScreenY(10560.0D));
            g.drawString("Jump", (int) toScreenX(-7970.0D), (int) toScreenY(10535.0D));
            g.drawString("You're on the edge of the platform", (int) toScreenX(-7650.0D), (int) toScreenY(10400.0D));
            g.drawString("You can push off it as a barrier ", (int) toScreenX(-7650.0D), (int) toScreenY(10450.0D));
            g.setColor(new Color(46, 26, 115));
            drawCenteredString(g, endMessage, new java.awt.Rectangle(frame.getWidth(), frame.getHeight()), new Font("bruh", Font.BOLD, 50));
        }
    }

    public void drawCenteredString(Graphics g, String text, java.awt.Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }


    public void updateImages() {
        this.PLAYER = emptyPlayer.getImage();
        if (playerSkin == 1) {
            this.PLAYER = PLAYER2.getImage();
        }
        if (backgrNum == 1) {
            this.fon = fon1.getImage();
            this.platform = pl1.getImage();
        } else if (backgrNum == 2) {
            this.fon = fon2.getImage();
            this.platform = pl2.getImage();
        } else if (backgrNum == 3) {
            this.fon = fon3.getImage();
            this.platform = pl3.getImage();
        } else if (backgrNum == 4) {
            this.fon = fon4.getImage();
            this.platform = pl4.getImage();
        } else if (backgrNum == 5) {
            ImageIcon sunset2 = new ImageIcon("resources/Images/закат 2.jpg");
            this.fon = sunset2.getImage();
            this.platform = new ImageIcon("resources/Images/Platform 7.png").getImage();
        }
        if (Textures3d) {
            pl1 = new ImageIcon("resources/Images/Platform52.png");
            pl2 = new ImageIcon("resources/Images/Platform62.png");
            pl3 = new ImageIcon("resources/Images/Platform 32.png");
            pl4 = new ImageIcon("resources/Images/Platform 82.png");
        } else {
            pl1 = new ImageIcon("resources/Images/Platform5.png");
            pl2 = new ImageIcon("resources/Images/Platform 6.png");
            pl3 = new ImageIcon("resources/Images/Platform 3.png");
            pl4 = new ImageIcon("resources/Images/Platform 8.png");
        }
        if (frameCounter >= 0 && frameCounter <= 6) {
            Birdd = birdLeft1.getImage();
        } else if (frameCounter > 6 && frameCounter <= 12) {
            Birdd = birdLeft2.getImage();
        } else if (frameCounter > 12 && frameCounter <= 18) {
            Birdd = birdLeft3.getImage();
        } else if (frameCounter > 18 && frameCounter <= 24) {
            Birdd = birdLeft4.getImage();
        } else if (frameCounter > 24 && frameCounter <= 30) {
            Birdd = birdLeft5.getImage();
        } else if (frameCounter > 30 && frameCounter <= 36) {
            Birdd = birdLeft6.getImage();
        } else if (frameCounter > 36 && frameCounter <= 42) {
            Birdd = birdLeft7.getImage();
        } else if (frameCounter > 42 && frameCounter <= 48) {
            Birdd = birdLeft8.getImage();
        } else if (frameCounter > 48 && frameCounter <= 54) {
            Birdd = birdLeft9.getImage();
        } else if (frameCounter > 54 && frameCounter <= 60) {
            Birdd = birdLeft10.getImage();
        }
    }

    public void loadImages() {
        if (Textures3d) {
            pl1 = new ImageIcon("resources/Images/Platform52.png");
            pl2 = new ImageIcon("resources/Images/Platform62.png");
            pl3 = new ImageIcon("resources/Images/Platform 32.png");
            pl4 = new ImageIcon("resources/Images/Platform 82.png");
        } else {
            pl1 = new ImageIcon("resources/Images/Platform5.png");
            pl2 = new ImageIcon("resources/Images/Platform 6.png");
            pl3 = new ImageIcon("resources/Images/Platform 3.png");
            pl4 = new ImageIcon("resources/Images/Platform 8.png");
        }
        fon1 = new ImageIcon("resources/Images/рассвет.png");
        fon2 = new ImageIcon("resources/Images/день.jpg");
        fon3 = new ImageIcon("resources/Images/закат.jpg");
        fon4 = new ImageIcon("resources/Images/ночь.png");
        birdLeft1 = new ImageIcon("resources/Images/bird left 1.png");
        birdLeft2 = new ImageIcon("resources/Images/bird left 2.png");
        birdLeft3 = new ImageIcon("resources/Images/bird left 3.png");
        birdLeft4 = new ImageIcon("resources/Images/bird left 4.png");
        birdLeft5 = new ImageIcon("resources/Images/bird left 5.png");
        birdLeft6 = new ImageIcon("resources/Images/bird left 6.png");
        birdLeft7 = new ImageIcon("resources/Images/bird left 7.png");
        birdLeft8 = new ImageIcon("resources/Images/bird left 8.png");
        birdLeft9 = new ImageIcon("resources/Images/bird left 9.png");
        birdLeft10 = new ImageIcon("resources/Images/bird left 10.png");
        ImageIcon ar = new ImageIcon("resources/Images/Arrow.png");
        eye = new ImageIcon("resources/Images/eye.png");
        emptyPlayer = new ImageIcon("resources/Images/player.png");
        this.arrow = ar.getImage();
    }

    static class AudioThread implements Runnable {
        @Override
        public void run() {
            Scanner s = null;
            try {
                s = new Scanner(new File("resources/Levels/name.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Audio track = null;
            assert s != null;
            if (s.nextLine().equals("learn") && restore) {
                track = new Audio("practice mode", 1.0, 85000);
            } else {
                double a = Math.random() * 0.7;
                if (a <= 0.1) {
                    track = new Audio("AirborneRobots", 1.0, 85000);
                } else if (a > 0.1 && a <= 0.2) {
                    track = new Audio("BackOnTrack", 1.0, 85000);
                } else if (a > 0.2 && a <= 0.3) {
                    track = new Audio("BaseAfterBase", 1.0, 85000);
                } else if (a > 0.3 && a <= 0.4) {
                    track = new Audio("Clubstep", 1.0, 85000);
                } else if (a > 0.4 && a <= 0.5) {
                    track = new Audio("Cycles", 1.0, 85000);
                } else if (a > 0.5 && a <= 0.6) {
                    track = new Audio("Deadlocked", 1.0, 85000);
                } else if (a > 0.6 && a <= 0.7) {
                    track = new Audio("The7Seas", 1.0, 85000);
                }
            }
            assert track != null;
            track.sound();
            track.setVolume();
            track.play();

            while (true) {
                track.repeat();
            }
        }
    }

    static class ComboBoxFrame extends JFrame {
        public static final int DEFAULT_WIDTH = 300;
        public static final int DEFAULT_HEIGHT = 300;
        private static final int DEFAULT_SIZE = 12;
        private final JComboBox<String> levels;

        public ComboBoxFrame() {
            setTitle("level selection");
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            levels = new JComboBox<>();
            levels.setEditable(false);
            Scanner s = null;
            try {
                s = new Scanner(new File("resources/Levels/Levels.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (s.hasNextLine()) {
                levels.addItem(s.nextLine());
            }
            levels.addItem("new lvl");
            levels.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String name = (String) levels.getSelectedItem();
                    if (name.equals("new lvl")) {
                        level = Generator.generateLevel(-1);
                        restore = false;
                        clearFile("resources/Levels/restore.txt");
                        appendStrToFile("resources/Levels/restore.txt", "false");
                    } else {
                        level = restoreLevel(name);
                    }
                    System.out.println("Restart to restore");
                    System.exit(10);
                }
            });
            JPanel comboPanel = new JPanel();
            comboPanel.add(levels);
            add(comboPanel, BorderLayout.CENTER);
        }
    }

}
