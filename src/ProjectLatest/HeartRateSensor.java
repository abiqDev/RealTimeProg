package ProjectLatest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * REQ A: THREAD AND RUNNABLE OBJECTS
 * HeartRateSensor is a sensor thread that continuously scans all patients,
 * generates a random heart rate reading for each, logs it to the dashboard,
 * and places any at-risk patient into the BlockingQueue for the doctor to handle.
 *
 * Risk threshold: Heart Rate > 100 bpm (tachycardia)
 */
public class HeartRateSensor implements Runnable {

    private static final int RISK_THRESHOLD = 100; // bpm — above this is at risk
    private static final String SENSOR_TYPE  = "Heart Rate";
    private static final String UNIT         = "bpm";

    private final List<Patient>              patientList;  // all 20 patients to scan
    private final BlockingQueue<SensorReading> riskQueue;  // shared queue with doctor
    private final CentralHealthDashboard     dashboard;
    private final Random                     random       = new Random();
    private volatile boolean                 isRunning    = true; // volatile for safe stop

    public HeartRateSensor(List<Patient> patientList,
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

                // Generate a random heart rate between 60 and 149 bpm
                int heartRate = 60 + random.nextInt(90);

                // Store the reading on the patient object (used by Phase 2 analytics)
                patient.updateVitals(heartRate,
                        patient.getBloodPressure() != null ? patient.getBloodPressure() : "0/0",
                        patient.getBloodOxygen(),
                        patient.getBloodGlucose());

                // Log the raw reading to the dashboard
                dashboard.logSensorReading(SENSOR_TYPE, patient, heartRate, UNIT);

                // If reading exceeds threshold — put patient in the risk queue for the doctor
                if (heartRate > RISK_THRESHOLD) {
                    try {
                        // put() will block if the queue is full — this is safe and intentional
                        riskQueue.put(new SensorReading(patient, heartRate, SENSOR_TYPE));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            try {
                /**
                 * REQ B: THREAD INFLUENCING
                 * sleep() controls the scanning pace — one full scan every 2 seconds
                 */
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("[HeartRateSensor] Sensor thread interrupted.");
                break;
            }
        }
    }
}
