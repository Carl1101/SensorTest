package sensor.data;


public class Trio {
    private double x;
    private double y;
    private double z;

    /**
     *  The exact Android definition of event.timestamp is unclear
     * (see https://code.google.com/p/android/issues/detail?id=7981).
     *
     * For the USF sensors, the timestamp values is in nanoseconds and as the
     * SystemClock.elapsedRealtimeNanos() value (i.e., elapsed nanoseconds since last boot,
     * including time spent in sleep).  For API levels, less than 17
     * (Build.VERSION_CODES.JELLY_BEAN_MR1), we use SystemClock.elapsedRealtime(), converted to
     * nanoseconds.
     */
    private long timestamp;

    public Trio() {
        x = y = z = 0;
        timestamp = 0;
    }

    public Trio(Trio t) {
        this.x = t.x;
        this.y = t.y;
        this.z = t.z;
        this.timestamp = t.timestamp;
    }

    public Trio(double x, double y, double z, long timestamp) {
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

    public void set(Trio t) {
        this.x = t.getX();
        this.y = t.getY();
        this.z = t.getZ();
        this.timestamp = t.timestamp;
    }

    public void set(float[] a) {
        this.x = a[0];
        this.y = a[1];
        this.z = a[2];
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.timestamp = 0;
    }


    public Trio neg() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Trio sum(Trio t) {
        Trio trio = new Trio();
        trio.x = this.x + t.x;
        trio.y = this.y + t.y;
        trio.z = this.z + t.z;
        return trio;
    }

    public void addition(Trio t) {
        this.x += t.x;
        this.y += t.y;
        this.z += t.z;
    }

    public Trio sub(Trio t) {
        Trio trio = new Trio();
        trio.x = this.x - t.x;
        trio.y = this.y - t.y;
        trio.z = this.z - t.z;
        return trio;
    }

    public void subtraction(Trio t) {
        this.x -= t.x;
        this.y -= t.y;
        this.z -= t.z;
    }

    public Trio mult(double i) {
        Trio trio = new Trio();
        trio.x = this.x * i;
        trio.y = this.y * i;
        trio.z = this.z * i;
        return trio;
    }

    public Trio multMatrix(float[] Matrix) {
        Trio trio = new Trio();
        for (int i = 0; i < 3; i++) {
            trio.x += this.x * Matrix[i];
            trio.y += this.y * Matrix[i + 3];
            trio.z += this.z * Matrix[i + 6];
        }
        return trio;
    }

    public Trio div(int i) {
        Trio trio = new Trio();
        trio.x = this.x / i;
        trio.y = this.y / i;
        trio.z = this.z / i;
        return trio;
    }

    public void division(int i) {
        this.x /= i;
        this.y /= i;
        this.z /= i;
    }

    public Trio sq() {
        Trio trio = new Trio();
        trio.x = this.x * this.x;
        trio.y = this.y * this.y;
        trio.z = this.z * this.z;
        return trio;
    }

    public double energy() {
        double energy = 0;
        energy = Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
        return energy;
    }


    public double energyZ() {
        double energy = 0;
        energy = Math.sqrt((this.z * this.z));
        return energy;
    }


    public void windowfiltering(double d) {
        if (Math.abs(this.x) < d)
            this.x = 0;
        if (Math.abs(this.y) < d)
            this.y = 0;
        if (Math.abs(this.z) < d)
            this.z = 0;
    }

    @Override
    public String toString() {
        return x + ";" + y + ";" + z;
    }
}
