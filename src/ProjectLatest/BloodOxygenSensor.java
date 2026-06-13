package ProjectLatest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * BloodOxygenSensor scans all patients and generates blood oxygen saturation (SpO2) readings.
 * Risk threshold: SpO2 < 90% (hypoxemia — dangerously low oxygen)
 */
public class BloodOxygenSensor implements Runnable {

    private static final double RISK_THRESHOLD = 90.0; // % — below this is at risk
    private static final String SENSOR_TYPE    = "Blood Oxygen";
    private static final String UNIT           = "% SpO2";

    private final List<Patient>                patientList;
    private final BlockingQueue<SensorReading> riskQueue;
    private final CentralHealthDashboard       dashboard;
    private final Random                       random    = new Random();
    private volatile boolean                   isRunning = true;

    public BloodOxygenSensor(List<Patient> patientList,
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

                // Generate SpO2 between 85% and 99%
                // Range deliberately includes values below 90 to simulate at-risk cases
                double spO2 = 85.0 + random.nextDouble() * 14.0;
                spO2 = Math.round(spO2 * 10.0) / 10.0; // round to 1 decimal place

                patient.updateVitals(patient.getHeartRate(),
                        patient.getBloodPressure() != null ? patient.getBloodPressure() : "0/0",
                        spO2,
                        patient.getBloodGlucose());

                dashboard.logSensorReading(SENSOR_TYPE, patient, spO2, UNIT);

                // Note: risk is BELOW threshold for oxygen (opposite of HR and BP)
                if (spO2 < RISK_THRESHOLD) {
                    try {
                        riskQueue.put(new SensorReading(patient, spO2, SENSOR_TYPE));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("[BloodOxygenSensor] Sensor thread interrupted.");
                break;
            }
        }
    }
}
