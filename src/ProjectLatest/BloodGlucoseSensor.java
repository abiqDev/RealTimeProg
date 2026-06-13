package ProjectLatest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * BloodGlucoseSensor scans all patients and generates blood glucose readings.
 * Risk threshold: Blood Glucose > 180 mg/dL (hyperglycemia)
 */
public class BloodGlucoseSensor implements Runnable {

    private static final double RISK_THRESHOLD = 180.0; // mg/dL
    private static final String SENSOR_TYPE    = "Blood Glucose";
    private static final String UNIT           = "mg/dL";

    private final List<Patient>                patientList;
    private final BlockingQueue<SensorReading> riskQueue;
    private final CentralHealthDashboard       dashboard;
    private final Random                       random    = new Random();
    private volatile boolean                   isRunning = true;

    public BloodGlucoseSensor(List<Patient> patientList,
                              BlockingQueue<SensorReading> riskQueue,
                              CentralHealthDashboard dashboard) {
        this.patientList = patientList;
        this.riskQueue   = riskQueue;
        this.dashboard   = dashboard;
    }

    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            for (Patient patient : patientList) {

                // Generate blood glucose between 70 and 249 mg/dL
                double glucose = 70.0 + random.nextDouble() * 180.0;
                glucose = Math.round(glucose * 10.0) / 10.0;

                patient.updateVitals(patient.getHeartRate(),
                        patient.getBloodPressure() != null ? patient.getBloodPressure() : "0/0",
                        patient.getBloodOxygen(),
                        glucose);

                dashboard.logSensorReading(SENSOR_TYPE, patient, glucose, UNIT);

                if (glucose > RISK_THRESHOLD) {
                    try {
                        riskQueue.put(new SensorReading(patient, glucose, SENSOR_TYPE));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("[BloodGlucoseSensor] Sensor thread interrupted.");
                break;
            }
        }
    }
}
