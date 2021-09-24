public class Rectangle {
    double x;
    double y;
    double w;
    double h;

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean overlaps(Rectangle other) {
        if (Math.abs(this.x / 2.0D - other.x / 2.0D) > this.w / 2.0D + other.w / 2.0D) {
            return false;
        } else {
            return !(Math.abs(this.y / 2.0D - other.y / 2.0D) > this.h / 2.0D + other.h / 2.0D);
        }
    }
}
