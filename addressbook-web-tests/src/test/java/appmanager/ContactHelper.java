package appmanager;

import data.ContactData;
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

    public void clickModifyContactButton(int id) {
        wd.findElements(By.xpath("//img[@title=\"Edit\"]")).get(id).click();
    }

    public void clickUpdateButton() {
        clickButton(By.name("update"));
    }

    public void selectContact(int id) {
        wd.findElements(By.name("selected[]")).get(id).click();
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
        List<WebElement> list = wd.findElements(By.xpath("//tr[@name=\"entry\"]"));
        for (WebElement element : list) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            contacts.add(new ContactData(id, null, null, null, null, null, null, null, null, null));
        }
        for (int i = 2; i < list.size()+2; i++) {
            int id = contacts.get(0).getId();
            String name = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + i + "]/td[3]")).getText();
            String surname = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + i + "]/td[2]")).getText();
            String address = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + i + "]/td[4]")).getText();
            contacts.remove(0);
            contacts.add(new ContactData(id, name, null, surname, null, null, null, address, null, null));
        }
        return contacts;
    }
}
