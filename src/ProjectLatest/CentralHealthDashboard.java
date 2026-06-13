package ProjectLatest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CentralHealthDashboard is the shared critical resource.
 * All sensor threads AND all doctor threads print through this one object.
 *
 * ReentrantLock with tryLock() prevents deadlock:
 * if the dashboard is busy, the thread backs off gracefully
 * instead of waiting forever.
 */
public class CentralHealthDashboard {

    private final ReentrantLock dashboardLock = new ReentrantLock();

    /**
     * Called by SENSOR threads to log a raw reading for a patient.
     */
    public void logSensorReading(String sensorType, Patient patient, double value, String unit) {
        try {
            if (dashboardLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.printf("[SENSOR - %-16s] Patient: %-25s | Reading: %.1f %s%n",
                            sensorType,
                            patient.getId() + " " + patient.getName(),
                            value,
                            unit);
                } finally {
                    dashboardLock.unlock(); // always unlock in finally — prevents permanent lock
                }
            } else {
                System.out.println("[LIVENESS WARNING] " + sensorType
                        + " sensor delayed for " + patient.getName() + ": Dashboard busy.");
            }
        } catch (InterruptedException e) {
            System.err.println("Sensor dashboard logging interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Called by DOCTOR threads when a risky patient is picked up from the queue.
     */
    public void logDoctorAlert(String doctorName, Patient patient,
                               double readingValue, String unit, String advice) {
        try {
            if (dashboardLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.printf("[DOCTOR  - %-16s] *** RISK ALERT *** Patient: %-20s | Value: %.1f %s | Action: %s%n",
                            doctorName,
                            patient.getId() + " " + patient.getName(),
                            readingValue,
                            unit,
                            advice);
                } finally {
                    dashboardLock.unlock();
                }
            } else {
                System.out.println("[LIVENESS WARNING] Doctor " + doctorName
                        + " alert delayed for " + patient.getName() + ": Dashboard busy.");
            }
        } catch (InterruptedException e) {
            System.err.println("Doctor dashboard logging interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
