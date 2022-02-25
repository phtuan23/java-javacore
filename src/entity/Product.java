package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import dao.CategoryDAOImp;
import main.MultipleLanguage;

public class Product {
	private String id; 
	private String name;
	private int status;
	private float price;
	private String description;
	private Date Expiration;
	private String category_id;
	
	public Product() {
	}

	public Product(String id, String name, int status, float price, String description, Date expiration,
			String category_id) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.price = price;
		this.description = description;
		this.Expiration = expiration;
		this.category_id = category_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getExpiration() {
		return Expiration;
	}

	public void setExpiration(Date expiration) {
		Expiration = expiration;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	
	public void displayPro() {
		System.out.printf("| %-4s | %-40s | %-10.2f | %-10s | %-10s | %-12s | %-20s |\n",this.id,this.name,this.price,this.status,this.description,this.Expiration,this.category_id);
	}
	 
	
	public void inputPro(List<Product> list) {
		inputId(list);
		inputName(list);
		inputPrice();
		inputStatus();
		inputDescription();
		inputExpiration();
		inputCategoryId();
	}

	public void inputId(List<Product> list) {
		Scanner scanner = new Scanner(System.in);
		Pattern pattern = Pattern.compile("^[cC]\\w{3}");
		while (true) {
			boolean isExist = false;
			System.out.println(MultipleLanguage.getRB().getString("product.inputID"));
			String id = scanner.nextLine();
			for (Product p : list) {
				if (p.getId().equalsIgnoreCase(id)) {
					isExist = true;
				}
			}
			if (pattern.matcher(id).matches()) {
				if (!isExist) {
					this.id = id;
					break;
				}else {
					System.err.println(MultipleLanguage.getRB().getString("product.errorIdExist"));
				}
			}else {
				System.err.println(MultipleLanguage.getRB().getString("product.errorID"));
			}
		}
	}
	
	public void inputName(List<Product> list) {
		Scanner scanner = new Scanner(System.in);
		Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		Pattern pattern = Pattern.compile("(.){6,50}");
		while (true) {
			boolean isExist = false;
			System.out.println(MultipleLanguage.getRB().getString("product.inputName"));
			String name = scanner.nextLine();
			for (Product p : list) {
				if (p.getName().equalsIgnoreCase(name)) {
					isExist = true;
				}
			}
			if (pattern.matcher(name).matches()) {
				if (!special.matcher(name).matches()) {
					if (!isExist) {
						this.name = name;
						break;
					}else {
						System.err.println(MultipleLanguage.getRB().getString("product.errorNameExist"));
					}
				}else {
					System.err.println(MultipleLanguage.getRB().getString("product.errorNameSC"));
				}
			}else {
				System.err.println(MultipleLanguage.getRB().getString("product.errorName"));
			}
		}
	}
	
	public void inputStatus() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(MultipleLanguage.getRB().getString("product.inputStatus"));
			try {
				int stt = Integer.parseInt(scanner.nextLine());
				if (stt == 0 || stt == 1) {
					this.status = stt;
					break;
				}else {
					System.err.println(MultipleLanguage.getRB().getString("product.inputStatusErr1"));
				}
			} catch (NumberFormatException e) {
				System.err.println(MultipleLanguage.getRB().getString("product.errorStatus"));
			}
		}
	}
	
	public void inputPrice() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(MultipleLanguage.getRB().getString("product.inputPrice"));
			try {
				float price = Float.parseFloat(scanner.nextLine());
				if (price > 0) {
					this.price = price;
					break;
				}else {
					System.err.println(MultipleLanguage.getRB().getString("product.errorPrice1"));
				}
			} catch (NumberFormatException e) {
				System.err.println(MultipleLanguage.getRB().getString("product.errorPrice2"));
			}
		}
	}
	
	public void inputDescription() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(MultipleLanguage.getRB().getString("product.inputDes"));
		String description = scanner.nextLine();
		description = description.length() != 0 ? description : MultipleLanguage.getRB().getString("product.new");
		this.description = description;
	}
	
	public void inputCategoryId() {
		CategoryDAOImp categoryDAOImp = new CategoryDAOImp();
		List<Category> listCats = categoryDAOImp.getAll();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			boolean isExist = false;
			System.out.printf("| %5s | %-30s | \n",MultipleLanguage.getRB().getString("ID"),MultipleLanguage.getRB().getString("Category"));
			for (Category cat : listCats) {
				System.out.printf("| %5s | %-30s | \n",cat.getId(),cat.getName());
			}
			System.out.println(MultipleLanguage.getRB().getString("selectCate"));
			String categoryId = scanner.nextLine();
			for (Category category : listCats) {
				if (category.getId().equalsIgnoreCase(categoryId)) {
					isExist = true;
				}
			}
			if (isExist) {
				this.category_id = categoryId;
				break;
			}else {
				System.err.println(MultipleLanguage.getRB().getString("cateNotExist"));
			}
		}
	}
	
	public void inputExpiration() {
		Scanner scanner = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while (true) {
			Date now = new Date();
			System.out.println(MultipleLanguage.getRB().getString("product.inputExp"));
			try {
				Date date = sdf.parse(scanner.nextLine());
				if (date.after(now)) {
					this.Expiration = date;
					break;
				}else {
					System.err.println(MultipleLanguage.getRB().getString("product.errorExp1"));
				}
			} catch (ParseException e) {
				System.err.println(MultipleLanguage.getRB().getString("product.errorExp"));
			}
		}
	}
}
