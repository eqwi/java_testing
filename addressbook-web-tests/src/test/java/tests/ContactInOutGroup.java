package tests;


import data.ContactData;
import data.Contacts;
import data.GroupData;
import data.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class ContactInOutGroup extends TestBase {

    @BeforeMethod
    public void checkContacts() {
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData()
                    .withName("name").withMidName("mid").withSurname("surname").withNickname("vahvah").withTitle("director")
                    .withCompany("New Company").withAddress("spb").withHomePhone("8915196819").withEmail("name.mid.surname@newcompany.ru"));
        }
    }

    @BeforeMethod
    public void checkGroups() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("New group").withHeader("There is header").withFooter("There is footer"));
            app.goTo().homePage();
        }
    }


    //
    // ТЕСТЫ
    //


    @Test
    public void testAddContactInGroup() {
        Groups groups = app.db().groups();
        GroupData groupToAdd;
        ContactData contactToAdd = getValidContact(app.db().contacts(), groups);
        if (contactToAdd != null) { // Если все контакты состоят во всех группах, то будет null, поэтому нужно создать новый контакт, чтобы тест выполнился
            groupToAdd = getValidGroup(contactToAdd, groups);
            app.contact().addContactToGroup(contactToAdd, groupToAdd);
        } else {
            groupToAdd = app.db().groups().iterator().next(); // Берем любую группу из которой будем сначала исключать контакт
            contactToAdd = app.db().contacts().iterator().next(); // Берем любой контакт который будем исключать из группы
            app.contact().removeContactFromGroup(contactToAdd, groupToAdd); // Исключаем контакт из группы для дальнейшего теста
            app.contact().addContactToGroup(contactToAdd, groupToAdd); // Добавляем контакт в группу для теста
        }
        if (!checkContactWasAddedToGroup(contactToAdd, groupToAdd)) fail(); // Если проверка не выполнилась фэйлим тест
    }

    @Test
    public void testRemoveContactFromGroup() {
        GroupData groupToRemove;
        ContactData contactToRemove = getValidContact(app.db().contacts());
        if (contactToRemove != null) {
            groupToRemove = getValidGroup(contactToRemove);
            app.contact().removeContactFromGroup(contactToRemove, groupToRemove);
        } else {
            groupToRemove = app.db().groups().iterator().next(); // Берем любую группу, потому что новый контакт не будет состоять в группах
            contactToRemove = app.db().contacts().iterator().next(); // Берем любой контакт, чтобы добавить его в группу
            app.contact().addContactToGroup(contactToRemove, groupToRemove); // Сначала добавляем его в группу
            app.contact().removeContactFromGroup(contactToRemove, groupToRemove); // Затем исключаем для теста
        }
        if (!checkContactWasRemovedFromGroup(contactToRemove, groupToRemove)) fail(); // Если проверка не выполнилась фэйлим тест
    }


    //
    // Дополнительные функции
    //


    // Выбираем контакт, который состоит хотя бы в одной группе
    private ContactData getValidContact(Contacts before) {
        for (int i = 0; i < before.size(); i++) {
            ContactData contact = before.iterator().next();
            if (contact.getGroups().size() != 0) return contact;
        }
        return null;
    }

    // Выбираем контакт, который не состоит во всех группах
    private ContactData getValidContact(Contacts contacts, Groups groups) {
        for (int i = 0; i < contacts.size(); i++) {
            ContactData contact = contacts.iterator().next();
            if (contact.getGroups().size() != groups.size()) return contact;
        }
        return null;
    }

    // Выбираем группу, в которой состоит контакт, который будем исключать из группы
    private GroupData getValidGroup(ContactData contactToRemove) {
        return contactToRemove.getGroups().iterator().next();
    }

    // Выбираем группу, в которой не состоит контакт
    private GroupData getValidGroup(ContactData contactToAdd, Groups groups) {
        if (contactToAdd.getGroups().size() > 0) { // проверяем есть ли у контакта хоть одна группа, если нет сразу возвращаем любую
            for (int i = 0; i < groups.size(); i++) {
                int flag = 0;                      // флаг для проверки есть ли у группы выбранный контакт
                GroupData group = groups.iterator().next();
                for (int j = 0; j < group.getContacts().size(); j++) {
                    if (group.getContacts().iterator().next().getId() == contactToAdd.getId()) flag++; // если группа есть, то изменяем флаг
                }
                if (flag == 0) return group; // если флаг не изменился значит в группе нет нашего контакта и возвращаем эту группу
            }
        } else return groups.iterator().next();
        return null;
    }

    // Проверяем что в списках групп контакта есть группа, в которую мы добавили его
    private boolean checkContactWasAddedToGroup(ContactData contactToAdd, GroupData groupToAdd) {
        Contacts after = app.db().contacts();
        for (int i = 0; i < after.size(); i++) {
            ContactData contact = after.iterator().next();
            if (contact.getId() == contactToAdd.getId()) { // сначала находим контакт, который мы добавляли в группу
                for (int j = 0; j < contact.getGroups().size(); j++) { // теперь нужно найти, что группа в которую мы добавляли есть в списках контакта
                    if (contact.getGroups().iterator().next().getId() == groupToAdd.getId()) return true; // заканчиваем сразу же как только находим группу
                }
            }
        }
        return false;
    }

    // Проверяем что в списках групп контакта больше нет группы, из которой мы его исключили
    private boolean checkContactWasRemovedFromGroup(ContactData contactToRemove, GroupData groupToRemove) {
        Contacts after = app.db().contacts();
        for (int i = 0; i < after.size(); i++) {
            ContactData contact = after.iterator().next();
            if (contact.getId() == contactToRemove.getId()) {
                int flag = 0;
                for (int j = 0; j < contact.getGroups().size(); j++) {
                    if (contact.getGroups().iterator().next().getId() == groupToRemove.getId()) flag++;
                }
                if (flag == 0) return true;  // Если значение флага не изменилось, то в списках групп контакта группы, из которой исключили, нет и значит все верно
            }
        }
        return false; // Если в цикле не зашли в return true, значит выполнилось с ошибкой
    }

}
