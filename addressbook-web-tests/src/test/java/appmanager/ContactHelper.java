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
        fillField(By.name("home"), contactData.getHomePhone());
        fillField(By.name("mobile"), contactData.getMobilePhone());
        fillField(By.name("work"), contactData.getWorkPhone());
        fillField(By.name("email"), contactData.getEmail());
        fillField(By.name("email2"), contactData.getEmail2());
        fillField(By.name("email3"), contactData.getEmail3());
    }

    public void clickModifyContactButtonOfChosenContact(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void clickUpdateButton() {
        clickButton(By.name("update"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector(String.format("input[id='%s']", id))).click();
    }

    public void clickDeleteButton() {
        clickButton(By.xpath("//input[@value='Delete']"));
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
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

    public ContactData collectContactDataFromEditPage() {
        ContactData contactData = new ContactData();
        contactData.withName(wd.findElement(By.name("firstname")).getAttribute("value"));
        contactData.withMidName(wd.findElement(By.name("middlename")).getAttribute("value"));
        contactData.withSurname(wd.findElement(By.name("lastname")).getAttribute("value"));
        contactData.withNickname(wd.findElement(By.name("nickname")).getAttribute("value"));
        contactData.withTitle(wd.findElement(By.name("title")).getAttribute("value"));
        contactData.withCompany(wd.findElement(By.name("company")).getAttribute("value"));
        contactData.withAddress(wd.findElement(By.name("address")).getAttribute("value"));
        contactData.withHomePhone(wd.findElement(By.name("home")).getAttribute("value"));
        contactData.withMobilePhone(wd.findElement(By.name("mobile")).getAttribute("value"));
        contactData.withWorkPhone(wd.findElement(By.name("work")).getAttribute("value"));
        contactData.withEmail(wd.findElement(By.name("email")).getAttribute("value"));
        contactData.withEmail2(wd.findElement(By.name("email2")).getAttribute("value"));
        contactData.withEmail3(wd.findElement(By.name("email3")).getAttribute("value"));
        return contactData;
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
            String phones = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + (list.size() - i + 2) + "]/td[6]")).getText();
            String emails = wd.findElement(By.xpath("//table[@id='maintable']/tbody/tr[" + (list.size() - i + 2) + "]/td[5]")).getText();
            contactCache.add(new ContactData().withId(id).withName(name).withSurname(surname).withAddress(address)
                    .withAllPhones(phones).withAllEmails(emails));
            i--;
        }
        return contactCache;
    }

}
