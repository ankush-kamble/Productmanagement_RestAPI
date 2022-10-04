package com.jbk.Product.Dao;

import java.util.Arrays;
import java.util.List;

import com.jbk.Product.Entity.Product;


public interface ProductDao {
	
	public boolean saveProduct(Product product);
	
	public Product getProductById(String productId);
	
	public List<Product> getAllProduct();
	
	public boolean deleteProductbyId(String productId);
	
	public boolean updateProduct (Product product);
	
	public int uploadProductList(List<Product> list);
	
	
}
