package com.jbk.Product.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jbk.Product.Entity.Product;
import com.jbk.Product.Service.ProductService;
import com.jbk.Product.exception.ProductNotFoundException;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
//type must be wrapper class in ResponseEntity
	@PostMapping(value = "/saveproduct")
	public ResponseEntity<Boolean> saveProduct(@Valid @RequestBody Product product) {
		boolean isAdded = service.saveProduct(product);

		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			throw new ProductNotFoundException("Product already exist");
		}

	}


	@GetMapping(value = "/getproductbyID")
	public ResponseEntity<Product> getProductById( @RequestParam String productId) {
		Product product = service.getProductById(productId);

		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found " + productId);
		}
	}

	@GetMapping(value = "/getallproduct")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> list = service.getAllProduct();
		if (list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found ");
		}
	}

	@DeleteMapping(value = "/deleteProduct")
	public ResponseEntity<Boolean> deleteProductbyId( @RequestParam String productId) {
		boolean isDeleted = service.deleteProductbyId(productId);

		if (isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found " + productId);
		}

	}

	@PutMapping(value = "/updateProduct")
	public ResponseEntity<Boolean> updateProduct(@Valid @RequestBody Product product) {
		boolean isUpdated = service.updateProduct(product);

		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found " + product);
		}

	}

	@GetMapping(value = "/sortproduct")
	public ResponseEntity<List<Product>> sortProduct( @RequestParam String sortBy) {
		List<Product> list = service.sortProduct(sortBy);

		if (list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found for sorting by" + sortBy );
		}
	}
	
	@GetMapping(value="/totalcount")
	public ResponseEntity<Integer> getTotalCountofProducts(){
		int productCount = service.getTotalCountofProducts();
		if (productCount > 0) {
			return new ResponseEntity<Integer>(productCount,HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>(productCount,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="/getpricesum")
	public ResponseEntity<Double> getSumofProductprice() {
		double sum = service.getSumofProductprice();
		if (sum > 0) {
			return new ResponseEntity<Double>(sum,HttpStatus.OK);
		}else {
			return new ResponseEntity<Double>(sum,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="getmaxproduct")
	public ResponseEntity<Product> getMaxProductDetails() {
		Product maxProduct = service.getMaxProductDetails();
		
		if (maxProduct != null) {
			return new ResponseEntity<Product>(maxProduct,HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(maxProduct,HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value="sortByDesc")
	public ResponseEntity<List<Product>> sortProduct_Desc( @RequestParam String sortBy){
		List<Product> list = service.sortProduct_Desc(sortBy);
		if (list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found for sorting by" + sortBy );
		}
		
	}
	
	@PostMapping(value = "/uploadSheet" )
	public ResponseEntity<Map<String,String>> uploadSheet(@RequestParam CommonsMultipartFile file,HttpSession httpSession){
		
		//System.out.println(file.getOriginalFilename());
//		String path=httpSession.getServletContext().getRealPath("/uploadFile/");
//		System.out.println(path);
		
		Map<String,String> map=service.uploadSheet(file, httpSession);
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
	}
		
	@GetMapping(value = "/exportToExcel")
	public ResponseEntity<String> exportToExcel() {
		String msg = service.exportToExcel();
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
}
