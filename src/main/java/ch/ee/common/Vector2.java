package ch.ee.common;

public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double xy) {
        this.x = xy;
        this.y = xy;
    }

    public static Vector2 zero() {
        return new Vector2(0);
    }
}
