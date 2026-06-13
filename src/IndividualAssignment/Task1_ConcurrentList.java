package IndividualAssignment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Task1_ConcurrentList {
    public static void main(String[] args) {

        // Create CopyOnWriteArrayList
        List<String> registrations = new CopyOnWriteArrayList<>();

        // Add at least 50 course registration records
        // Format: StudentID | Student Name | Course Code
        registrations.add("307895 | Abiq | STIWK3014");
        registrations.add("307001 | Ali Hassan | STIWK3014");
        registrations.add("307002 | Siti Aisyah | STIWK2114");
        registrations.add("307003 | Ahmad Farid | STIWK3024");
        registrations.add("307004 | Nurul Hidayah | STIWK2124");
        registrations.add("307005 | Mohamad Zikri | STIWK3014");
        registrations.add("307006 | Fatin Izzati | STIWK2114");
        registrations.add("307007 | Hafiz Ramli | STIWK3024");
        registrations.add("307008 | Syafiqah Noor | STIWK2124");
        registrations.add("307009 | Izzatul Iman | STIWK3014");
        registrations.add("307010 | Razif Danial | STIWK2114");
        registrations.add("307011 | Amirul Haziq | STIWK3014");
        registrations.add("307012 | Liyana Sofea | STIWK2124");
        registrations.add("307013 | Farah Nabilah | STIWK3024");
        registrations.add("307014 | Asyraf Wajdi | STIWK2114");
        registrations.add("307015 | Hana Maisarah | STIWK3014");
        registrations.add("307016 | Irfan Syafiq | STIWK2124");
        registrations.add("307017 | Zulaikha Husna | STIWK3024");
        registrations.add("307018 | Ridhwan Aziz | STIWK2114");
        registrations.add("307019 | Nabila Husna | STIWK3014");
        registrations.add("307020 | Khairul Fitri | STIWK2124");
        registrations.add("307021 | Dini Athirah | STIWK3014");
        registrations.add("307022 | Faris Mukhtar | STIWK2114");
        registrations.add("307023 | Iman Taqwa | STIWK3024");
        registrations.add("307024 | Sufi Annas | STIWK2124");
        registrations.add("307025 | Rania Irdina | STIWK3014");
        registrations.add("307026 | Hazwan Haikal | STIWK2114");
        registrations.add("307027 | Arina Zafira | STIWK3024");
        registrations.add("307028 | Luqman Hakim | STIWK2124");
        registrations.add("307029 | Yasmin Najwa | STIWK3014");
        registrations.add("307030 | Aiman Syahir | STIWK2114");
        registrations.add("307031 | Nadia Qistina | STIWK3014");
        registrations.add("307032 | Harith Zafran | STIWK2124");
        registrations.add("307033 | Batrisyia Nur | STIWK3024");
        registrations.add("307034 | Suffian Zafir | STIWK2114");
        registrations.add("307035 | Malak Maisara | STIWK3014");
        registrations.add("307036 | Uwais Rafiq | STIWK2124");
        registrations.add("307037 | Ruqayyah Sifa | STIWK3024");
        registrations.add("307038 | Asif Danial | STIWK2114");
        registrations.add("307039 | Zara Hannani | STIWK3014");
        registrations.add("307040 | Wafi Muadz | STIWK2124");
        registrations.add("307041 | Aisyah Qurratu | STIWK3014");
        registrations.add("307042 | Danish Haziq | STIWK2114");
        registrations.add("307043 | Farhah Nisa | STIWK3024");
        registrations.add("307044 | Azim Mujahid | STIWK2124");
        registrations.add("307045 | Insyirah Hana | STIWK3014");
        registrations.add("307046 | Hafiy Zakwan | STIWK2114");
        registrations.add("307047 | Maryam Widad | STIWK3024");
        registrations.add("307048 | Raihan Fahmi | STIWK2124");
        registrations.add("307049 | Sofiya Irdina | STIWK3014");
        registrations.add("307050 | Ziyad Hamzah | STIWK2114");

        // Display all registration records
        System.out.println("===== ALL REGISTRATION RECORDS =====");
        for (String record : registrations) {
            System.out.println(record);
        }

        // Display the total number of registration requests
        System.out.println("\nTotal registration requests: " + registrations.size());

        // Check whether a specific course exists in the list
        String searchCourse = "STIWK3014";
        boolean exists = registrations.stream().anyMatch(r -> r.contains(searchCourse));
        System.out.println("\nCourse " + searchCourse + " exists in list: " + exists);

        // Using contains() to check an exact record
        boolean directContains = registrations.contains("307895 | Abiq | STIWK3014");
        System.out.println("Exact record contains (307895 | Abiq | STIWK3014): " + directContains);

        // Retrieve and display the registration record at index position 10
        System.out.println("\nRegistration record at index 10: " + registrations.get(10));
    }
}
