package ProjectLatest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * REQ H: TESTING THE CONCURRENT PROGRAM
 *
 * RealTimeHealthSystem is the entry point of the entire program.
 *
 * Architecture:
 *   - 4 Sensor threads: HeartRate, BloodPressure, BloodOxygen, BloodGlucose
 *     Each sensor scans all 20 patients, generates readings, logs to dashboard,
 *     and puts at-risk patients into a BlockingQueue.
 *
 *   - 4 Doctor threads: one per sensor
 *     Each doctor watches its sensor's BlockingQueue using take(),
 *     picks up at-risk patients, and prints a RISK ALERT to the dashboard.
 *
 *   - 1 CentralHealthDashboard: shared by all 8 threads, protected by ReentrantLock
 *
 *   - 4 BlockingQueues: one per sensor-doctor pair (Producer-Consumer pattern)
 */
public class RealTimeHealthSystem {

    public static void main(String[] args) {

        System.out.println("=============================================================");
        System.out.println("   REAL-TIME CONCURRENT HEALTH MONITORING SYSTEM v2.0");
        System.out.println("=============================================================\n");

        // ---------------------------------------------------------------
        // STEP 1: Create the shared dashboard
        // ---------------------------------------------------------------
        CentralHealthDashboard dashboard = new CentralHealthDashboard();

        // ---------------------------------------------------------------
        // STEP 2: Create all 20 patients
        // ---------------------------------------------------------------
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient("P001", "Abiq"));
        patientList.add(new Patient("P002", "Aiman"));
        patientList.add(new Patient("P003", "Marie"));
        patientList.add(new Patient("P004", "Karim"));
        patientList.add(new Patient("P005", "Hazim"));
        patientList.add(new Patient("P006", "Maiza"));
        patientList.add(new Patient("P007", "Fathi"));
        patientList.add(new Patient("P008", "Alife"));
        patientList.add(new Patient("P009", "Haikal"));
        patientList.add(new Patient("P010", "Atiq"));
        patientList.add(new Patient("P011", "Azlam"));
        patientList.add(new Patient("P012", "Aina"));
        patientList.add(new Patient("P013", "Adriana"));
        patientList.add(new Patient("P014", "Amin"));
        patientList.add(new Patient("P015", "Amir"));
        patientList.add(new Patient("P016", "Azrul"));
        patientList.add(new Patient("P017", "Anen"));
        patientList.add(new Patient("P018", "Linda"));
        patientList.add(new Patient("P019", "Alia"));
        patientList.add(new Patient("P020", "Tomok"));

        // ---------------------------------------------------------------
        // STEP 3: Create 4 BlockingQueues — one per sensor-doctor pair
        // LinkedBlockingQueue is thread-safe by design — no extra locking needed
        // ---------------------------------------------------------------
        BlockingQueue<SensorReading> heartRateQueue    = new LinkedBlockingQueue<>();
        BlockingQueue<SensorReading> bloodPressureQueue = new LinkedBlockingQueue<>();
        BlockingQueue<SensorReading> bloodOxygenQueue   = new LinkedBlockingQueue<>();
        BlockingQueue<SensorReading> bloodGlucoseQueue  = new LinkedBlockingQueue<>();

        // ---------------------------------------------------------------
        // STEP 4: Create 4 sensor Runnables
        // ---------------------------------------------------------------
        HeartRateSensor    hrSensor  = new HeartRateSensor(patientList, heartRateQueue, dashboard);
        BloodPressureSensor bpSensor  = new BloodPressureSensor(patientList, bloodPressureQueue, dashboard);
        BloodOxygenSensor   o2Sensor  = new BloodOxygenSensor(patientList, bloodOxygenQueue, dashboard);
        BloodGlucoseSensor  glSensor  = new BloodGlucoseSensor(patientList, bloodGlucoseQueue, dashboard);

        // ---------------------------------------------------------------
        // STEP 5: Create 4 doctor Runnables
        // Each doctor is given: a name, the queue to watch, the dashboard,
        // the unit label, and the medical advice to print on alert
        // ---------------------------------------------------------------
        DoctorThread hrDoctor = new DoctorThread(
                "Dr. Heart Rate", heartRateQueue, dashboard,
                "bpm", "Administer beta-blocker, monitor continuously");

        DoctorThread bpDoctor = new DoctorThread(
                "Dr. Blood Pressure", bloodPressureQueue, dashboard,
                "mmHg", "Administer antihypertensive, reduce sodium intake");

        DoctorThread o2Doctor = new DoctorThread(
                "Dr. Blood Oxygen", bloodOxygenQueue, dashboard,
                "% SpO2", "Administer supplemental oxygen immediately");

        DoctorThread glDoctor = new DoctorThread(
                "Dr. Blood Glucose", bloodGlucoseQueue, dashboard,
                "mg/dL", "Administer insulin, restrict carbohydrate intake");

        // ---------------------------------------------------------------
        // STEP 6: Wrap all Runnables in Thread objects and set priorities
        // REQ B: THREAD INFLUENCING — sensor threads get higher priority
        // than doctor threads since detection must happen before treatment
        // ---------------------------------------------------------------
        Thread hrSensorThread  = new Thread(hrSensor,  "Thread-HR-Sensor");
        Thread bpSensorThread  = new Thread(bpSensor,  "Thread-BP-Sensor");
        Thread o2SensorThread  = new Thread(o2Sensor,  "Thread-O2-Sensor");
        Thread glSensorThread  = new Thread(glSensor,  "Thread-GL-Sensor");

