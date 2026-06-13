package Week11;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentLab {
    static List<String> activeStudents = new CopyOnWriteArrayList<>();
    static Set<String> submittedStudents = new ConcurrentSkipListSet<>();
    static Map<String, Integer> studentMarks = new ConcurrentHashMap<>();
    static BlockingQueue<String> submissionQueue = new LinkedBlockingQueue<>();
    public static void main(String[] args) {
        Runnable studentTask = () -> {
            String name = Thread.currentThread().getName();

            activeStudents.add(name);
            System.out.println(name + " joined the class");

            submissionQueue.add(name);
            submittedStudents.add(name);
            System.out.println(name + " submitted assignment");
        };

        Runnable lecturerTask = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String student = submissionQueue.take();
                    studentMarks.put(student, 100);
                    System.out.println("Lecturer graded " + student);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } };

        for (int i = 1; i <= 10; i++) {
            new Thread(studentTask, "Student-" + i).start();
        }

        new Thread(lecturerTask).start();
    }}