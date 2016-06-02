package data;

public class UserInfo {

    private String mail;
    private String user;

    public UserInfo() {
        this.mail = null;
        this.user = null;
    }

    public String userName() {
        return user;
    }

    public String mail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserName(String user) {
        this.user = user;
    }
}
