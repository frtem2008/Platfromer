import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TimerTask;

public class Player {
    private static int a = 0;
    private static final Platform[] platforms;
    private static final MovingPlatform[] movingPlatforms;
    private static final Barrier[] barriers;
    private static final double size;
    public static int deaths;
    public static boolean godMode;
    public static Keyboard keyboard;
    public static int maxSpeed;
    public static double fallingHeight;
    public static String ohh;
    public static double spawnX = -1000;
    public static double spawnY = -400;
    public static double x = spawnX;
    public static double y = spawnY;
    public static ArrayList<Double> spawnpointsX;
    public static ArrayList<Double> spawnpointsY;
    public static String[] chat;
    public static int chatLast;
    public static int bonusCount = 0;
    public static double speed;
    public static double begin, end;
    public static int firstTime = 0;
    public static double gravitation;
    public static boolean jump;
    private static double maxFallingHeight;
    private static int frame;
    private static boolean onBarrier;
    private static boolean fromRight;
    private static boolean fromLeft;
    private static boolean fromUp;
    private static boolean fromDown;

    static {
        begin = System.currentTimeMillis();
        platforms = Main.platforms;
        movingPlatforms = Main.movingPlatforms;
        barriers = Main.barriers;
        deaths = 0;
        godMode = false;
        keyboard = new Keyboard();
        maxSpeed = 5;
        fallingHeight = 0.0D;
        ohh = "";
        spawnpointsX = new ArrayList<>();
        spawnpointsY = new ArrayList<>();
        chat = new String[6];
        chatLast = 0;
        size = 64.0D;
        gravitation = 0.0D;
        jump = true;
        speed = 80.0D;
        maxFallingHeight = 550.0D;
        frame = 1;
        onBarrier = false;
        fromRight = false;
        fromLeft = false;
        fromUp = false;
        fromDown = false;
    }

    public Player() {
    }

    public static void respawn() {
        speed = 0.0D;
        gravitation = 0.0D;
        fallingHeight = 0.0D;
        x = spawnX;
        y = spawnY;
        maxFallingHeight = 510.0D;
        maxSpeed = 5;
    }

    public static void jumpSound() {
      /*  Audio jump = new Audio("resources.Sounds/jump.wav", 1.0);
        jump.play();
        jump.setVolume();*/
    }

    public static double getSpawnX() {
        return spawnX;
    }

    public static double getSpawnY() {
        return spawnY;
    }

