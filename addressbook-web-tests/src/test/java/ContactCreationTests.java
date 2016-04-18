import appmanager.TestBase;
import data.ContactData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        gotoAddNewContactPage();
        fillContactForm(new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru"));
        clickCreateButton();
        gotoHomePage();
    }

}
