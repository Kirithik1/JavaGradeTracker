import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


class Student {
    private String name;
    private List<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void clearGrades() {
        grades.clear();
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public String toString() {
        return name + "," + String.join(",", grades.stream().map(String::valueOf).toList());
    }
}



public class GradeTracker {

    private static final String DATA_FILE = "data.txt";
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadtheStudentsdataFromDataFile();

        int option = 0;

        do {
            System.out.println("Student Grade Tracker Menu:");
            System.out.println("1. To Add the Student with Grade details");
            System.out.println("2. Delete Student Name and Grade");
            System.out.println("3. To the Search Student by Name");
            System.out.println("4. Modify Student Grades by Name");
            System.out.println("5. Class Statistics(Highest,Lowest,Average)");
            System.out.println("6. Exit");
            System.out.print("Enter your Option: ");
            String inputLine = System.console().readLine();
            option = Integer.parseInt(inputLine);

            switch (option) {
                case 1:
                    totheaddStudent(scanner);
                    break;
                case 2:
                    todeleteStudentdetalis(scanner);
                    break;
                case 3:
                    searchtheStudentBystudentName(scanner);
                    break;
                case 4:
                    modifytheStudentGrades(scanner);
                    break;
                case 5:
                    displayClasshighestlowestavaerge();
                    break;
                case 6:
                    saveStudentsdetalisToDataFile();
                    System.out.println("Exit");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while(option < 7);
    }

        private static void loadtheStudentsdataFromDataFile() {
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    Student student = new Student(name);
                        for (int i = 1; i < parts.length; i++) {
                            try {
                                int grade = Integer.parseInt(parts[i]);
                                student.addGrade(grade);
                            } catch (NumberFormatException e) {
                
                            }
                        }
                        students.add(student);
                }
            } catch (IOException e) {
                System.out.println("Error loading data file: " + e.getMessage());
            }
        }
        private static void saveStudentsdetalisToDataFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
                for (Student student : students) {
                    writer.write(student.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving data file: " + e.getMessage());
            }
        }
        private static void totheaddStudent(Scanner scanner) {
            System.out.print("Enter the Student Name: ");
            String studentName = scanner.nextLine();
        
            
            Student student = new Student(studentName);
        
            System.out.print("Enter the number of Subjects: ");
            int numberOfSubjects = Integer.parseInt(scanner.nextLine());
        
            
            for (int i = 0; i < numberOfSubjects; i++) {
                System.out.print("Enter the grade for Subject " + (i + 1) + ": ");
                int grade = Integer.parseInt(scanner.nextLine());
                student.addGrade(grade);
            }
        
            
            students.add(student);
        
            System.out.println("Student added.");
        }
        private static void todeleteStudentdetalis(Scanner scanner) {
            System.out.print("Enter student name to delete: ");
            String searchName = scanner.nextLine().toLowerCase();
            boolean found = false;
            for (Student student : students) {
                if (student.getName().toLowerCase().contains(searchName)) {
                    students.remove(student);
                    System.out.println("Student deleted.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student not found.");
            }
        }
        private static void searchtheStudentBystudentName(Scanner scanner) {
            System.out.print("Enter student name to search: ");
            String searchName = scanner.nextLine().toLowerCase();
            boolean found = false;
            for (Student student : students) {
                if (student.getName().toLowerCase().contains(searchName)) {
                    System.out.println("Found student: " + student.getName());
                    System.out.println("Grades: " + student.toString());
                    System.out.println("Average Grade: " + student.getAverageGrade());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Student not found.");
            }
        }
        public static void modifytheStudentGrades(Scanner scanner) {
            System.out.print("Enter student name to modify grades: ");
            String searchName = scanner.nextLine().toLowerCase();
            boolean found = false;
        
            for (Student student : students) {
                if (student.getName().toLowerCase().contains(searchName)) {
                    System.out.println("Current grades for " + student.getName() + ": " + student.toString());
                    student.clearGrades();
                    boolean validGrade;
                    do {
                        System.out.print("Enter a new grade (or -1 to finish): ");
                        int grade = Integer.parseInt(scanner.nextLine());
                        if (grade == -1) {
                            break;
                        }
                        student.addGrade(grade);
                        validGrade = true;
                    } while (validGrade);
        
                    System.out.println("Grades modified.");
                    found = true;
                }
            }
        
            if (!found) {
                System.out.println("Student not found.");
            }
        }
        private static void displayClasshighestlowestavaerge() {
            if (students.isEmpty()) {
                System.out.println("No students in the class.");
                return;
            }
            double highestGrade = 0;
            double lowestGrade = 0;
            double totalGrades = 0;
        
            for (Student student : students) {
                double averageGrade = student.getAverageGrade();
                totalGrades += averageGrade;
                
                if (averageGrade > highestGrade) {
                    highestGrade = highestGrade;
                }
                
                if (averageGrade < lowestGrade) {
                    lowestGrade = averageGrade;
                }
            }
        
            double averageClassGrade = totalGrades / students.size();
        
            System.out.println("Class Statistics:");
            System.out.println("Number of Students: " + students.size());
            System.out.println("Highest Average Grade: " + highestGrade);
            System.out.println("Lowest Average Grade: " + lowestGrade);
            System.out.println("Average Class Grade: " + averageClassGrade);
        }
}

   