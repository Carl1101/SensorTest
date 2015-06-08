package sensor.data;


public class Sample {
    private double x;
    private double y;
    private double z;
    private long timestamp;

    public Sample() {
        x = y = z = 0;
        timestamp = 0;
    }

    public Sample(Sample s) {
        this.x = s.x;
        this.y = s.y;
        this.z = s.z;
        this.timestamp = s.timestamp;
    }

    public Sample(double x, double y, double z, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public double getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return x + ";" + y + ";" + z;
    }
}
