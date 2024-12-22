package StudentManeger;

import java.io.*;
import java.util.*;


 // Main menu

public class Main {
    private static final int CAPACITY = 100; // Maximum number of students
    private static Student[] students = new Student[CAPACITY]; // Array to store students
    private static int studentCount = 0; // Current number of students

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadStudentDetails();//load student details for fille
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Check available seats");//check available seats
            System.out.println("2. Register student (with ID)");//register student
            System.out.println("3. Cancel student");//cancel student
            System.out.println("4. Find student (with student ID)");//find student
            System.out.println("5. Store student details into a file");//store data
            System.out.println("6. Load student details from the file to the system");//load data
            System.out.println("7. View the list of students based on their names");//view details
            System.out.println("8. Manage student results");//Manage student
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0 -> {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                case 1 -> checkAvailableSeats();
                case 2 -> registerStudent(scanner);
                case 3 -> cancelStudent(scanner);
                case 4 -> findStudent(scanner);
                case 5 -> storeStudentDetails();
                case 6 -> loadStudentDetails();
                case 7 -> sortAndViewStudents();
                case 8 -> manageStudentResults(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }


     // Displays how many number of available seats.

    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (CAPACITY - studentCount));
    }


    //Registers a new student.

    private static void registerStudent(Scanner scanner) {
        if (studentCount >= CAPACITY) {
            System.out.println("No available seats.");
            return;
        }

        String id;
        do {
            System.out.print("Enter student ID (1 letter + 7 digits): ");
            id = scanner.nextLine();
            if (!isValidStudentId(id)) {
                System.out.println("Invalid student ID format. Please try again.");
                id = null; // Repeat incorrect student ID
            }
        } while (id == null);

        String name;
        do {
            System.out.print("Enter student name: ");
            name = scanner.nextLine();
            if (!isValidName(name)) {
                System.out.println("Invalid name format. Only letters and spaces are allowed. Please try again.");
                name = null;
            }
        } while (name == null);

        students[studentCount++] = new Student(id, name);
        System.out.println("Student registered successfully.");
        storeStudentDetails();
    }


     //Validates the student ID format.

    private static boolean isValidStudentId(String id) {
        return id.matches("[A-Za-z][0-9]{7}");
    }


      //Checks if the student ID is a duplicate.

    private static boolean isDuplicateId(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


     // Validates the student name format.

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }


     //Cancel a student by ID.

    private static void cancelStudent(Scanner scanner) {
        System.out.print("Enter student ID to cancel: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                students[i] = students[--studentCount];
                students[studentCount] = null;
                System.out.println("Student cancel successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }


     // Finds a student by ID.

    private static void findStudent(Scanner scanner) {
        System.out.print("Enter student ID to find: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (Student student : students) {
            if (student != null && student.getId().equals(id)) {
                System.out.println("Student found: " + student);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }


     // Stores student details into a file.

    private static void storeStudentDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                if (isValidStudentId(students[i].getId()) && isValidName(students[i].getName())) {
                    writer.write(students[i].toFileString());
                    writer.newLine();
                }
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }


     // Loads student details from a file.

    private static void loadStudentDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            studentCount = 0;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromFileString(line);
                if (isValidStudentId(student.getId()) && isValidName(student.getName())) {
                    students[studentCount++] = student;
                }
            }
            System.out.println("Student details loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }


     //Sorts and displays students by name.

    private static void sortAndViewStudents() {
        Arrays.sort(students, 0, studentCount, Comparator.comparing(Student::getName));
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i]);
        }
    }


     // Manages student results including adding names, module marks, generating summaries, and reports.


    private static void manageStudentResults(Scanner scanner) {
        System.out.println("Manage Student Results:");
        System.out.println("a. Add student name");
        System.out.println("b. Add module marks");
        System.out.println("c. Generate summary");
        System.out.println("d. Generate complete report");
        System.out.print("Select an option: ");
        String option = scanner.next();
        scanner.nextLine();  //  newline after next()

        switch (option) {
            case "a" -> addStudentName(scanner);
            case "b" -> addModuleMarks(scanner);
            case "c" -> generateSummary();
            case "d" -> generateCompleteReport();
            default -> System.out.println("Invalid option. Please try again.");
        }
    }


     // Adds or updates the name of a student.

    private static void addStudentName(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (Student student : students) {
            if (student != null && student.getId().equals(id)) {
                String name;
                do {
                    System.out.print("Enter student name: ");
                    name = scanner.nextLine();
                    if (!isValidName(name)) {
                        System.out.println("Invalid name format. Only letters and spaces are allowed. Please try again.");
                        name = null;
                    }
                } while (name == null);
                student.setName(name);
                System.out.println("Student name updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }


     // Adds or updates module marks for a student.

    private static void addModuleMarks(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (Student student : students) {
            if (student != null && student.getId().equals(id)) {
                for (int i = 0; i < 3; i++) {
                    int marks;
                    do {
                        System.out.print("Enter marks for Module " + (i + 1) + " (0-100): ");
                        marks = scanner.nextInt();
                        if (marks < 0 || marks > 100) {
                            System.out.println("Invalid marks. Please enter a value between 0 and 100.");
                        }
                    } while (marks < 0 || marks > 100);
                    student.getModule().setMarks(i, marks);
                }
                student.calculateGrade();
                System.out.println("Module marks updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }


     // Generates a summary report .

    private static void generateSummary() {
        int totalRegistrations = studentCount;
        int[] modulePassCount = new int[3];
        for (Student student : students) {
            if (student != null) {
                for (int i = 0; i < 3; i++) {
                    if (student.getModule().getMarks(i) >= 40) {
                        modulePassCount[i]++;
                    }
                }
            }
        }
        System.out.println("Total student registrations: " + totalRegistrations);
        System.out.println("Total students passed in Module 1: " + modulePassCount[0]);
        System.out.println("Total students passed in Module 2: " + modulePassCount[1]);
        System.out.println("Total students passed in Module 3: " + modulePassCount[2]);
    }


      //Generates a complete report of all students.

    private static void generateCompleteReport() {
        Arrays.sort(students, 0, studentCount, Comparator.comparingDouble(Student::getAverage).reversed());
        for (Student student : students) {
            if (student != null) {
                System.out.println(student.fullDetails());
            }
        }
    }
}
