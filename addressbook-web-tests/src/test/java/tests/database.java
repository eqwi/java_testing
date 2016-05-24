package tests;

import data.ContactData;
import data.GroupData;
import data.Groups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;

import static org.testng.Assert.fail;

public class database {

    private SessionFactory sessionFactory;

    @Test
    public void testDbConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=&useSSL=false&serverTimezone=GMT");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from group_list");

            Groups groups = new Groups();
            while (rs.next()) {
                groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name")).withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
            }
            rs.close();
            st.close();
            conn.close();

            System.out.println(groups);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            fail();
        }
    }

    @Test
    public void testHbConnectionGroupData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery("from GroupData").list();
        result.forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testHbConnectionContactData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        result.forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
    }

    @BeforeClass
    public void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
