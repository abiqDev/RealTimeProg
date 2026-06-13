package ProjectLatest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * BloodPressureSensor scans all patients and generates systolic blood pressure readings.
 * Risk threshold: Systolic BP > 140 mmHg (hypertension stage 2)
 */
public class BloodPressureSensor implements Runnable {

    private static final int    RISK_THRESHOLD = 140; // mmHg systolic
    private static final String SENSOR_TYPE    = "Blood Pressure";
    private static final String UNIT           = "mmHg (systolic)";

    private final List<Patient>                patientList;
    private final BlockingQueue<SensorReading> riskQueue;
    private final CentralHealthDashboard       dashboard;
    private final Random                       random    = new Random();
    private volatile boolean                   isRunning = true;

    public BloodPressureSensor(List<Patient> patientList,
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

                // Generate systolic BP between 100 and 179 mmHg
                int systolic = 100 + random.nextInt(80);

                dashboard.logSensorReading(SENSOR_TYPE, patient, systolic, UNIT);

                if (systolic > RISK_THRESHOLD) {
                    try {
                        riskQueue.put(new SensorReading(patient, systolic, SENSOR_TYPE));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("[BloodPressureSensor] Sensor thread interrupted.");
                break;
            }
        }
    }
}
