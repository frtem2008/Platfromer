public class Platform {
    private final int defaultX;
    private final int defaultY;
    private final Rectangle r;
    private double platformX = 0.0D;
    private double platformY = 0.0D;
    private int width = 100;
    private int height = 50;
    private boolean isSpawnpoint = false;

    public Platform(Platform platform) {
        this.platformX = platform.getPlatformX();
        this.platformY = platform.getPlatformY();
        this.defaultX = (int) platform.getPlatformX();
        this.defaultY = (int) platform.getPlatformY();
        this.width = (int) platform.getWidth();
        this.height = (int) platform.getHeight();
        this.isSpawnpoint = platform.isSpawnpoint();
        this.r = platform.getRect();
    }

    public Platform(int x, int y, int width, int height) {
        this.platformX = x;
        this.platformY = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
        this.r = new Rectangle(x, y, width, height);
    }

    public Platform(int x, int y, int Width, int Heigth, boolean isSpawnpoint) {
        this.platformX = x;
        this.platformY = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = Width;
        this.height = Heigth;
        this.r = new Rectangle(x, y, Width, Heigth);
        this.isSpawnpoint = isSpawnpoint;
    }

    public static int Collides(Player player, Platform[] platforms) {
        int b = -1;

        for (int i = 0; i < platforms.length; ++i) {
            Platform platform = platforms[i];
            double x0 = player.getX();
            double y0 = player.getY();
            double x1 = player.getX() + player.getSize();
            double y1 = player.getY() + player.getSize();
            double x2 = platform.platformX;
            double y2 = platform.platformY;
            double x3 = platform.platformX + (double) platform.width;
            double y3 = platform.platformY + (double) platform.height;
            if (!(x0 > x3) && !(x1 < x2) && !(y0 > y3) && !(y1 < y2)) {
                b = i;
            }
        }
        return b;
    }

    public boolean isSpawnpoint() {
        return this.isSpawnpoint;
    }

    public void setSpawnpoint(boolean spawnpoint) {
        isSpawnpoint = spawnpoint;
    }

    public Rectangle getRect() {
        return this.r;
    }

    public double getHeight() {
        return this.r.h;
    }

    public double getPlatformX() {
        return this.r.x;
    }

    public void setPlatformX(double platformX) {
        this.platformX = platformX;
        this.r.x = platformX;

    }

    public double getPlatformY() {
        return this.r.y;
    }

    public void setPlatformY(double platformY) {
        this.platformY = platformY;
        this.r.y = platformY;
    }

    public double getWidth() {
        return this.r.w;
    }
}
