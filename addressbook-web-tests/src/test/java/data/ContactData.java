package data;

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
    private int id;

    public ContactData(int id, String name, String midName, String surname, String nickname, String title, String company, String address, String phone, String email){
        this.id = id;
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

    public ContactData(String name, String midName, String surname, String nickname, String title, String company, String address, String phone, String email){
        this.id = Integer.MAX_VALUE;
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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public String toString() {
        return "ContactData{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

}