import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Centroid {

    private double x;
    private double y;
    List<Point> points = new ArrayList<>();

    public Centroid(double x_min, double x_max, double y_min, double y_max) {
        calculateCenter(x_min, x_max, y_min, y_max);
    }

    public boolean addPoint(Point p) {
        return points.add(p);
    }

    public boolean removePoint(Point p) {
        return points.remove(p);
    }

    public void calculateCenter(double x_min, double x_max, double y_min, double y_max) {
        if (points.size() > 0) {
            double x_mean = 0d, y_mean = 0d;

            for (Point p : points) {
                x_mean += p.getX();
                y_mean += p.getY();
            }

            x = x_mean / points.size();
            y = y_mean / points.size();
        } else {
            Random rng = new Random();
            x = x_min + (x_max - x_min) * rng.nextDouble();
            y = y_min + (y_max - y_min) * rng.nextDouble();
        }
    }

    public double distanceTo(Point p) {
        return Math.sqrt(
                Math.pow(x - p.getX(), 2) +
                Math.pow(y - p.getY(), 2)
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[x: ");
        sb.append(x);
        sb.append(", y: ");
        sb.append(y);
        sb.append(", points: ");
        sb.append(points.size() + "]");

        return sb.toString();
    }
}