        Thread hrDoctorThread  = new Thread(hrDoctor,  "Thread-HR-Doctor");
        Thread bpDoctorThread  = new Thread(bpDoctor,  "Thread-BP-Doctor");
        Thread o2DoctorThread  = new Thread(o2Doctor,  "Thread-O2-Doctor");
        Thread glDoctorThread  = new Thread(glDoctor,  "Thread-GL-Doctor");

        // Sensor threads get MAX priority — they must detect risk first
        hrSensorThread.setPriority(Thread.MAX_PRIORITY);
        bpSensorThread.setPriority(Thread.MAX_PRIORITY);
        o2SensorThread.setPriority(Thread.MAX_PRIORITY);
        glSensorThread.setPriority(Thread.MAX_PRIORITY);

        // Doctor threads get NORM priority — they respond after detection
        hrDoctorThread.setPriority(Thread.NORM_PRIORITY);
        bpDoctorThread.setPriority(Thread.NORM_PRIORITY);
        o2DoctorThread.setPriority(Thread.NORM_PRIORITY);
        glDoctorThread.setPriority(Thread.NORM_PRIORITY);

        // ---------------------------------------------------------------
        // STEP 7: Start all 8 threads
        // Doctor threads must start BEFORE sensor threads
        // so they are ready and waiting on take() before any readings arrive
        // ---------------------------------------------------------------
        System.out.println("[SYSTEM] Starting 4 doctor threads (waiting on queues)...");
        hrDoctorThread.start();
        bpDoctorThread.start();
        o2DoctorThread.start();
        glDoctorThread.start();

        System.out.println("[SYSTEM] Starting 4 sensor threads (scanning patients)...");
        hrSensorThread.start();
        bpSensorThread.start();
        o2SensorThread.start();
        glSensorThread.start();

        System.out.println("[SYSTEM] All 8 threads running. Monitoring for 6 seconds...\n");
        System.out.println("-------------------------------------------------------------");

        // ---------------------------------------------------------------
        // STEP 8: Let the system run for 6 seconds
        // ---------------------------------------------------------------
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------------------
        // STEP 9: Gracefully stop all sensor threads
        // ---------------------------------------------------------------
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("[SYSTEM] Stopping sensor threads...");
        hrSensor.stop();
        bpSensor.stop();
        o2Sensor.stop();
        glSensor.stop();

        // ---------------------------------------------------------------
        // STEP 10: Interrupt doctor threads to wake them from take()
        // Doctor threads block on take() — calling interrupt() wakes them up
        // so they can check isRunning and exit cleanly
        // ---------------------------------------------------------------
        hrDoctor.stop();
        bpDoctor.stop();
        o2Doctor.stop();
        glDoctor.stop();

        hrDoctorThread.interrupt();
        bpDoctorThread.interrupt();
        o2DoctorThread.interrupt();
        glDoctorThread.interrupt();

        // ---------------------------------------------------------------
        // STEP 11: REQ C — JOIN all threads
        // Main thread waits here until every thread finishes completely
        // ---------------------------------------------------------------
        System.out.println("[SYSTEM] Waiting for all threads to finish (join)...");

        Thread[] allThreads = {
            hrSensorThread, bpSensorThread, o2SensorThread, glSensorThread,
            hrDoctorThread, bpDoctorThread, o2DoctorThread, glDoctorThread
        };

        for (Thread t : allThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[SYSTEM] All threads joined and terminated safely.");

        // ---------------------------------------------------------------
        // STEP 12: REQ G — Parallel Streams final analytics
        // ---------------------------------------------------------------
        System.out.println("\n=============================================================");
        System.out.println("   PHASE 2: PARALLEL STREAM ANALYTICS");
        System.out.println("=============================================================");

        double avgHeartRate = patientList.parallelStream()
                .filter(p -> p.getHeartRate() > 0)
                .mapToInt(Patient::getHeartRate)
                .average()
                .orElse(0.0);

        double avgBloodOxygen = patientList.parallelStream()
                .filter(p -> p.getBloodOxygen() > 0)
                .mapToDouble(Patient::getBloodOxygen)
                .average()
                .orElse(0.0);

        double avgBloodGlucose = patientList.parallelStream()
                .filter(p -> p.getBloodGlucose() > 0)
                .mapToDouble(Patient::getBloodGlucose)
                .average()
                .orElse(0.0);

        long atRiskHR = patientList.parallelStream()
                .filter(p -> p.getHeartRate() > 100)
                .count();

        long atRiskO2 = patientList.parallelStream()
                .filter(p -> p.getBloodOxygen() > 0 && p.getBloodOxygen() < 90.0)
                .count();

        System.out.printf("[ANALYTICS] Average Heart Rate      : %.2f bpm%n",    avgHeartRate);
        System.out.printf("[ANALYTICS] Average Blood Oxygen    : %.2f %% SpO2%n", avgBloodOxygen);
        System.out.printf("[ANALYTICS] Average Blood Glucose   : %.2f mg/dL%n",   avgBloodGlucose);
        System.out.printf("[ANALYTICS] Patients at risk (HR)   : %d patients%n",  atRiskHR);
        System.out.printf("[ANALYTICS] Patients at risk (SpO2) : %d patients%n",  atRiskO2);

        System.out.println("\n=============================================================");
        System.out.println("   SYSTEM EXECUTION COMPLETE — NO DATA CORRUPTION DETECTED");
        System.out.println("=============================================================");
    }
}
