public class ContactData {

    private final String name;
    private final String midName;
    private final String surname;
    private final String nickname;
    private final String title;
    private final String company;
    private final String address;
    private final String phone;
    private final String email;

    public ContactData(String name, String midName, String surname, String nickname, String title, String company, String address, String phone, String email){
        this.name = name;
        this.midName = midName;
        this.surname = surname;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getMidName() {
        return midName;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
