package appmanager;

import data.ContactData;
import data.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactHelper extends BaseHelper {

    private Contacts contactCache = null;
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

    public void clickModifyContactButtonOfChosenContact(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void clickUpdateButton() {
        clickButton(By.name("update"));
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
    }

    public void clickDeleteButton() {
        clickButton(By.xpath("//input[@value='Delete']"));
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void create(ContactData contactData) {
        navigationHelper.addNewContactPage();
        fillContactForm(contactData);
        clickCreateButton();
        contactCache = null;
        navigationHelper.homePage();
    }
    public void modify(ContactData contact) {
        clickModifyContactButtonOfChosenContact(contact.getId());
        fillContactForm(contact);
        clickUpdateButton();
        contactCache = null;
        navigationHelper.homePage();
    }

    public void delete(ContactData deletedContact) {
        selectContactById(deletedContact.getId());
        clickDeleteButton();
        contactCache = null;
        acceptDeletion();
    }

    public Contacts all() {
        if (contactCache != null) return new Contacts(contactCache);
        contactCache = new Contacts();
        List<WebElement> list = wd.findElements(By.xpath("//tr[@name=\"entry\"]"));

        int i = list.size();
        for (WebElement element : list) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String name = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + (list.size() - i + 2) + "]/td[3]")).getText();
            String surname = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + (list.size() - i + 2) + "]/td[2]")).getText();
            String address = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + (list.size() - i + 2) + "]/td[4]")).getText();
            contactCache.add(new ContactData().withId(id).withName(name).withSurname(surname).withAddress(address));
            i--;
        }
        return contactCache;
    }
}
