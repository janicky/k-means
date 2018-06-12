import java.util.ArrayList;
import java.util.List;

public class Centroid {

    List<Point> points = new ArrayList<>();

    public boolean addPoint(Point p) {
        return points.add(p);
    }
}
