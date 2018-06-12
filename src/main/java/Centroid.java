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
}
