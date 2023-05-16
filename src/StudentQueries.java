import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rithv
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudents;
    private static PreparedStatement getAllStudents;    
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudents(StudentEntry student) {
        connection = DBConnection.getConnection();
        try {
            addStudents = connection.prepareStatement("insert into app.student values (?, ?, ?)");
            addStudents.setString(1, student.getStudentID());
            addStudents.setString(2, student.getFirstName());
            addStudents.setString(3, student.getLastName());
            addStudents.executeUpdate();
        } 
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        StudentEntry student;
        try{
            getAllStudents = connection.prepareStatement("select * from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next()){
                student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                students.add(student);
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try{
            getStudent = connection.prepareStatement("select * from app.student where studentID = (?)");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next()){
                student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return student;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try{
            dropStudent = connection.prepareStatement("delete from app.student where studentID = (?)");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
