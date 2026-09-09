package model;

public class Teacher extends Person {

    private String specialization;

    public Teacher(String id, String name, String email,
                   String department, String specialization) {

        super(id, name, email, department);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Teacher Details ---");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + getDepartment());
        System.out.println("Specialization: " + specialization);
    }
}