    public static void SetSpawnpoint(double X, double Y) {
        spawnX = X;
        spawnY = Y;
        x = spawnX;
        y = spawnY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public void move() {
        a = 0;
        keyboard.update();
        Main.fonX = (int) x / 100;
        if (chatLast >= chat.length) {
            for (int i = 1; i < chat.length; ++i) {
                chat[i] = chat[i - 1];
            }

            chatLast = 0;
        }

        if (!godMode) {
            if (speed > 0.0D) {
                fromRight = true;
                fromLeft = false;
            } else if (speed < 0.0D) {
                fromLeft = true;
                fromRight = false;
            }

            if (gravitation > 0.0D) {
                fromUp = true;
                fromDown = false;
            } else if (gravitation < 0.0D) {
                fromUp = false;
                fromDown = true;
            }

            if (frame % 60 != 0) {
                ++frame;
            } else {
                frame = 1;
            }

            if (MovingPlatform.Collides(Main.player, movingPlatforms) != -1) {
                MovingPlatform cur = movingPlatforms[MovingPlatform.Collides(Main.player, movingPlatforms)];
                Scanner s = null;
                try {
                    s = new Scanner(new File("resources/Levels/restore.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (s.nextLine().equals("true")) {
                    x += cur.XSpeed * 2;
                    y += cur.YSpeed * 2;
                } else {
                    x += cur.XSpeed;
                    y += cur.YSpeed;
                }
                jump = false;
                fallingHeight = 0.0D;
                gravitation = 0.0D;
                if (keyboard.getLeft() && !keyboard.getRight() && !jump) {
                    speed -= 2.0D;
                }

                if (!keyboard.getLeft() && keyboard.getRight() && !jump) {
                    speed += 2.0D;
                }

                if (keyboard.getUp()) {
                    double n = cur.XSpeed;
                    speed += n;
                }
                if (!keyboard.getLeft() && !keyboard.getRight()) {
                    if (speed > 0.0D) {
                        speed -= 1D;
                    } else if (speed < 0.0D) {
                        speed += 1D;
                    }
                }
                fromDown = true;
                fromUp = false;

            }
            if (Platform.Collides(Main.player, platforms) != -1) {
                jump = false;
                fallingHeight = 0.0D;
                gravitation = 0.0D;
                if (keyboard.getLeft() && !keyboard.getRight() && !jump) {
                    speed -= 2.0D;
                }

                if (!keyboard.getLeft() && keyboard.getRight() && !jump) {
                    speed += 2.0D;
                }
                if (!keyboard.getLeft() && !keyboard.getRight()) {
                    if (speed > 0.0D) {
                        speed -= 1D;
                    } else if (speed < 0.0D) {
                        speed += 1D;
                    }
                }
            }
            if (Barrier.Collides(Main.player, barriers) == -1 && Barrier.Collides(Main.player, Main.platformBarriers) == -1) {
                onBarrier = false;
            } else {
                onBarrier = true;
                speed = 0.0D;
                fallingHeight = 0.0D;
                if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                    jump = true;
                    speed = -5.0D;
                    jumpSound();
                    if (gravitation <= 7.0D) {
                        gravitation += -8.0D;
                    }
                }

                if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                    jump = true;
                    speed = 5.0D;
                    jumpSound();
                    if (gravitation <= 7.0D) {
                        gravitation += -8.0D;
                    }
                }
            }


            if ((Platform.Collides(Main.player, platforms) == -1)) {
                if (MovingPlatform.Collides(Main.player, movingPlatforms) == -1)
                    jump = true;
            } else {
                Main.begScene = false;
                int var10001;
                if (fallingHeight > maxFallingHeight) {
                    var10001 = (int) fallingHeight;
                    System.out.println("Вы упали с высоты " + var10001 + " и разбились (максимальная высота: " + maxFallingHeight + ")");
                    chat[chatLast] = "Player fell from a high place!!!";
                    ++chatLast;
                    respawn();
                    ++deaths;
                    if (Main.isHardcore) {
                        System.exit(50);
                    }
                }

                if (Platform.Collides(Main.player, platforms) != -1) {
                    fromUp = false;
                    fromDown = true;
                    Platform cur = platforms[Platform.Collides(Main.player, platforms)];
                    if (cur.isSpawnpoint()) {
                        this.setSpawnpoint(cur.getPlatformX() + cur.getWidth() / 2.0D, cur.getPlatformY() - size - 50.0D);
                    }
                }

                if (Platform.Collides(Main.player, platforms) == platforms.length - 1) {
                    if (firstTime == 0) {
                        firstTime++;
                        end = System.currentTimeMillis();
                        double ms = end - begin;
                        int sec = (int) (ms / 1000);
                        String minSec = String.format("%02d:%02d", sec / 60 % 60, sec % 60);
                        if (Main.LEARN.equals("false")) {
                            Main.clearFile("resources/Levels/restore.txt");
                            Main.appendStrToFile("resources/Levels/restore.txt", "false");
                            System.out.println("now go to a new rgenerated level");
                            Main.endMessage = "Restart the program \nto complete the real level";
                        } else {
                            Main.endMessage = "YOU'VE WON!!!\nTime: " + minSec;
                        }
                        java.util.Timer t = new java.util.Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                System.exit(100);
                            }
                        }, 5000);
                        firstTime++;
                    }
                }

                jump = false;
                fallingHeight = 0.0D;
                gravitation = 0.0D;
                if (Barrier.Collides(Main.player, barriers) != -1) {
                    onBarrier = true;
                    speed = 0.0D;
                    fallingHeight = 0.0D;
                    if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        jumpSound();
                        speed = -5.0D;
                        if (gravitation <= 7.0D) {
                            gravitation += -8.0D;
                        }
                    }

                    if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                        jump = true;
                        speed = 5.0D;
                        gravitation += -8.0D;
                        jumpSound();
                    }
                } else if (Barrier.Collides(Main.player, Main.platformBarriers) != -1) {
                    onBarrier = true;
                    speed = 0.0D;
                    fallingHeight = 0.0D;
                    if (fromRight && keyboard.getLeft() && !keyboard.getRight() && !keyboard.getUp()) {
                        jumpSound();
                        jump = true;
                        speed = -5.0D;
                        if (gravitation <= 7.0D) {
                            gravitation += -8.0D;
                        }
                    }

                    if (fromLeft && !keyboard.getLeft() && keyboard.getRight() && !keyboard.getUp()) {
                        jumpSound();
                        jump = true;
                        speed = 5.0D;
                        gravitation += -8.0D;
                    }
                } else {
                    if (keyboard.getLeft() && !keyboard.getRight() && !jump) {
                        speed -= 2.0D;
                    }

                    if (!keyboard.getLeft() && keyboard.getRight() && !jump) {
                        speed += 2.0D;
                    }
                }

                if (!keyboard.getLeft() && !keyboard.getRight()) {
                    if (speed > 0.0D) {
                        speed -= 1D;
                    } else if (speed < 0.0D) {
                        speed += 1D;
                    }
                }

                if (keyboard.getShift() && gravitation < 10.0D) {
                    fallingHeight /= 1.5D;
                }

                if (fallingHeight > 501.0D) {
                    var10001 = (int) fallingHeight;
                    System.out.println("Вы упали с высоты " + var10001 + " и разбились (максимальная высота: " + maxFallingHeight + ")");
                    chat[chatLast] = "Player fell from a high place!!!";
                    ++chatLast;
                    respawn();
                    ++deaths;
                    if (Main.isHardcore) {
                        System.exit(50);
                    }
                }

                if (Arrow.collides(Main.player, Main.arrows) || Platform.Collides(Main.player, new Platform[]{Main.first}) == 0) {
                    maxSpeed = 10;
                    maxFallingHeight = 800.0D;
                } else if (maxSpeed > 5) {
                    maxSpeed = (int) ((double) maxSpeed - 0.2D);
                    maxFallingHeight = 501.0D;
                }

                if (Math.abs(speed) > 0.0D && keyboard.getShift()) {
                    speed = 0.0D;
                }

                if (Math.abs(speed) > 4.0D) {
                    ohh = "";
                }

                if (100.0D <= fallingHeight && fallingHeight <= 200.0D) {
                    ohh = "oh";
                }

                if (201.0D <= fallingHeight && fallingHeight <= 300.0D) {
                    ohh = "ooh";
                }

                if (fallingHeight >= 301.0D) {
                    ohh = "oooh";
                }
            }

            if (fallingHeight >= 5000.0D) {
                System.out.println("Вы упали вниз");
                chat[chatLast] = "Player fell into the void!!!";
                ++chatLast;
                respawn();
                ++deaths;
                if (Main.isHardcore) {
                    System.exit(50);
                }
            }

            if (keyboard.getUp() && !jump && !onBarrier) {
                jumpSound();
                jump = true;
                gravitation += -10.0D;
            }

            if (speed > (double) maxSpeed) {
                speed = maxSpeed;
            }

            if (speed < (double) (-maxSpeed)) {
                speed = -maxSpeed;
            }

            if (!jump && !keyboard.getLeft() && !keyboard.getRight() && speed != 0.0D) {
                if (speed > 0.0D && speed > 1.0D) {
                    speed -= 1D;
                } else if (speed > 0.0D && speed < 1.0D) {
                    speed = 0.0D;
                }

                if (speed < 0.0D && speed < -1.0D) {
                    speed += 1D;
                } else if (speed < 0.0D && speed > -1.0D) {
                    speed = 0.0D;
                }
            }

            if (keyboard.getQ()) {
                System.out.println("Выход");
                System.exit(20);
            }

          /*  if (keyboard.getS()) {
                this.setSpawnpoint(x, y);
            }*/
            if (gravitation > 0.0D) {
                fallingHeight += Math.abs(gravitation);
            }

            if (Platform.Collides(Main.player, new Platform[]{Main.first}) == -1) {
                if (Platform.Collides(Main.player, platforms) == -1 && MovingPlatform.Collides(Main.player, movingPlatforms) == -1) {
                    gravitation += 0.4D;
                }
                if (!Arrow.collides(Main.player, Main.arrows)) {
                    if (Platform.Collides(Main.player, platforms) != -1)
                        maxSpeed = 5;
                }
            } else {
                maxSpeed = 30;
                speed = 30;
            }
            if (Platform.Collides(Main.player, new Platform[]{Main.firstJump}) == 0) {
                jump = true;
                gravitation += -2.0D;
                maxFallingHeight = 1000;
                Main.begScene = false;
            }
            x += speed;
            y += gravitation;

        } else {
            if (keyboard.getLeft() && !keyboard.getRight()) {
                x -= 50.0D;
            }

            if (!keyboard.getLeft() && keyboard.getRight()) {
                x += 50.0D;
            }

            if (keyboard.getUp()) {
                jumpSound();
                y -= 50.0D;
            }

            if (keyboard.getS()) {
                y += 50.0D;
            }
        }

