package ir.madamas.kntushahed.kntushahed.classes;

/**
 * Created by MSF on 5/15/2017.
 */

public class notification {
    String maincontent;
    String title;
    String timedate;
    String status;

    public notification(String maincontent, String title, String timedate, String status) {
        this.maincontent = maincontent;
        this.title = title;
        this.timedate = timedate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "notification{" +
                "maincontent='" + maincontent + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", timedate='" + timedate + '\'' +
                '}';
    }

    public String getMaincontent() {
        return maincontent;
    }

    public void setMaincontent(String maincontent) {
        this.maincontent = maincontent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }
}
