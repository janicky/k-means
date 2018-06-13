import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Map {

    private int k;
    private List<Centroid> centroids = new ArrayList<>();
    private List<Point> points = new ArrayList<>();
    private List<Integer> last_centroids = new ArrayList<>();
    private List<Integer> current_centroids = new ArrayList<>();
    private boolean first_assignment = true;
    private double global_error = Double.MAX_VALUE;
    private double last_global_error;
    private double precision = 0.005;
    private Random rng = new Random();

    public Map(int k) {
        this.k = k;
        loadPoints("attract_small.txt");
        createCentroids();

        for (Centroid c : centroids) {
            last_centroids.add(centroids.indexOf(c), 0);
            current_centroids.add(centroids.indexOf(c), c.getPoints().size());
        }

        double active_error;
        int corrections = 0;
        do {
            calculateAssignments();

            for (Centroid c : centroids) {
                last_centroids.set(centroids.indexOf(c), current_centroids.get(centroids.indexOf(c)));
                current_centroids.set(centroids.indexOf(c), c.getPoints().size());
                int current = current_centroids.get(centroids.indexOf(c));
                int last = last_centroids.get(centroids.indexOf(c));
            }
            last_global_error = global_error;
            global_error = getGlobalError();
            active_error = Math.abs((last_global_error - global_error) / global_error);
            corrections++;
        } while (active_error > precision);

        Diagram d = new Diagram(400);
        d.setCentroids(centroids);
        d.setPoints(points);
        d.draw();

        System.out.println(centroids.toString());
        System.out.println("E: " + getGlobalError());
        System.out.println("e: " + Double.toString(active_error));
        System.out.println("Corr: " + Integer.toString(corrections - 1));
    }

    private void createCentroids() {
        for (int i = 0; i < k; i++) {
            Point fp = points.get(rng.nextInt(points.size()));
            Point sp;
            do {
                sp = points.get(rng.nextInt(points.size()));
            } while (fp == sp);
            centroids.add(new Centroid(fp.getX(), fp.getY(), sp.getX(), sp.getY()));
        }
    }

    private void calculateAssignments() {
        if (!first_assignment) {
            for (Point p : points) {
                Centroid candidate = null;
                double min_dist = Double.MAX_VALUE;
                for (Centroid c : centroids) {
                    if (c.distanceTo(p) < min_dist) {
                        min_dist = c.distanceTo(p);
                        candidate = c;
                    }
                }
                if (!p.getCentroid().equals(candidate)) {
                    p.getCentroid().removePoint(p);
                    candidate.addPoint(p);
                }
            }
        } else {
            assignToCentroids();
            first_assignment = false;
        }
    }

    private void assignToCentroids() {
        for (Point p : points) {
            Centroid candidate = null;
            double min_dist = Double.MAX_VALUE;
            for (Centroid c : centroids) {
                if (c.distanceTo(p) < min_dist) {
                    min_dist = c.distanceTo(p);
                    candidate = c;
                }
            }
            candidate.addPoint(p);
        }
    }

    private void loadPoints(final String file_name) {
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

    private double getGlobalError() {
        double sum = 0d;
        if (centroids.size() == 0) {
            return 0d;
        }
        for (Centroid c : centroids) {
            sum += c.getError();
        }
        return sum / centroids.size();
    }
}
