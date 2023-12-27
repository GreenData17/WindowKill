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

    public static Vector2 add(Vector2 a, Vector2 b){
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static double getAngleTowards(Vector2 from, Vector2 to){
        double angle = Math.atan2(to.y - from.y, to.x - from.x);
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;
        return angle;
    }

    public static Vector2 moveTowardsAngle(Vector2 startPosition, double angleDegrees, double distance) {
        double angleRadians = Math.toRadians(angleDegrees);

        double deltaX = distance * Math.cos(angleRadians);
        double deltaY = distance * Math.sin(angleRadians);

        double newX = startPosition.x + deltaX;
        double newY = startPosition.y + deltaY;

        return new Vector2(newX, newY);
    }

    public static Vector2 rotatePositionFromOriginPointByDegrees(Vector2 position, Vector2 originPoint, double angleDegrees){
        double angleRadians = Math.toRadians(angleDegrees);

        double xPrime = (position.x - originPoint.x) * Math.cos(angleRadians) - (position.y - originPoint.y) * Math.sin(angleRadians) + originPoint.x;
        double yPrime = (position.x - originPoint.x) * Math.sin(angleRadians) + (position.y - originPoint.y) * Math.cos(angleRadians) + originPoint.y;

        return new Vector2(xPrime, yPrime);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
