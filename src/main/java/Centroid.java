import java.util.ArrayList;
import java.util.List;

public class Centroid {

    private int x;
    private int y;
    List<Point> points = new ArrayList<>();

    public Centroid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean addPoint(Point p) {
        return points.add(p);
    }

    public boolean removePoint(Point p) {
        return points.remove(p);
    }

    public double distanceTo(Point p) {
        return Math.sqrt(
                Math.pow(x - p.getX(), 2) +
                Math.pow(y - p.getY(), 2)
        );
    }
}
