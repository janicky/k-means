public class Vector {

    private double x;
    private double y;
    private Centroid centroid;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setCentroid(Centroid c) {
        centroid = c;
    }

    public Centroid getCentroid() {
        return centroid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[x: ");
        sb.append(x + ", ");
        sb.append("y: ");
        sb.append(y + "]");

        return sb.toString();
    }
}
