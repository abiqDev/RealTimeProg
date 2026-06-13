package ProjectLatest;

import java.util.concurrent.BlockingQueue;

/**
 * REQ A: THREAD AND RUNNABLE OBJECTS
 * DoctorThread is a reusable doctor class. One instance is created for each sensor.
 * Each doctor watches its own BlockingQueue — when a sensor puts a risky patient
 * into the queue, the doctor immediately picks it up and prints a risk alert.
 *
 * This is the classic Producer-Consumer pattern:
 *   Sensor thread  = Producer  (puts risky readings INTO the queue)
 *   Doctor thread  = Consumer  (takes risky readings OUT of the queue)
 *
 * BlockingQueue handles all synchronization automatically —
 * the doctor thread simply blocks (waits) on take() until something arrives.
 */
public class DoctorThread implements Runnable {

    private final String                       doctorName; // e.g. "Dr. Heart Rate"
    private final BlockingQueue<SensorReading> riskQueue;  // the queue this doctor watches
    private final CentralHealthDashboard       dashboard;
    private final String                       unit;       // e.g. "bpm"
    private final String                       advice;     // medical advice to print
    private volatile boolean                   isRunning = true;

    public DoctorThread(String doctorName,
                        BlockingQueue<SensorReading> riskQueue,
                        CentralHealthDashboard dashboard,
                        String unit,
                        String advice) {
        this.doctorName = doctorName;
        this.riskQueue  = riskQueue;
        this.dashboard  = dashboard;
        this.unit       = unit;
        this.advice     = advice;
    }

    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                /**
                 * take() is the key method here.
                 * It BLOCKS — meaning the doctor thread pauses and waits here
                 * doing nothing until a SensorReading appears in the queue.
                 * The moment a sensor puts something in, take() returns it instantly.
                 * This is efficient — the doctor thread uses zero CPU while waiting.
                 */
                SensorReading reading = riskQueue.take();

                // Print the risk alert to the shared dashboard
                dashboard.logDoctorAlert(
                        doctorName,
                        reading.getPatient(),
                        reading.getReadingValue(),
                        unit,
                        advice
                );

            } catch (InterruptedException e) {
                // InterruptedException is thrown when stop() is called
                // and the thread is interrupted to wake it from take()
                System.out.println("[" + doctorName + "] Doctor thread shutting down.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
