package com.jbk.Product.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jbk.Product.Dao.ProductDao;
import com.jbk.Product.Entity.Product;
import com.jbk.Product.sort.ProductPriceComparator;
import com.jbk.Product.sort.ProductQtyComparator;
import com.jbk.Product.sort.ProductTypeComparator;
import com.jbk.Product.sort.ProductQtyComparator;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	String excludedRows = "";
	int totalRecordCount = 0;
	
	@Override
	public boolean saveProduct(Product product) {
		if (product.getProductId() == null) {
			String id = new SimpleDateFormat("yyyyMMddHHssSSS").format(new Date());
			product.setProductId(id);
		}
		boolean isAdded = dao.saveProduct(product);
		return isAdded;
	}

	@Override
	public Product getProductById(String productId) {
		Product product = dao.getProductById(productId);
		return product;
	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> list = dao.getAllProduct();
		return list;
	}

	@Override
	public boolean deleteProductbyId(String productId) {
		boolean isDeleted = dao.deleteProductbyId(productId);
		return isDeleted;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean isUpdated = dao.updateProduct(product);
		return isUpdated;
	}

	@Override
	public List<Product> sortProduct(String sortBy) {
		List<Product> list = getAllProduct();
		if (!list.isEmpty()) {
			if (sortBy.equalsIgnoreCase("productprice")) {
				Collections.sort(list, new ProductPriceComparator());
			} else if (sortBy.equalsIgnoreCase("productPrice")) {
				Collections.sort(list, new ProductPriceComparator());

			} else if (sortBy.equalsIgnoreCase("productQty")) {
				Collections.sort(list, new ProductQtyComparator());

			} else if (sortBy.equalsIgnoreCase("productType")) {
				Collections.sort(list, new ProductTypeComparator());
			}
		}
		return list;
	}

	@Override
	public List<Product> sortProduct_Desc(String sortBy) {
		List<Product> list = getAllProduct();
		if (sortBy.equalsIgnoreCase("productPrice")) {
			Collections.sort(list, new ProductPriceComparator().reversed());
		}
		return list;
	}

	@Override
	public int getTotalCountofProducts() {
		List<Product> list = getAllProduct();
		int productCount = 0;
		if (!list.isEmpty()) {
			productCount = list.size();
		}
		return productCount;
	}

	@Override
	public double getSumofProductprice() {
		List<Product> list = getAllProduct();
		double sum = 0;
		if (!list.isEmpty()) {
			sum = list.stream().mapToDouble(Product::getProductPrice).sum();
		}
		return sum;
	}

	@Override
	public Product getMaxProductDetails() {
		List<Product> list = getAllProduct();
		Product product = null;
		if (!list.isEmpty()) {
			product = list.stream().max(Comparator.comparingDouble(Product::getProductPrice)).get();
		}
		return product;
	}

	@Override
	public Product getMaxProductDetails_2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getMaxProductDetails_3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMaxProductvalue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String,String> uploadSheet(CommonsMultipartFile file, HttpSession httpSession) {
		
		String path=httpSession.getServletContext().getRealPath("/");
		String fileName = file.getOriginalFilename();
		HashMap<String, String> map = new HashMap<>();
		
		int uploadedCount=0;
		
		FileOutputStream fileOutputStream = null;
		byte[] data=file.getBytes();
		try {
			System.out.println(path);
			fileOutputStream = new FileOutputStream(new File(path+File.separator+fileName));
			fileOutputStream.write(data);			
			
			List<Product> list= readExcel(path + File.separator + fileName);
			
			uploadedCount =dao.uploadProductList(list);
			
			map.put("Total Record In Sheet",String.valueOf(totalRecordCount));
			map.put("Uploaded Record In DB",String.valueOf(uploadedCount));
			map.put("Bad Record Row Number",excludedRows);
			map.put("Total Excluded",String.valueOf(totalRecordCount - uploadedCount));
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public List<Product> readExcel(String filePath) {
		Workbook workbook=null;
		FileInputStream fileInputStream=null;
		List<Product> list = new ArrayList<>();
		Product product=null;
		
		try {
			fileInputStream= new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(fileInputStream);
			
			Sheet sheet= workbook.getSheetAt(0);
			totalRecordCount= sheet.getLastRowNum();
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount=0;
			
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				
				if (rowCount == 0) {
					rowCount++;
					continue;
				}
				product = new Product();
				Thread.sleep(1);
				String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				product.setProductId(id);
				Iterator<Cell> cells = row.cellIterator();
				
				while (cells.hasNext()) {
					Cell cell = cells.next();
					
					int column = cell.getColumnIndex();
					
					switch (column) {
					case 0:
						product.setProductName(cell.getStringCellValue());
						break;

					case 1:
						product.setProductQty((int) cell.getNumericCellValue());
						break;
						
					case 2:
						product.setProductPrice(cell.getNumericCellValue());
						break;
						
					case 3:
						product.setProductType(cell.getStringCellValue());
						break;
				
					}
					
				}
				list.add(product);
			}
			fileInputStream.close();
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String exportToExcel() {
		List<Product> allProduct = getAllProduct();
		String[] columns = {"ID","NAME","QTY","PRICE","TYPE"};
		
		//Workbook,Sheet,Row,Sell
		
		try {
			
			Workbook workbook = new HSSFWorkbook();
			
			CreationHelper creationHelper = workbook.getCreationHelper();
	
			//For creating A sheet
		Sheet sheet = workbook.createSheet();
			
		//For creating font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeight((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		
		Row headerRow = sheet.createRow(0);
		
		for (int i=0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
			
		}
		
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		
		int rowNum = 1;
		for (Product product : allProduct) {
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(product.getProductId());
			
			row.createCell(1).setCellValue(product.getProductName());
			
			row.createCell(2).setCellValue(product.getProductQty());
			
			row.createCell(3).setCellValue(product.getProductPrice());
			
			row.createCell(4).setCellValue(product.getProductType());
		}
		
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream("product.xlsx");
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		
		workbook.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Created";
	}

}
