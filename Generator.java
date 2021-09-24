import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Generator {

    public static int minPlatformLength = 100;
    public static int maxPlatformLength = 300;
    public static int minPlatformGateX = 100;
    public static int maxPlatformGateX = 150;
    public static int minPlatformGateY = 20;
    public static int maxPlatformGateYPlus = 60;
    public static int maxPlatformGateYMinus = 200;


    public static Platform generatePlatform(int x, int y, int width, int height) {
        return new Platform(x, y, width, height);
    }

    public static Platform generatePlatform(Platform previousPlatform, boolean right) {
        double a = Math.random();
        Platform exit;
        if (right) {
            if (a >= 0.5d) {
                double plusX = Math.random() * maxPlatformGateX + minPlatformGateX;
                double plusY = -Math.random() * maxPlatformGateYPlus - minPlatformGateY;
                if (plusX - plusY >= 230) {
                    exit = generatePlatform(previousPlatform, true);
                } else {
                    exit = new Platform(
                            (int) (previousPlatform.getPlatformX() + previousPlatform.getWidth() + plusX),
                            (int) (previousPlatform.getPlatformY() - previousPlatform.getHeight() + plusY),
                            (int) (Math.random() * maxPlatformLength + minPlatformLength), 50

                    );
                }
            } else {
                double plusX = Math.random() * maxPlatformGateX + minPlatformGateX;
                double plusY = Math.random() * maxPlatformGateYMinus + minPlatformGateY;
                exit = new Platform(
                        (int) (previousPlatform.getPlatformX() + previousPlatform.getWidth() + plusX),
                        (int) (previousPlatform.getPlatformY() + previousPlatform.getHeight() + plusY),
                        (int) (Math.random() * maxPlatformLength + minPlatformLength),
                        50

                );
            }
        } else {
            if (a >= 0.5) {
                exit = new Platform(
                        (int) (previousPlatform.getPlatformX() - previousPlatform.getWidth() - (Math.random() * maxPlatformGateX) + minPlatformGateX),
                        (int) (previousPlatform.getPlatformY() - previousPlatform.getHeight() - Math.random() * maxPlatformGateYPlus + minPlatformGateY),
                        (int) (Math.random() * maxPlatformLength + minPlatformLength),
                        50

                );
            } else {
                exit = new Platform(
                        (int) (previousPlatform.getPlatformX() - previousPlatform.getWidth() - (Math.random() * maxPlatformGateX) + minPlatformGateX),
                        (int) (previousPlatform.getPlatformY() + previousPlatform.getHeight() + Math.random() * maxPlatformGateYMinus + minPlatformGateY),
                        (int) (Math.random() * maxPlatformLength + minPlatformLength),
                        50

                );
            }
        }
        if (Math.random() >= 0.9) {
            exit.setSpawnpoint(true);
        }
        return exit;
    }

    public static Platform[] generatePlatforms(Platform firstPlatform, int numberOfPlatforms, boolean right) {
        Platform[] platforms = new Platform[numberOfPlatforms];
        platforms[0] = firstPlatform;
        for (int i = 1; i < numberOfPlatforms; i++) {
            platforms[i] = Generator.generatePlatform(platforms[i - 1], right);
        }
        return platforms;
    }

    public static Platform[] generateStairs(Platform firstPlatform, int numOfStairs, boolean right, boolean up) {
        Platform[] platforms = new Platform[numOfStairs];
        platforms[0] = firstPlatform;
        int platXGate = 0;
        int platYGate;
        if (up)
            platYGate = (int) (Math.random() * maxPlatformGateYPlus + minPlatformGateY);
        else
            platYGate = (int) (Math.random() * maxPlatformGateYMinus + minPlatformGateY / 1.5);
        int width = (int) (Math.random() * maxPlatformLength + minPlatformLength);
        if (width < 100) {
            width = 100;
        }
        int height = 50;
        if (right) {
            if (up) {
                Platform cur = firstPlatform;
                for (int i = 0; i < numOfStairs; i++) {
                    platforms[i] = new Platform((int) (cur.getPlatformX() + platXGate + cur.getWidth()), (int) (cur.getPlatformY() - platYGate - cur.getHeight()), width, height);
                    cur = platforms[i];
                }
            } else {
                Platform cur = firstPlatform;
                for (int i = 0; i < numOfStairs; i++) {
                    platforms[i] = new Platform((int) (cur.getPlatformX() + platXGate + cur.getWidth()), (int) (cur.getPlatformY() + platYGate + cur.getHeight()), width, height);
                    cur = platforms[i];
                }
            }
        } else {
            if (up) {
                Platform cur = firstPlatform;
                for (int i = 0; i < numOfStairs; i++) {
                    platforms[i] = new Platform((int) (cur.getPlatformX() - platXGate), (int) (cur.getPlatformY() - platYGate - cur.getHeight()), width, height);
                    cur = platforms[i];
                }
            } else {
                Platform cur = firstPlatform;
                for (int i = 0; i < numOfStairs; i++) {
                    platforms[i] = new Platform((int) (cur.getPlatformX() - platXGate), (int) (cur.getPlatformY() + platYGate + cur.getHeight()), width, height);
                    cur = platforms[i];
                }
            }
        }
        platforms[numOfStairs - 1].setSpawnpoint(true);
        return platforms;
    }

    public static Object[] generateLongJump(Platform from) {
        Object[] longJump = new Object[3];
        longJump[0] = from;
        longJump[1] = new Arrow((int) from.getPlatformX(), (int) (from.getPlatformY() - from.getHeight()), (int) from.getWidth(), 50);
        if (Math.random() > 0.5) {
            Platform pl1 = new Platform((int) ((Math.random() * (maxPlatformGateX * 3 - 200)) + from.getPlatformX() + from.getWidth() + 200), (int) ((Math.random() * maxPlatformGateYMinus - minPlatformGateY) + from.getPlatformY() - from.getHeight()), (int) (Math.random() * maxPlatformLength + minPlatformLength), 50);
            longJump[2] = pl1;
        } else {
            Platform pl1 = new Platform((int) ((Math.random() * (maxPlatformGateX * 2 - 200)) + from.getPlatformX() + from.getWidth() + 200), (int) ((Math.random() * maxPlatformGateYPlus + minPlatformGateY) + from.getPlatformY()), (int) (Math.random() * maxPlatformLength + minPlatformLength), 50);
            longJump[2] = pl1;
        }
        return longJump;
    }

    public static Object[] generateTower(Platform from, boolean right, boolean fromDown, boolean thisWay) {
        Object[] tower = new Object[4];
        if (right) {
            if (fromDown) {
                if (thisWay) {
                    tower[0] = from;
                    double a = -Math.random() * 600 - 300;
                    Barrier barrier1 = new Barrier(from.getPlatformX() + from.getWidth(), from.getPlatformY() + a, from.getPlatformY());
                    Barrier barrier2 = new Barrier(from.getPlatformX() + from.getWidth() - 100, from.getPlatformY() + a - 100, from.getPlatformY() - 70);
                    tower[1] = barrier1;
                    tower[2] = barrier2;
                    Platform up = new Platform((int) (from.getPlatformX() + from.getWidth() + 10), (int) (from.getPlatformY() + a), (int) (Math.random() * maxPlatformGateX + minPlatformGateX), 50);
                    tower[3] = up;
                } else {
                    tower[0] = from;
                    double a = -Math.random() * 600 - 300;
                    Barrier barrier1 = new Barrier(from.getPlatformX() + from.getWidth(), from.getPlatformY() + a - 70, from.getPlatformY());
                    Barrier barrier2 = new Barrier(from.getPlatformX() + from.getWidth() - 100, from.getPlatformY() + a, from.getPlatformY() - 70);
                    tower[1] = barrier1;
                    tower[2] = barrier2;
                    Platform up = new Platform((int) (from.getPlatformX() - 10), (int) (from.getPlatformY() + a), (int) (from.getWidth() - 100), 50);
                    tower[3] = up;
                }
            } else {
                if (thisWay) {
                    tower[0] = from;
                    double a = -Math.random() * 600 - 300;
                    Barrier barrier1 = new Barrier(from.getPlatformX() + from.getWidth(), from.getPlatformY() + from.getHeight(), from.getPlatformY() - a);
                    Barrier barrier2 = new Barrier(from.getPlatformX() + from.getWidth() + 100, from.getPlatformY() - 70, from.getPlatformY() - a - 70);
                    tower[1] = barrier1;
                    tower[2] = barrier2;
                    Platform down = new Platform((int) (from.getPlatformX() + from.getWidth() + 10), (int) (from.getPlatformY() - a), (int) (Math.random() * maxPlatformGateX + minPlatformGateX), 50);
                    tower[3] = down;
                } else {
                    tower[0] = from;
                    double a = -Math.random() * 600 - 300;
                    Barrier barrier1 = new Barrier(from.getPlatformX() + from.getWidth(), from.getPlatformY(), from.getPlatformY() + a - 70);
                    Barrier barrier2 = new Barrier(from.getPlatformX() + from.getWidth() - 100, from.getPlatformY() - 70, from.getPlatformY() - a);
                    tower[1] = barrier1;
                    tower[2] = barrier2;
                    Platform up = new Platform((int) (from.getPlatformX() - 10), (int) (from.getPlatformY() - a), (int) (from.getWidth() - 100), 50);
                    tower[3] = up;
                }
            }
        }
        return tower;
    }

    public static Platform[] generateStairTower(Platform fram, boolean fromDown) {
        Platform from = Generator.generatePlatform(fram, true);
        BigInteger b = new BigInteger(String.valueOf(10));
        int a = (int) ((Math.random() * 10 + 5) / 2) * 2;
        int plusX = (int) (Math.random() * 100 + 40);
        int plusY = (int) (Math.random() * 70 + 30);
        int width = (int) from.getWidth();
        Platform[] tower = new Platform[a];
        tower[0] = from;
        if (fromDown) {
            for (int i = 1; i < a; i++) {
                Platform cur = tower[i - 1];
                Platform pl;
                if (i % 2 == 1) {
                    pl = Generator.generatePlatform((int) (from.getPlatformX() + from.getWidth() + plusX), (int) (cur.getPlatformY() - cur.getHeight() - plusY), width, 50);
                } else {
                    pl = generatePlatform((int) (from.getPlatformX()), (int) (from.getPlatformY() - i * (from.getHeight() + plusY)), width, 50);
                }
                tower[i] = pl;
            }
        }

        return tower;
    }

    public static Object[] generateMovingPlatformFromAtoB(Platform from) {
        Object[] lvlPart = new Object[2];
        Platform a = generatePlatform(from, true);
        Platform to;
        a.setPlatformX(a.getPlatformX() - 100);
        double b = Math.random();
        MovingPlatform pl;
        int x1, x2, y1, y2;
        double XSpeed, YSpeed;
        x1 = (int) a.getPlatformX();
        x2 = x1 + (int) (Math.random() * 800) + 200;
        y1 = (int) a.getPlatformY();
        if (b <= 0.5) {
            y2 = y1 + ((int) (Math.random() * 300) + 400);
            XSpeed = Math.random() * 2.5 + 2.5;
            YSpeed = ((y2 - y1) * XSpeed) / (x2 - x1);
            pl = new MovingPlatform(a, x1, x2, XSpeed, true, y1, y2, YSpeed, true);
            to = new Platform(pl.x2 + (int) pl.platform.getWidth() + 100, pl.y2, (int) (Math.random() * 300) + 100, 50, true);
        } else {
            y2 = y1 - ((int) (Math.random() * 300) + 200);
            XSpeed = Math.random() * 2.5 + 2.5;
            YSpeed = ((y2 - y1) * XSpeed) / (x2 - x1);
            pl = new MovingPlatform(a, x1, x2, XSpeed, true, y2, y1, YSpeed, true);
            to = new Platform(pl.x2 + (int) pl.platform.getWidth() + 100, pl.y1, (int) (Math.random() * 300) + 100, 50, true);
        }
        lvlPart[0] = pl;
        lvlPart[1] = to;
        return lvlPart;
    }

    public static Object[] generateLevel(int length) {
        ArrayList<Object> level = new ArrayList<>();
        level.add(0, Generator.generatePlatform(1400, -200, 350, 50));
        level.add(1, Generator.generatePlatform((Platform) level.get(0), true));
        int lastObjGen = 0;
        int b;
        if (length == -1) {
            b = (int) (Math.random() * 150 + 70);
        } else {
            b = length;
        }
        for (int i = 2; i < b; i++) {
            double a = Math.random();
            if (a <= 0.6) {
                level.add(Generator.generatePlatform((Platform) level.get(level.size() - 1), true));
                lastObjGen = 1;
            } else if (a > 0.60 && a <= 0.75) {
                if (lastObjGen == 1) {
                    Object[] mov = Generator.generateMovingPlatformFromAtoB((Platform) level.get(level.size() - 1));
                    level.addAll(Arrays.asList(mov));
                    lastObjGen = 1;
                } else {
                    i--;
                }
            } else if (a > 0.75 && a <= 0.8) {
                if (lastObjGen == 1) {
                    i--;
                    continue;
                }
                level.addAll(Arrays.asList(Generator.generateStairTower((Platform) level.get(level.size() - 1), true)));
                lastObjGen = 2;

            } else if (a > 0.8 && a < 0.9) {
                Platform[] stairs;
                if (a < 0.85)
                    stairs = Generator.generateStairs((Platform) level.get(level.size() - 1), (int) (Math.random() * 3 + 2), true, true);
                else
                    stairs = Generator.generateStairs((Platform) level.get(level.size() - 1), (int) (Math.random() * 3 + 2), true, false);
                level.addAll(Arrays.asList(stairs));
                lastObjGen = 3;
            } else if (a >= 0.9 && a <= 0.95) {
                if (lastObjGen != 1) {
                    i--;
                    continue;
                }
                Object[] tower = Generator.generateTower((Platform) level.get(level.size() - 1), true, true, true);
                level.addAll(Arrays.asList(tower));
                lastObjGen = 4;
            } else {
                if (lastObjGen == 5 || lastObjGen == 4) {
                    i--;
                    continue;
                }

                level.addAll(Arrays.asList(Generator.generateLongJump((Platform) level.get(level.size() - 1))));
                lastObjGen = 5;
            }
        }
        return level.toArray();
    }

}
