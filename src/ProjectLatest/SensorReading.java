package ProjectLatest;

/**
 * SensorReading is a simple data object (also called a "carrier" or "DTO").
 * Its only job is to bundle a Patient together with their reading value
 * so the doctor thread knows WHO is at risk and WHAT their reading was.
 *
 * This object is placed into the BlockingQueue by a sensor thread,
 * and taken out by a doctor thread.
 */
public class SensorReading {

    private final Patient patient;      // the patient who has the risky reading
    private final double readingValue;  // the actual value recorded (e.g. 145.0 bpm)
    private final String sensorType;   // which sensor produced this (e.g. "Heart Rate")

    public SensorReading(Patient patient, double readingValue, String sensorType) {
        this.patient = patient;
        this.readingValue = readingValue;
        this.sensorType = sensorType;
    }

    public Patient getPatient()       { return patient; }
    public double getReadingValue()   { return readingValue; }
    public String getSensorType()     { return sensorType; }
}
