package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl extends Util implements UserDao {
	SessionFactory sessionFactory = getSessionFactory();

	public UserDaoHibernateImpl() {
	}

	@Override
	public void createUsersTable() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS `mydb`.`Users` (\n" +
					"`id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
					"`name` VARCHAR(45) NOT NULL,\n" +
					"`lastName` VARCHAR(45) NOT NULL,\n" +
					"`age` TINYINT NOT NULL,\n" +
					"PRIMARY KEY (`id`, `name`, `lastName`,age));");

			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void dropUsersTable() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery("DROP TABLE IF EXISTS USERS");

			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			User user = new User();
			user.setName(name);
			user.setLastName(lastName);
			user.setAge(age);
			session.save(user);
			System.out.println("User с именем – " + name + " добавлен в базу");

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void removeUserById(long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery("DELETE FROM users WHERE id = :userid");
			query.setParameter("userid", id);
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<User> getAllUsers() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			List user = session.createQuery("from User").list();

			for (Object user1 : user) {
				System.out.println(user1);
			}
			transaction.commit();
			return user;
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public void cleanUsersTable() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery("TRUNCATE TABLE users");
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}
}
