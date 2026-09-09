import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {

        // Create Student
        Student student = new Student(
                "S001",
                "Niharika",
                "niharika@example.com",
                "CSE",
                3
        );

        // Create Teacher
        Teacher teacher = new Teacher(
                "T001",
                "Dr. Anil Kumar",
                "anil@example.com",
                "CSE",
                "Object Oriented Programming"
        );

        // Create Course
        Course course = new Course(
                "CSE201",
                "Object Oriented Programming",
                4
        );

        // Create Classroom
        Classroom classroom = new Classroom(
                "B201",
                60,
                "Main Block"
        );

        // Display basic details
        student.displayDetails();
        teacher.displayDetails();
        course.displayCourse();
        classroom.displayClassroom();

        // Create Syllabus
        Syllabus syllabus = new Syllabus(course);

        syllabus.addModule("Introduction to OOP");
        syllabus.addModule("Classes and Objects");
        syllabus.addModule("Inheritance");
        syllabus.addModule("Polymorphism");
        syllabus.addModule("Exception Handling");

        syllabus.displaySyllabus();

        // Create Assignment
        Assignment assignment = new Assignment(
                "OOP Assignment 1",
                "Implement inheritance using Java.",
                "15-09-2026",
                course
        );

        assignment.displayAssignment();

        // Create Attendance
        Attendance attendance = new Attendance(student, course);

        attendance.markAttendance(true);
        attendance.markAttendance(true);
        attendance.markAttendance(false);
        attendance.markAttendance(true);

        attendance.displayAttendance();

        // Create Marks
        Marks marks = new Marks(
                student,
                course,
                85,
                100
        );

        marks.displayMarks();

        // Create Class Session
        ClassSession session = new ClassSession(
                course,
                teacher,
                classroom,
                "07-09-2026",
                "10:00 AM",
                "11:00 AM"
        );

        session.displaySession();

        // Test StudentService
        StudentService studentService = new StudentService();

        studentService.addStudent(student);
        studentService.displayAllStudents();

        // Test TeacherService
        TeacherService teacherService = new TeacherService();

        teacherService.addTeacher(teacher);
        teacherService.displayAllTeachers();

        // Test CourseService
        CourseService courseService = new CourseService();

        courseService.addCourse(course);
        courseService.displayAllCourses();

        // Test AssignmentService
        AssignmentService assignmentService = new AssignmentService();

        assignmentService.addAssignment(assignment);
        assignmentService.displayAllAssignments();

        // Test AttendanceService
        AttendanceService attendanceService = new AttendanceService();

        attendanceService.addAttendanceRecord(attendance);
        attendanceService.displayAllAttendance();

        // Test MarksService
        MarksService marksService = new MarksService();

        marksService.addMarks(marks);
        marksService.displayAllMarks();

        // Test ClassroomService
        ClassroomService classroomService = new ClassroomService();

        classroomService.addClassroom(classroom);
        classroomService.displayAllClassrooms();

        // Test ClassSessionService
        ClassSessionService sessionService = new ClassSessionService();

        sessionService.addSession(session);
        sessionService.displayAllSessions();

        System.out.println("\n===== SCMS FOUNDATION TEST COMPLETED =====");
    }
}