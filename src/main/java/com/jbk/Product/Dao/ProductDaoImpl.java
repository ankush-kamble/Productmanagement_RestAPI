package com.jbk.Product.Dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.Product.Entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
//	List<Product> list=getAllProduct();

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public boolean saveProduct(Product product) {

		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isAdded = false;
		try {
			Product prd = session.get(Product.class, product.getProductId());
			if (prd == null) {
				session.save(product);
				transaction.commit();
				isAdded = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (session != null) {
			session.close();
		}
		return isAdded;
	}

	@Override
	public Product getProductById(String productId) {

		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		Product product = null;
		try {
			product = session.get(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<Product> getAllProduct() {
		Session session = sessionfactory.openSession();
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> list = null;
		try {
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteProductbyId(String productId) {
		Session session = sessionfactory.openSession();

		boolean isDeleted = false;
		try {

			Product product = session.get(Product.class, productId);
			Transaction transaction = session.beginTransaction();
			session.delete(product);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isDeleted;
	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sessionfactory.openSession();

		boolean isUpdated = false;
		try {
			Transaction transaction = session.beginTransaction();
			Product prd = session.get(Product.class, product.getProductId());
			if (prd != null) {
				session.evict(prd);
				session.update(prd);
				transaction.commit();
				isUpdated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isUpdated;
	}

	@Override
	public int uploadProductList(List<Product> list) {
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {

			for (Product product : list) {
				session = sessionfactory.openSession();
				transaction = session.beginTransaction();
				session.save(product);
				transaction.commit();
				count = count + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

}
