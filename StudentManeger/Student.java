package StudentManeger;

import java.util.Arrays;


 // Represents a student with an ID, name, module information, and grade.

public class Student {
    private String id; // Student ID
    private String name; // Student name
    private Module module; // Module information
    private String grade; // Grade based on module marks


     // student with ID and name.

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.module = new Module();
    }


     //Gets the student ID.

    public String getId() {
        return id;
    }


     // Gets the student name.

    public String getName() {
        return name;
    }


     //Sets the student name.

    public void setName(String name) {
        this.name = name;
    }


     // module information.

    public Module getModule() {
        return module;
    }


     //Calculates the grade based on the average module marks.

    public void calculateGrade() {
        double average = module.getAverageMarks();
        if (average >= 80) {
            this.grade = "Distinction";
        } else if (average >= 70) {
            this.grade = "Merit";
        } else if (average >= 40) {
            this.grade = "Pass";
        } else {
            this.grade = "Fail";
        }
    }


     // average marks of the student.

    public double getAverage() {
        return module.getAverageMarks();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", moduleMarks=" + Arrays.toString(module.marks) +
                ", grade='" + grade + '\'' +
                '}';
    }




    public String toFileString() {
        return id + "," + name + "," + Arrays.toString(module.marks).replaceAll("[\\[\\] ]", "") + "," + grade;
    }


     //Creates a Student object from a file string.

    public static Student fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        Student student = new Student(parts[0], parts[1]);
        for (int i = 0; i < 3; i++) {
            student.getModule().setMarks(i, Integer.parseInt(parts[i + 2]));
        }
        student.calculateGrade();
        return student;
    }


     //  Full details of the student.

    public String fullDetails() {
        return "ID: " + id + ", Name: " + name +
                ", Marks: " + Arrays.toString(module.marks) +
                ", Total: " + module.getTotalMarks() +
                ", Average: " + module.getAverageMarks() +
                ", Grade: " + grade;
    }
}