/*        if (keyboard.getE()) {
            godMode = true;
        }

        if (keyboard.getR()) {
            godMode = false;
        }*/

        if (keyboard.getF11() && frame % 5 == 0) {
            if (Display.isFullScreen) {
                Display.frame.setSize(Display.w, Display.h);
            } else {
                Display.frame.setExtendedState(6);
            }

            Display.isFullScreen = !Display.isFullScreen;
        }

        if (keyboard.getF()) {
            chat[chatLast] = "[Debug] Player x: " + x + " ; player y: " + (y - 10000.0D);
            ++chatLast;
            System.out.println("Player x: " + x + " ; player y: " + (y - 10000.0D));
        }

        if (keyboard.getX()) {
            chatLast = 1;
            Arrays.fill(chat, "");
            chat[0] = "[Debug] Cleared chat";
        }

        if (keyboard.getC()) {
            this.cancelSpawnpoint();
        }

        if (keyboard.getV()) {
            respawn();
        }

     /*   if (keyboard.getG()) {
            this.setSpawnpoint(spawnpointsX.get(0), spawnpointsY.get(0));
        }*/

        if (fromDown) {
            if (fromLeft) {
                Main.playerSkin = 5;
            } else {
                Main.playerSkin = 3;
            }
        } else {
            if (fromLeft) {
                Main.playerSkin = 6;
            } else {
                Main.playerSkin = 4;
            }
        }
        if (keyboard.getB()) {
            Main.playerSkin = 2;
        }
        MovingPlatform.move(movingPlatforms);

    }

    private void cancelSpawnpoint() {
        if (spawnpointsX.size() > 2) {
            spawnX = spawnpointsX.get(spawnpointsX.size() - 2);
            spawnY = spawnpointsY.get(spawnpointsY.size() - 2);
            spawnpointsX.remove(spawnpointsX.size() - 1);
            spawnpointsY.remove(spawnpointsY.size() - 1);
            int var10002 = (int) spawnX;
            chat[chatLast] = "[Debug] Set player's spawnpoint on " + var10002 + "; " + ((int) spawnY - 10000);
            ++chatLast;
            System.out.println("Set spawnpoint on " + spawnX + "; " + spawnY);
        } else {
            chat[chatLast] = "[Debug] Nothing changed";
            ++chatLast;
            System.out.println("Nothing changed");
        }

    }

    private void setSpawnpoint(double x, double y) {
        int a = 0;

        for (int i = 0; i < spawnpointsX.size(); ++i) {
            if (spawnpointsX.get(i) == x && spawnpointsY.get(i) == y) {
                ++a;
                break;
            }
        }

        if (a == 0) {
            spawnX = x;
            spawnY = y;
            int var10002 = (int) spawnX;
            chat[chatLast] = "[Debug] Set player's spawnpoint on " + var10002 + "; " + ((int) spawnY - 10000);
            ++chatLast;
            System.out.println("Set spawnpoint on " + spawnX + "; " + spawnY);
            spawnpointsX.add(x);
            spawnpointsY.add(y);
        }

    }
}
