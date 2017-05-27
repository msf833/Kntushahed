package ir.madamas.kntushahed.kntushahed.classes;

/**
 * Created by MSF on 5/12/2017.
 */

public class course {


    String courseID;
    String courseName;
boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public course(String courseID, String courseName, String courseImageUrl) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.courseImageUrl = courseImageUrl;
    }

    String courseImageUrl;

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
