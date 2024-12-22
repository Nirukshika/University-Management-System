package StudentManeger;


 // The module information for a student, including marks calculation.
public class Module {
    int[] marks; // Array to store module marks


     // Display an array for module marks.

    public Module() {
        this.marks = new int[3];
    }




    public int getMarks(int index) {
        return marks[index];
    }


     // Sets the marks

    public void setMarks(int index, int mark) {
        this.marks[index] = mark;
    }


     //Calculates  the total marks across all modules.

    public int getTotalMarks() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }


     // Calculates  the average marks across all modules.

    public double getAverageMarks() {
        return getTotalMarks() / 3.0; // Calculates average by dividing total marks by 3
    }
}
