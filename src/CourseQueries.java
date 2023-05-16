
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

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement getCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        } 
        catch(SQLException sqlException) 
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try {
            getCourses = connection.prepareStatement("select * from app.course where semester = (?)");
            getCourses.setString(1, semester);
            resultSet = getCourses.executeQuery();
            while(resultSet.next()) {
                courses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<>();
        try {
            getCourseCodes = connection.prepareStatement("select coursecode from app.course");
            resultSet = getCourseCodes.executeQuery();
            
            while(resultSet.next()) {
                courses.add(resultSet.getString(1));
            }
        } 
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseSeats(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<>();
        try {
            getCourseSeats = connection.prepareStatement("select seats from app.course");
            resultSet = getCourseSeats.executeQuery();
            while(resultSet.next()) {
                courses.add(resultSet.getString(1));
            }
        } 
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("delete from app.course where semester = (?) and coursecode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        } 
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

