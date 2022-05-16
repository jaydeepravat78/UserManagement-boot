package com.example.UserManagementBoot.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.example.UserManagementBoot.models.User;
import com.example.UserManagementBoot.utility.KeyGeneration;


@Repository
public class UserDaoImpl<T> implements UserDao<T> {
	@Autowired(required = false)
	HibernateTemplate template;

	private static final Logger log = Logger.getLogger(UserDaoImpl.class);

	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	@Override
	public void addUser(T user) {
		template.save(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getUser(Object email, Object password) {
		T user = null;
		Session session = template.getSessionFactory().openSession();
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("email"), email));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			user = (T) criteria.getResultList().stream().findFirst().orElse(null);
			if (user != null) {
				User u = (User) user;
				if (password.equals(KeyGeneration.decrypt(u.getPassword())))
					return user;
				else
					return null;
			}
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllUsers() {
		Session session = template.getSessionFactory().openSession();
		List<T> users = null;
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("isAdmin"), false));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			users = (List<T>) criteria.getResultList();
		} catch(HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getUserData(Object id) {
		int userId = (int) id;
		return (T) template.get(User.class, userId);
	}
	
	@Override
	@Transactional
	public Object deleteUser(Object id) {
		int userId = (int) id;
		User user = template.get(User.class, userId);
		template.delete(user);
		return true;
	}
	
	@Override
	@Transactional
	public void updateUser(T user) {
		template.update(user);
	}
	
	@Override
	@Transactional
	public void deleteAddress(Object address) {
		template.delete(address);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkEmail(Object email) {
		Session session = template.getSessionFactory().openSession();
		T user = null;
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("email"), email));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			user = (T) criteria.getResultList().stream().findFirst().orElse(null);
			return user == null;
		} catch(HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean updatePassword(T user) {
		Session session = template.getSessionFactory().openSession();
		User userData = null;
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			User userraw = (User) user;
			cquery.where(cbuilder.equal(root.get("email"), userraw.getEmail()));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			userData = criteria.getResultList().stream().findFirst().orElse(null);
			if(userData != null) {
				if(userraw.getSecQues().equals(userData.getSecQues()) && userraw.getGame().equals(userData.getGame())) {
					Transaction t =  session.beginTransaction();
					userData.setPassword(KeyGeneration.encrypt(userraw.getPassword()));
					session.update(userData);
					t.commit();
					return true;
				} else
					return false;
			} else {
				return false;
			}
		} catch(HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}
}
