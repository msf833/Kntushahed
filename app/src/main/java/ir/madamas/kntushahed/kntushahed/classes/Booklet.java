package ir.madamas.kntushahed.kntushahed.classes;

/**
 * Created by NFP_7037 on 6/29/2017.
 */

public class Booklet {

    String ID;
    String name;
    String level;
    String user_ID;
    String link;
    String size;
    String rate;
    String price;


    public Booklet(String a, String b, String c, String d, String e, String f, String g, String h) {

        this.ID = a;
        this.name = b;
        this.level = c;
        this.user_ID = d;
        this.link = e;
        this.size = f;
        this.rate = g;
        this.price = h;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
