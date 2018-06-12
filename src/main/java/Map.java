import de.alsclo.voronoi.Voronoi;
import de.alsclo.voronoi.graph.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Map {

    private int k;
    private List<Centroid> centroids = new ArrayList<>();
    private List<Vector> vectors = new ArrayList<>();
    private List<Integer> last_centroids = new ArrayList<>();
    private List<Integer> current_centroids = new ArrayList<>();
    private boolean first_assignment = true;

    public Map(int k) {
        this.k = k;
        loadPoints("attract_small.txt");
        createCentroids();

        for (Centroid c : centroids) {
            last_centroids.add(centroids.indexOf(c), 0);
            current_centroids.add(centroids.indexOf(c), c.getVectors().size());
        }

        boolean stop = false;
        while (!stop) {
            stop = true;
            calculateAssignments();

            for (Centroid c : centroids) {
                last_centroids.set(centroids.indexOf(c), current_centroids.get(centroids.indexOf(c)));
                current_centroids.set(centroids.indexOf(c), c.getVectors().size());
                int current = current_centroids.get(centroids.indexOf(c));
                int last = last_centroids.get(centroids.indexOf(c));

                if (current != last && current != 0) {
                    stop = false;
                }
            }
            System.out.println(centroids.toString());
        }

//        Collection<Point> pts = new ArrayList<>();
//        for (Vector v : vectors) {
//            pts.add(new Point(v.getX(), v.getY()));
//        }
//        Voronoi voronoi = new Voronoi(pts);


    }

    private void createCentroids() {
        for (int i = 0; i < k; i++) {
            centroids.add(new Centroid(-20.0, 20.0, -20, 20));
        }
    }

    private void calculateAssignments() {
        if (!first_assignment) {
            for (Vector v : vectors) {
                Centroid candidate = null;
                double min_dist = Double.MAX_VALUE;
                for (Centroid c : centroids) {
                    if (c.distanceTo(v) < min_dist) {
                        min_dist = c.distanceTo(v);
                        candidate = c;
                    }
                }
                if (!v.getCentroid().equals(candidate)) {
                    v.getCentroid().removeVector(v);
                    candidate.addVector(v);
                }
            }
        } else {
            assignToCentroids();
            first_assignment = false;
        }
    }

    private void assignToCentroids() {
        for (Vector v : vectors) {
            Centroid candidate = null;
            double min_dist = Double.MAX_VALUE;
            for (Centroid c : centroids) {
                if (c.distanceTo(v) < min_dist) {
                    min_dist = c.distanceTo(v);
                    candidate = c;
                }
            }
            candidate.addVector(v);
        }
    }

    private void loadPoints(final String file_name) {
        try {
            File file = new File(file_name);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] values = line.split("\\,");
                vectors.add(new Vector(
                        Double.parseDouble(values[0]),
                        Double.parseDouble(values[1])
                ));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
