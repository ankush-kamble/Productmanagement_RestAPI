package com.jbk.Product.Service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jbk.Product.Entity.Product;

public interface ProductService {

	public boolean saveProduct(Product product);
	
	public Product getProductById(String productId);
	
	public List<Product> getAllProduct();
	
	public boolean deleteProductbyId(String productId);
	
	public boolean updateProduct (Product product);
	
	public List<Product> sortProduct(String sortBy);
	
	public List<Product> sortProduct_Desc(String sortBy);
	

	
	public int getTotalCountofProducts() ;
	
	public double getSumofProductprice();
	
	public Product getMaxProductDetails();
	
	public Product getMaxProductDetails_2();
	
	public Product getMaxProductDetails_3();

	public double getMaxProductvalue();

	public Map<String, String> uploadSheet(CommonsMultipartFile  file, HttpSession httpSession);
	
	public String exportToExcel();
	
	public List<Product> readExcel(String filePath) ;
}
