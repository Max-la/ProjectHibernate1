package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl extends Util implements UserDao {
	SessionFactory sessionFactory = getSessionFactory();

	public UserDaoHibernateImpl() {
	}


	@Override
	public void createUsersTable() {
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS `mydb`.`Users` (\n" +
					"`id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
					"`name` VARCHAR(45) NOT NULL,\n" +
					"`lastName` VARCHAR(45) NOT NULL,\n" +
					"`age` TINYINT NOT NULL,\n" +
					"PRIMARY KEY (`id`, `name`, `lastName`,age));");
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dropUsersTable() {
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createSQLQuery("DROP TABLE IF EXISTS USERS");

			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		try (Session session = sessionFactory.openSession()) {
			User user = new User();
			user.setName(name);
			user.setLastName(lastName);
			user.setAge(age);
			session.save(user);
			System.out.println("User с именем – " + name + " добавлен в базу");
			Transaction transaction = session.beginTransaction();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUserById(long id) {
		try (Session session = sessionFactory.openSession()) {
			User user = session.get(User.class, id);
			user.setId(id);
			session.remove(user);
			Transaction transaction = session.beginTransaction();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			List<User> userList = session.createCriteria(User.class).list();
			for (User user : userList) {
				System.out.println(user.toString());
			}
			session.beginTransaction();
			session.getTransaction().commit();
			return userList;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void cleanUsersTable() {
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createSQLQuery("TRUNCATE TABLE users");
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}
}
