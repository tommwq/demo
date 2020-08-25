package jpademo;

import javax.persistence.*;
import org.hibernate.jpa.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {

        GuestBook guestBook = new GuestBook();
        guestBook.id = 2;
        guestBook.title = "test";
        guestBook.contents = "Test content.";
        guestBook.username = "john";
        
        EntityManager entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        // entityManager.persist(guestBook);
        GuestBook guestBook2 = entityManager.find(GuestBook.class, 2);

        System.out.println(guestBook2.title);
        System.out.println(guestBook2.contents);
        
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private EntityManager createEntityManager() {


		HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();

        MySQLPersistenceUnitInfo persistenceUnitInfo = new MySQLPersistenceUnitInfo("localhost",
                                                                                      3306,
                                                                                      "root",
                                                                                      "",
                                                                                      "test",
                                                                                      "CST");
		EntityManagerFactory entityManagerFactory = hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, new HashMap<Object,Object>());

		return entityManagerFactory.createEntityManager();
	}
}
