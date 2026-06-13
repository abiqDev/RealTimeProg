package Exe4;


public class HospitalRegistration {
    private static int totalPatients = 0; // Shared variable

    public static void main(String[] args) throws InterruptedException {
        Runnable registerPatient = () -> {
            for (int i = 0; i < 100000; i++) {
                totalPatients++; // Race condition happens here
            }
        };

        Thread counter1 = new Thread(registerPatient, "Counter-1");
        Thread counter2 = new Thread(registerPatient, "Counter-2");

        counter1.start();
        counter2.start();

        counter1.join();
        counter2.join();

        System.out.println("Total Registered Patients: " + totalPatients);
    }
}
