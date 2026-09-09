package model;

public class Student extends Person {

    private int semester;

    public Student(String id, String name, String email,
                   String department, int semester) {

        super(id, name, email, department);
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Student Details ---");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + getDepartment());
        System.out.println("Semester: " + semester);
    }
}