package ir.madamas.kntushahed.kntushahed.classes;

/**
 * Created by MSF on 5/12/2017.
 */

public class course {


    String courseID , courseName;

    public course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
