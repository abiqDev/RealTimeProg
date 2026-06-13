package ProjectLatest;

/**
 * Simple data model representing a Patient.
 * Holds identity (id, name) and vital signs (heartRate, bloodPressure).
 * updateVitals() is synchronized to prevent race conditions when
 * multiple threads write to this object simultaneously.
 */
public class Patient {

    private final String id;
    private final String name;
    private int heartRate;
    private String bloodPressure;
    private double bloodOxygen;
    private double bloodGlucose;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // synchronized — only one thread can update vitals at a time
    // prevents a race condition where fields get mismatched readings
    public synchronized void updateVitals(int heartRate, String bloodPressure,
                                          double bloodOxygen, double bloodGlucose) {
        this.heartRate     = heartRate;
        this.bloodPressure = bloodPressure;
        this.bloodOxygen   = bloodOxygen;
        this.bloodGlucose  = bloodGlucose;
    }

    public String getId()            { return id; }
    public String getName()          { return name; }
    public int getHeartRate()        { return heartRate; }
    public String getBloodPressure() { return bloodPressure; }
    public double getBloodOxygen()   { return bloodOxygen; }
    public double getBloodGlucose()  { return bloodGlucose; }
}
