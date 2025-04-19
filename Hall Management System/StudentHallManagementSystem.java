import java.util.*;

// Abstract class for abstraction
abstract class User {
    private String name;
    private String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Encapsulation with getters and setters
    public String getName() { return name; }
    public String getId() { return id; }
    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }

    // Abstract method for polymorphism
    public abstract String getDetails();

    @Override
    public String toString() {
        return getDetails();
    }
}

// Inheritance: Student is a type of User
class Student extends User {
    private String hallRoomNumber;

    public Student(String name, String studentId) {
        super(name, studentId);
        this.hallRoomNumber = null;
    }

    public String getHallRoomNumber() {
        return hallRoomNumber;
    }

    public void assignRoom(String room) {
        this.hallRoomNumber = room;
    }

    public void cancelRoom() {
        this.hallRoomNumber = null;
    }

    // Polymorphic implementation of abstract method
    @Override
    public String getDetails() {
        return "Student Name: " + getName() +", ID: " + getId() + ", Room: " + (hallRoomNumber != null ?hallRoomNumber : "Not Assigned");
    }
}

// Hall class managing students and rooms
class Hall {
    private List<String> availableRooms;
    private List<User> users;
    private List<String> maintenanceRequests;

    public Hall() {
        availableRooms = new ArrayList<>();
        users = new ArrayList<>();
        maintenanceRequests = new ArrayList<>();

        // Add initial rooms
        for (int i = 1; i <= 5; i++) {
            availableRooms.add("Room " + i);
        }
    }

    public void registerStudent(String name, String id) {
        users.add(new Student(name, id));
        System.out.println("Student registered successfully.");
    }

    public void assignRoom(Student student) {
        if (!availableRooms.isEmpty()) {
            String room = availableRooms.remove(0);
            student.assignRoom(room);
            System.out.println("Assigned " + room + " to " + student.getName());
        } else {
            System.out.println("No rooms available.");
        }
    }

    public void cancelRoom(Student student) {
        if (student.getHallRoomNumber() != null) {
            availableRooms.add(student.getHallRoomNumber());
            student.cancelRoom();
            System.out.println("Room canceled for " + student.getName());
        } else {
            System.out.println("No room assigned to this student.");
        }
    }

    public void requestMaintenance(String room) {
        maintenanceRequests.add("Maintenance requested for " + room);
        System.out.println("Maintenance request submitted for " + room);
    }

    public void showStudents() {
        boolean found = false;
        for (User user : users) {
            if (user instanceof Student) {
                System.out.println(user);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No students found.");
        }
    }

    public void showAvailableRooms() {
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            System.out.println("Available rooms:");
            for (String room : availableRooms) {
                System.out.println(room);
            }
        }
    }

    public void showMaintenanceRequests() {
        if (maintenanceRequests.isEmpty()) {
            System.out.println("No maintenance requests.");
        } else {
            System.out.println("Maintenance Requests:");
            for (String req : maintenanceRequests) {
                System.out.println(req);
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }
}

// Main class with menu
public class StudentHallManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hall hall = new Hall();

        while (true) {
            System.out.println("\n===== Daffodil Student Hall Management System =====");
            System.out.println("1. Register Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Show Available Rooms");
            System.out.println("4. Assign Room");
            System.out.println("5. Request for Maintenance");
            System.out.println("6. Cancel Room");
            System.out.println("7. Show Maintenance Requests");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    hall.registerStudent(name, id);
                    break;

                case 2:
                    hall.showStudents();
                    break;

                case 3:
                    hall.showAvailableRooms();
                    break;

                case 4:
                    System.out.print("Enter student ID to assign room: ");
                    String idToAssign = scanner.nextLine();
                    Student studentToAssign = findStudentById(hall, idToAssign);
                    if (studentToAssign != null) {
                        hall.assignRoom(studentToAssign);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter room for maintenance request: ");
                    String room = scanner.nextLine();
                    hall.requestMaintenance(room);
                    break;

                case 6:
                    System.out.print("Enter student ID to cancel room: ");
                    String idToCancel = scanner.nextLine();
                    Student studentToCancel = findStudentById(hall, idToCancel);
                    if (studentToCancel != null) {
                        hall.cancelRoom(studentToCancel);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 7:
                    hall.showMaintenanceRequests();
                    break;

                case 8:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Helper method to find student using polymorphism
    public static Student findStudentById(Hall hall, String id) {
        for (User user : hall.getUsers()) {
            if (user instanceof Student && user.getId().equals(id)) {
                return (Student) user;
            }
        }
        return null;
    }
}