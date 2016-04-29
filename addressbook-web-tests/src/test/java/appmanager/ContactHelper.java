package appmanager;

import data.ContactData;
import data.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

    private NavigationHelper navigationHelper = new NavigationHelper(wd);

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void clickCreateButton() {
        clickButton(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        fillField(By.name("firstname"), contactData.getName());
        fillField(By.name("middlename"), contactData.getMidName());
        fillField(By.name("lastname"), contactData.getSurname());
        fillField(By.name("nickname"), contactData.getNickname());
        fillField(By.name("title"), contactData.getTitle());
        fillField(By.name("company"), contactData.getCompany());
        fillField(By.name("address"), contactData.getAddress());
        fillField(By.name("home"), contactData.getPhone());
        fillField(By.name("email"), contactData.getEmail());
    }

    public void clickModifyContactButton() {
        clickButton(By.cssSelector("img[alt=\"Edit\"]"));
    }

    public void clickUpdateButton() {
        clickButton(By.name("update"));
    }

    public void selectContact() {
        clickButton(By.name("selected[]"));
    }

    public void clickDeleteButton() {
        clickButton(By.xpath("//input[@value='Delete']"));
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void createContact(ContactData contactData) {
        navigationHelper.gotoAddNewContactPage();
        fillContactForm(contactData);
        clickCreateButton();
        navigationHelper.gotoHomePage();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> listOfNames = wd.findElements(By.xpath("//input[@name=\"selected[]\"]"));
        for (WebElement element : listOfNames) {
            String name = element.getText();
            ContactData contact = new ContactData(name, null, null, null, null, null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
