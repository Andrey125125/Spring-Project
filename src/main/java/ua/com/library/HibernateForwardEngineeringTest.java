package ua.com.library;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.library.entity.Author;
import ua.com.library.entity.Book;
import ua.com.library.entity.User;

import java.sql.Date;

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
            user2.setSurname("kolodiychuck");
            user2.setRole(User.Role.READER);

            Book gof = new Book();
            gof.setName("GoF patterns");
            gof.setPublisher("ProgBooks");
            gof.setQuantity(9);
            gof.setPublishingDate(new Date(System.currentTimeMillis()));
            gof.setDescription("book about pattens");
            gof.setDescriptionUa("книга про паттерни");

            Author erichGamma = new Author();
            erichGamma.setName("Erich Gamma");
            Author richardHelm = new Author();
            richardHelm.setName("Richard Helm");
            gof.addAuthor(erichGamma);
            gof.addAuthor(richardHelm);


            Book refactor = new Book();
            refactor.setName("Refactoring");
            refactor.setPublisher("ProgBooks");
            refactor.setQuantity(9);
            refactor.setPublishingDate(new Date(System.currentTimeMillis()));
            refactor.setDescription("book about refactoring");
            refactor.setDescriptionUa("книга про рефакторинг");

            refactor.addAuthor(erichGamma);

            Book mrMercedes = new Book();
            mrMercedes.setName("mr Mercedez");
            mrMercedes.setPublisher("Amazon");
            mrMercedes.setQuantity(9);
            mrMercedes.setPublishingDate(new Date(System.currentTimeMillis()));
            mrMercedes.setDescription("book about crime story");
            mrMercedes.setDescriptionUa("книга про детектива");

            Author king = new Author();
            king.setName("Stephen King");

            mrMercedes.addAuthor(king);




            session.save(mrMercedes);
            session.save(gof);
            session.save(refactor);

            session.save(erichGamma);
            session.save(richardHelm);
            session.save(king);

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