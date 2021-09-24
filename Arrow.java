public class Arrow {
    private final int defaultX;
    private final int defaultY;
    private int arrowX = 0;
    private int arrowY = 0;
    private int length = 100;
    private int height = 50;

    public Arrow(int x, int y, int Length, int height) {
        this.arrowX = x;
        this.arrowY = y;
        this.defaultX = x;
        this.defaultY = y;
        this.length = Length;
        this.height = height;
    }

    public static void respawn(Arrow[] arrows) {
        for (int i = 0; i < arrows.length; ++i) {
            Arrow arrow = arrows[i];
            arrow.setArrowX(arrow.defaultX);
            arrow.setArrowY(arrow.defaultY);
        }

    }

    public static boolean collides(Player player, Arrow[] arrows) {
        int a = 0;

        for (int i = 0; i < arrows.length; ++i) {
            Arrow arrow = arrows[i];
            double x0 = player.getX();
            double y0 = player.getY();
            double x1 = player.getX() + player.getSize();
            double y1 = player.getY() + player.getSize();
            double x2 = arrow.arrowX;
            double y2 = arrow.arrowY;
            double x3 = x2 + (double) arrow.length;
            double y3 = y2 + (double) arrow.height;
            if (!(x0 > x3) && !(x1 < x2) && !(y0 > y3) && !(y1 < y2)) {
                ++a;
            }
        }

        return a > 0;
    }

    public int getHeight() {
        return this.height;
    }

    public int getArrowX() {
        return this.arrowX;
    }

    public void setArrowX(int arrowX) {
        this.arrowX = arrowX;
    }

    public int getArrowY() {
        return this.arrowY;
    }

    public void setArrowY(int arrowY) {
        this.arrowY = arrowY;
    }

    public void plusarrowX(double a) {
        this.arrowX = (int) ((double) this.arrowX + a);
    }

    public void plusarrowY(double a) {
        this.arrowY = (int) ((double) this.arrowY + a);
    }

    public int getLength() {
        return this.length;
    }
}
