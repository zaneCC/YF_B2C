package yf.com.yf_b2c.user.bean;

/**
 * Created by zhouzhan on 24/10/16.
 */
public class User {
    public String firstName;
    public String lastName;
    public boolean isShow;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
