package model;

public class Classroom {

    private String roomNumber;
    private int capacity;
    private String building;

    public Classroom(String roomNumber, int capacity, String building) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.building = building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void displayClassroom() {
        System.out.println("\n===== CLASSROOM =====");
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Building: " + building);
        System.out.println("Capacity: " + capacity);
    }
}