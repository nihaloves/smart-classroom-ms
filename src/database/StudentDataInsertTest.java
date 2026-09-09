package database;

import model.Student;

public class StudentDataInsertTest {

    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        System.out.println("===== INSERTING STUDENT DATA =====");

        addStudent(studentDAO, "TL25BTCS0545", "JASMIN R A");
        addStudent(studentDAO, "TL25BTCS0393", "JASNA FATHIMA V U");
        addStudent(studentDAO, "TL25BTCS0010", "JEEVAN GEO VADAKKEN");
        addStudent(studentDAO, "TL25BTCS0546", "JEEVA SAJEESH");
        addStudent(studentDAO, "TL25BTCS0102", "JERIL M REJI");
        addStudent(studentDAO, "TL25BTCS0173", "JESNALIN ROSE JAISON");
        addStudent(studentDAO, "TL25BTCS0626", "JESWIN GEO VADAKKEN");
        addStudent(studentDAO, "TL25BTCS0565", "JITHIN K J");
        addStudent(studentDAO, "TL25BTCS0161", "JIYA CHEERAN JILSON");
        addStudent(studentDAO, "TL25BTCS0085", "JOEL JOHNSON");
        addStudent(studentDAO, "TL25BTCS0566", "JOEL JOHNSON");
        addStudent(studentDAO, "TL25BTCS0574", "JOSHUA SUDHISH");
        addStudent(studentDAO, "TL25BTCS0333", "JOSWIN DAVIS");
        addStudent(studentDAO, "TL25BTCS0589", "JOTHAM JERRY KANNATH");
        addStudent(studentDAO, "TL25BTCS0581", "JOYAL JOSE T");
        addStudent(studentDAO, "TL25BTCS0039", "KIRAN KANJULLY MURALI");
        addStudent(studentDAO, "TL25BTCS0314", "K M ANANDHAKRISHNAN");
        addStudent(studentDAO, "TL25BTCS0607", "KRISHNADEV R");
        addStudent(studentDAO, "TL25BTCS0280", "K R MEDHA");
        addStudent(studentDAO, "TL25BTCS0311", "K SANJU");
        addStudent(studentDAO, "TL25BTCS0134", "LAKSHMIKRISHNA P R");
        addStudent(studentDAO, "TL25BTCS0571", "LAKSHMI NANDHA K B");
        addStudent(studentDAO, "TL25BTCS0208", "LAKSHMI PARVATHI A S");
        addStudent(studentDAO, "TL25BTCS0608", "LANSON C JOSE");
        addStudent(studentDAO, "TL25BTCS0271", "LENA JOSEPH");
        addStudent(studentDAO, "TL25BTCS0398", "MADHAV SURESH");
        addStudent(studentDAO, "TL25BTCS0337", "MAHESWAR K S");
        addStudent(studentDAO, "TL25BTCS0191", "MANOMIKA C R");
        addStudent(studentDAO, "TL25BTCS0569", "MANUJITH SHIJU");
        addStudent(studentDAO, "TL25BTCS0219", "MAYA E R");
        addStudent(studentDAO, "TL25BTCS0049", "MEENAKSHI E S");
        addStudent(studentDAO, "TL25BTCS0325", "MEERA KRISHNA");
        addStudent(studentDAO, "TL25BTCS0212", "MEGHA C N");
        addStudent(studentDAO, "TL25BTCS0388", "MINERVA T K");
        addStudent(studentDAO, "TL25BTCS0576", "MOHAMED SHIBIL P J");
        addStudent(studentDAO, "TL25BTCS0348", "MOHAMMED IRFAN MOHAMMED IBRAHIM");
        addStudent(studentDAO, "TL25BTCS0036", "MOHAMMED SALIH MUSLIMVEETTIL");
        addStudent(studentDAO, "TL25BTCS0359", "MOHAMMED SINAN SHARAFUDHEEN");
        addStudent(studentDAO, "TL25BTCS0340", "MOKSHADA V");
        addStudent(studentDAO, "TL25BTCS0380", "MUHAMMAD SHINAF K R");
        addStudent(studentDAO, "TL25BTCS0579", "MUHAMMED ALTHAF M A");
        addStudent(studentDAO, "TL25BTCS0625", "MUHAMMED SHIFAN");
        addStudent(studentDAO, "TL25BTCS0211", "NAFRIN NAWSHAD");
        addStudent(studentDAO, "TL25BTCS0605", "NAJID AMAN KUNNATH MUJIBURAHIMAN");
        addStudent(studentDAO, "TL25BTCS0599", "NANDAKISHORE S");
        addStudent(studentDAO, "TL25BTCS0355", "NANDANA K S");
        addStudent(studentDAO, "TL25BTCS0386", "NANDANA R NAIR");
        addStudent(studentDAO, "TL25BTCS0326", "NANDHANA A B");
        addStudent(studentDAO, "TL25BTCS0580", "NARENDRANATH P S");
        addStudent(studentDAO, "TL25BTCS0111", "NAVANEETH RAGHUNADHAN");
        addStudent(studentDAO, "TL25BTCS0160", "NAVITA P");
        addStudent(studentDAO, "TL25BTCS0396", "NAZEER AHMED");
        addStudent(studentDAO, "TL25BTCS0394", "NAZLA MANAL A");
        addStudent(studentDAO, "TL25BTCS0224", "NAZREEN AL NOOR");
        addStudent(studentDAO, "TL25BTCS0166", "NIBIL SABU");
        addStudent(studentDAO, "TL25BTCS0591", "NIDHI SAJAN");
        addStudent(studentDAO, "TL25BTCS0214", "NIHARIKA K THILAK");
        addStudent(studentDAO, "TL25BTCS0590", "NIJA SULTHANA K");
        addStudent(studentDAO, "TL25BTCS0300", "NIKHIL KRISHNAN V");
        addStudent(studentDAO, "TL25BTCS0647", "ABHINAV P S");
        addStudent(studentDAO, "TL25BTCS0632", "ALOSHYA K R");
        addStudent(studentDAO, "TL25BTCS0658", "AMALKRISHNA K H");
        addStudent(studentDAO, "TL25BTCS0645", "DARSANA T S");
        addStudent(studentDAO, "TL25BTCS0654", "DEVAPRIYA V J");
        addStudent(studentDAO, "TL25BTCS0641", "STEEVO C S");
        addStudent(studentDAO, "TL25BTCS0677", "UMMUHABEEBA K A");

        System.out.println("\n===== STUDENT DATA INSERTION COMPLETED =====");

        studentDAO.displayStudents();
    }

    private static void addStudent(StudentDAO studentDAO,
                                    String id,
                                    String name) {

        String email = id.toLowerCase() + "@vidyaacademy.ac.in";

        Student student = new Student(
                id,
                name,
                email,
                "CSE",
                3
        );

        studentDAO.addStudent(student);
    }
}