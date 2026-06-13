package IndividualAssignment;

import java.util.concurrent.ConcurrentHashMap;

public class Task3_ConcurrentMap {
    public static void main(String[] args) {

        // Create ConcurrentHashMap: key = StudentID, value = CourseCode
        ConcurrentHashMap<String, String> registrationMap = new ConcurrentHashMap<>();

        // Add at least 50 student-course records
        registrationMap.put("307895", "STIWK3014");
        registrationMap.put("307001", "STIWK2114");
        registrationMap.put("307002", "STIWK3024");
        registrationMap.put("307003", "STIWK2124");
        registrationMap.put("307004", "STIWK3034");
        registrationMap.put("307005", "STIWK2134");
        registrationMap.put("307006", "STIWK3044");
        registrationMap.put("307007", "STIWK2144");
        registrationMap.put("307008", "STIWK3054");
        registrationMap.put("307009", "STIWK2154");
        registrationMap.put("307010", "STIWK3014");
        registrationMap.put("307011", "STIWK2114");
        registrationMap.put("307012", "STIWK3024");
        registrationMap.put("307013", "STIWK2124");
        registrationMap.put("307014", "STIWK3034");
        registrationMap.put("307015", "STIWK2134");
        registrationMap.put("307016", "STIWK3044");
        registrationMap.put("307017", "STIWK2144");
        registrationMap.put("307018", "STIWK3054");
        registrationMap.put("307019", "STIWK2154");
        registrationMap.put("307020", "STIWK3014");
        registrationMap.put("307021", "STIWK2114");
        registrationMap.put("307022", "STIWK3024");
        registrationMap.put("307023", "STIWK2124");
        registrationMap.put("307024", "STIWK3034");
        registrationMap.put("307025", "STIWK2134");
        registrationMap.put("307026", "STIWK3044");
        registrationMap.put("307027", "STIWK2144");
        registrationMap.put("307028", "STIWK3054");
        registrationMap.put("307029", "STIWK2154");
        registrationMap.put("307030", "STIWK3014");
        registrationMap.put("307031", "STIWK2114");
        registrationMap.put("307032", "STIWK3024");
        registrationMap.put("307033", "STIWK2124");
        registrationMap.put("307034", "STIWK3034");
        registrationMap.put("307035", "STIWK2134");
        registrationMap.put("307036", "STIWK3044");
        registrationMap.put("307037", "STIWK2144");
        registrationMap.put("307038", "STIWK3054");
        registrationMap.put("307039", "STIWK2154");
        registrationMap.put("307040", "STIWK3014");
        registrationMap.put("307041", "STIWK2114");
        registrationMap.put("307042", "STIWK3024");
        registrationMap.put("307043", "STIWK2124");
        registrationMap.put("307044", "STIWK3034");
        registrationMap.put("307045", "STIWK2134");
        registrationMap.put("307046", "STIWK3044");
        registrationMap.put("307047", "STIWK2144");
        registrationMap.put("307048", "STIWK3054");
        registrationMap.put("307049", "STIWK2154");
        registrationMap.put("307050", "STIWK3014");

        // Retrieve the course registered by a specific student
        System.out.println("===== RETRIEVE STUDENT COURSE =====");
        System.out.println("Course for student 307895: " + registrationMap.get("307895"));

        // Update (replace) the course of a student
        System.out.println("\n===== UPDATE STUDENT COURSE =====");
        System.out.println("Before update, course for 307895: " + registrationMap.get("307895"));
        registrationMap.replace("307895", "STIWK2124");
        System.out.println("After update, course for 307895: " + registrationMap.get("307895"));

        // Check whether a student ID exists
        System.out.println("\n===== CHECK STUDENT ID EXISTS =====");
        System.out.println("Student 307010 exists: " + registrationMap.containsKey("307010"));
        System.out.println("Student 307999 exists: " + registrationMap.containsKey("307999"));

        // Remove a student registration
        registrationMap.remove("307050");
        System.out.println("\n===== AFTER REMOVING STUDENT 307050 =====");
        System.out.println("Total registrations: " + registrationMap.size());
        System.out.println("Student 307050 exists now: " + registrationMap.containsKey("307050"));

        // Display all registrations
        System.out.println("\n===== ALL REGISTRATIONS =====");
        registrationMap.forEach((studentID, courseCode) ->
                System.out.println("Student ID: " + studentID + " -> Course: " + courseCode)
        );
    }
}
