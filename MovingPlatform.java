public class MovingPlatform {
    public Platform platform;
    public int x1;
    public int x2;
    public int y1, y2;
    public double XSpeed, YSpeed, defaultXSpeed, defaultYSpeed;
    public boolean XDirection, YDirection;

    public MovingPlatform(Platform platform, int x1, int x2, double XSpeed, boolean XDirection, int y1, int y2, double YSpeed, boolean YDirection) {
        this.platform = platform;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.XSpeed = XSpeed;
        this.YSpeed = YSpeed;
        this.defaultXSpeed = XSpeed;
        this.defaultYSpeed = YSpeed;
        this.XDirection = XDirection;
        this.YDirection = YDirection;
    }


    public static void move(MovingPlatform[] platforms) {
        for (int i = 0; i < platforms.length; i++) {
            MovingPlatform cur = platforms[i];
            if (cur.XDirection) {
                if (cur.XSpeed <= 0) {
                    cur.XSpeed = -cur.XSpeed;
                }
            } else {
                if (cur.XSpeed > 0) {
                    cur.XSpeed = -cur.XSpeed;
                }
            }
            if (cur.platform.getPlatformX() <= cur.x1) {
                cur.XDirection = true;
            }
            if (cur.platform.getPlatformX() >= cur.x2) {
                cur.XDirection = false;
            }

            if (cur.YDirection) {
                if (cur.YSpeed <= 0) {
                    cur.YSpeed = -cur.YSpeed;
                }
            } else {
                if (cur.YSpeed > 0) {
                    cur.YSpeed = -cur.YSpeed;
                }
            }
            if (cur.platform.getPlatformY() <= cur.y1) {
                cur.YDirection = true;
            }
            if (cur.platform.getPlatformY() >= cur.y2) {
                cur.YDirection = false;
            }
            cur.platform.setPlatformX(cur.platform.getPlatformX() + cur.XSpeed);
            cur.platform.setPlatformY(cur.platform.getPlatformY() + cur.YSpeed);
        }
    }

    public static int Collides(Player player, MovingPlatform[] platforms) {
        for (int i = 0; i < platforms.length; i++) {
            MovingPlatform cur = platforms[i];
            if (Platform.Collides(player, new Platform[]{cur.platform}) != -1) {
                return i;
            }
        }
        return -1;
    }
}
