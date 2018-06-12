import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {

    private int k;
    private List<Centroid> centroids = new ArrayList<>();
    private List<Point> points = new ArrayList<>();

    public Map(int k) {
        this.k = k;
        loadPoints("attract_small.txt");
    }

    public void loadPoints(final String file_name) {
        try {
            File file = new File(file_name);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] values = line.split("\\,");
                points.add(new Point(
                        Double.parseDouble(values[0]),
                        Double.parseDouble(values[1])
                ));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
