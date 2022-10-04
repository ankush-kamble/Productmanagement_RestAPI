package com.jbk.Product.sort;

import java.util.Comparator;

import com.jbk.Product.Entity.Product;


public class ProductNameComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		
		return p1.getProductName().compareTo(p2.getProductName());
	}

}
