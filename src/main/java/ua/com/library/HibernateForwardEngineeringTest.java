package ua.com.library;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
//import ua.com.library.entity.Order;
import ua.com.library.entity.User;

public class HibernateForwardEngineeringTest {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            User user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            user.setEmail("a@gmail.com");
            user.setName("andrey");
            user.setSurname("sokorenko");
            user.setRole(User.Role.ADMIN);



            session.save(user);

            transaction.commit();

            session.close();
            factory.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
}