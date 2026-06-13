package IndividualAssignment;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Task2_ConcurrentSet {
    public static void main(String[] args) {

        // Create a Concurrent Set
        Set<String> courseCodes = new CopyOnWriteArraySet<>();

        // Add course codes into the Set
        courseCodes.add("STIWK3014");
        courseCodes.add("STIWK2114");
        courseCodes.add("STIWK3024");
        courseCodes.add("STIWK2124");
        courseCodes.add("STIWK3034");
        courseCodes.add("STIWK2134");
        courseCodes.add("STIWK3044");
        courseCodes.add("STIWK2144");
        courseCodes.add("STIWK3054");
        courseCodes.add("STIWK2154");

        // Attempt to add duplicate course codes
        System.out.println("===== ATTEMPTING TO ADD DUPLICATES =====");
        System.out.println("Adding duplicate STIWK3014: " + courseCodes.add("STIWK3014"));
        System.out.println("Adding duplicate STIWK2124: " + courseCodes.add("STIWK2124"));
        System.out.println("Adding duplicate STIWK3034: " + courseCodes.add("STIWK3034"));

        // Display all unique course codes
        System.out.println("\n===== ALL UNIQUE COURSE CODES =====");
        for (String code : courseCodes) {
            System.out.println(code);
        }

        // Check whether a course code exists
        System.out.println("\n===== CHECK COURSE CODE EXISTS =====");
        System.out.println("Contains STIWK3014: " + courseCodes.contains("STIWK3014"));
        System.out.println("Contains STIWK9999: " + courseCodes.contains("STIWK9999"));

        // Remove one course code
        courseCodes.remove("STIWK2154");
        System.out.println("\n===== AFTER REMOVING STIWK2154 =====");
        System.out.println("Total unique course codes: " + courseCodes.size());
        System.out.println("Contains STIWK2154 now: " + courseCodes.contains("STIWK2154"));
    }
}
