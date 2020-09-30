package ua.com.library;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
//import ua.com.library.entity.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.library.entity.User;

public class HibernateForwardEngineeringTest {
    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
            user.setPassword(encoder.encode( "admin"));
            user.setEmail("a@gmail.com");
            user.setName("andrey");
            user.setSurname("sokorenko");
            user.setRole(User.Role.ADMIN);


            User user2 = new User();
            user2.setLogin("user");
            user2.setPassword(encoder.encode( "user"));
            user2.setEmail("user@gmail.com");
            user2.setName("dima");
            user2.setSurname("colodiychuck");
            user2.setRole(User.Role.READER);



            session.save(user);
            session.save(user2);

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