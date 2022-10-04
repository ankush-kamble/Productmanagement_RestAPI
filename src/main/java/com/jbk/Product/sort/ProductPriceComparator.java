package com.jbk.Product.sort;

import java.util.Comparator;

import com.jbk.Product.Entity.Product;



public class ProductPriceComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
	if (p1.getProductPrice()==p2.getProductPrice()) {
		return 0;
	} else if (p1.getProductPrice()>p2.getProductPrice()) {
		return 1;
	} else {
		return -1;
	}
		
	}

}
