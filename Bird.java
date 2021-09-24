public class Bird {
    public double x, y;
    public int skin;

    public Bird(double x, double y) {
        this.x = Display.frame.getWidth() + x;
        this.y = y;
        this.skin = 1;
    }

    public static void move(Bird[] birds) {
        for (int i = 0; i < birds.length; i++) {
            Bird cur = birds[i];
            cur.x -= 7 + Player.speed;
        }
    }
}
