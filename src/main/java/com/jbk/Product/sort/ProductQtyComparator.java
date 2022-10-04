package com.jbk.Product.sort;

import java.util.Comparator;

import com.jbk.Product.Entity.Product;


public class ProductQtyComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		if (p1.getProductQty()==p2.getProductQty()) {
			return 0;
		} else if (p1.getProductQty()>p2.getProductQty()) {
			return 1;
		} else {
			return -1;
		}

	}

}
