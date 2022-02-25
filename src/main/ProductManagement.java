package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import dao.CategoryDAOImp;
import dao.ProductDAOImp;
import entity.Product;

public class ProductManagement {

	ProductDAOImp productDAOImp = new ProductDAOImp();

	public void mainMenuProduct() {
		int ch;
		do {
			ch = menuProduct();
			switch (ch) {
			case 1:
				addProduct();
				break;
			case 2:
				displayProduct();
				break;
			case 3:
				sortProduct();
				break;
			case 4:
				updateProduct();
				break;
			case 5:
				break;
			default:
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
				break;
			}
		} while (ch != 5);
	}

	private int menuProduct() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("------------------------");
			System.out.println(MultipleLanguage.getRB().getString("proMng.add"));
			System.out.println("------------------------");
			System.out.println(MultipleLanguage.getRB().getString("proMng.show"));
			System.out.println("------------------------");
			System.out.println(MultipleLanguage.getRB().getString("proMng.sort"));
			System.out.println("------------------------");
			System.out.println(MultipleLanguage.getRB().getString("proMng.update"));
			System.out.println("------------------------");
			System.out.printf("| %d. %-17s |\n",5,MultipleLanguage.getRB().getString("back"));
			System.out.println("------------------------");
			System.out.print(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("[ "+MultipleLanguage.getRB().getString("choose")+ MultipleLanguage.getRB().getString("from") + "1" + MultipleLanguage.getRB().getString("to") + " 4 ]");
			}
		}
	}

	private void addProduct() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			List<Product> list = productDAOImp.getAll();
			Product p = new Product();
			p.inputPro(list);
			if (productDAOImp.addProduct(p)) {
				System.out.println(MultipleLanguage.getRB().getString("success"));
			} else {
				System.err.println(MultipleLanguage.getRB().getString("failed"));
			}
			System.out.println(MultipleLanguage.getRB().getString("continue") + " ? ( 0: "+MultipleLanguage.getRB().getString("stop")+" -- 1: "+MultipleLanguage.getRB().getString("continue")+" )");
			try {
				if (Integer.parseInt(scanner.nextLine()) == 0) {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
		System.out.println();
	}

	private int menuDisplay() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("------------------------------------------------------");
			System.out.printf("| %d. %-47s |\n",1,MultipleLanguage.getRB().getString("listPro"));
			System.out.println("------------------------------------------------------");
			System.out.printf("| %d. %-47s |\n",2,MultipleLanguage.getRB().getString("proByCat"));
			System.out.println("------------------------------------------------------");
			System.out.printf("| %d. %-47s |\n",3,MultipleLanguage.getRB().getString("proDetailByName"));
			System.out.println("------------------------------------------------------");
			System.out.printf("| %d. %-47s |\n",4,MultipleLanguage.getRB().getString("proRemain3dayExp"));
			System.out.println("------------------------------------------------------");
			System.out.printf("| %d. %-47s |\n",5,MultipleLanguage.getRB().getString("back"));
			System.out.println("------------------------------------------------------");
			System.out.print(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("[ "+MultipleLanguage.getRB().getString("choose") + MultipleLanguage.getRB().getString("from") + "1" + MultipleLanguage.getRB().getString("to") + " 5 ]");
			}
		}
	}

	private void displayProduct() {
		int ch;
		do {
			ch = menuDisplay();
			switch (ch) {
			case 1:
				listProduct();
				break;
			case 2:
				prodByCat();
				break;
			case 3:
				prodByName();
				break;
			case 4:
				prodRemain3dayExp();
				break;
			case 5:
				break;
			default:
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
				break;
			}
		} while (ch != 5);
		System.out.println();
	}
	
	private void listProduct() {
		List<Product> list = productDAOImp.getAll();
		showProduct(list);
		System.out.println();
	}

	private void prodByCat() {
		CategoryDAOImp categoryDAOImp = new CategoryDAOImp();
		Scanner scanner = new Scanner(System.in);
		System.out.println("-----------------------------------");
		System.out.printf("| %-3s | %-25s |\n", MultipleLanguage.getRB().getString("ID"), MultipleLanguage.getRB().getString("Category"));
		System.out.println("-----------------------------------");
		categoryDAOImp.getAll().forEach(cat -> System.out.printf("| %-3s | %-25s | \n", cat.getId(), cat.getName()));
		System.out.println("-----------------------------------");
		System.out.println(MultipleLanguage.getRB().getString("choose"));
		String id = scanner.nextLine();
		boolean isExist = false;
		// check tồn tại
		for (var cat : categoryDAOImp.getAll()) {
			if (cat.getId().equalsIgnoreCase(id)) {
				isExist = true;
			}
		}
		// xử lý
		if (isExist) {
			if (productDAOImp.getByCat(id).size() > 0) {
				showProduct(productDAOImp.getByCat(id));
				System.out.println();
			} else {
				System.out.println(MultipleLanguage.getRB().getString("notHaveProOf") + productDAOImp.getCatName(id));
			}
		} else {
			System.out.println(MultipleLanguage.getRB().getString("notFound") + " " + id);
		}
		System.out.println();
	}

	private void prodByName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(MultipleLanguage.getRB().getString("inputProNameWantShow"));
		String name = scanner.nextLine();
		Product p = productDAOImp.detailProduct(name);
		if (p != null) {
			System.out.println(MultipleLanguage.getRB().getString("proDetail") + " " + name);
			header();
			displayOne(p);
			border();
		} else {
			System.out.println(MultipleLanguage.getRB().getString("notFound") + " " + name);
		}
		System.out.println();
	}

	private void prodRemain3dayExp() {
		List<Product> list = productDAOImp.proRemainExp(3);
//		List<Product> list = productDAOImp.getAll();
		if (list.size() != 0) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			for (Product product : list) {
//				Date now = new Date();
//				long diff = product.getExpiration().getTime() - now.getTime();
//				System.out.println("diff = " + TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS));
//			}
			showProduct(list);
			writeToFile(list);
		} else {
			System.out.println(MultipleLanguage.getRB().getString("notFoundPro"));
		}
		System.out.println();
	}

	private int menuSortPro() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("-----------------------");
			System.out.printf("| %d. %-16s |\n",1,MultipleLanguage.getRB().getString("sortByPrice"));
			System.out.println("-----------------------");
			System.out.printf("| %d. %-16s |\n",2,MultipleLanguage.getRB().getString("sortByName"));
			System.out.println("-----------------------");
			System.out.printf("| %d. %-16s |\n",3,MultipleLanguage.getRB().getString("back"));
			System.out.println("-----------------------");
			System.out.println(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
	}

	private void sortProduct() {
		int ch;
		do {
			ch = menuSortPro();
			switch (ch) {
			case 1:
				sortByPrice();
				break;
			case 2:
				sortByName();
				break;
			case 3:
				break;
			default:
				System.out.println("[ "+MultipleLanguage.getRB().getString("choose")+ MultipleLanguage.getRB().getString("from") + "1" + MultipleLanguage.getRB().getString("to") + " 3 ]");
				break;
			}
		} while (ch != 3);
		System.out.println();
	}

	private void sortByPrice() {
		List<Product> list = productDAOImp.sortByPrice("desc");
		if (list.size() != 0) {
			System.out.println(MultipleLanguage.getRB().getString("listAfterSort"));
			showProduct(list);
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listProEmpty"));
		}
		System.out.println();
	}

	private void sortByName() {
		List<Product> list = productDAOImp.sortByName("DESC");
		if (list.size() != 0) {
			System.out.println(MultipleLanguage.getRB().getString("listAfterSort"));
			showProduct(list);
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listProEmpty"));
		}
		System.out.println();
	}

	private void updateProduct() {
		List<Product> list = productDAOImp.getAll();
		Scanner scanner = new Scanner(System.in);
		if (list.size() != 0) {
			showProduct(list);
			System.out.println(MultipleLanguage.getRB().getString("selectPro"));
			Product p = productDAOImp.getById(scanner.nextLine());
			if (p != null) {
				int ch = menuUpdatePro();
				updateAttr(ch, p);
			} else {
				System.out.println(MultipleLanguage.getRB().getString("notFoundPro"));
			}
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listProEmpty"));
		}
		System.out.println();
	}

	private int menuUpdatePro() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",1,MultipleLanguage.getRB().getString("proName"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",2,MultipleLanguage.getRB().getString("price"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",3,MultipleLanguage.getRB().getString("status"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",4,MultipleLanguage.getRB().getString("description"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",5,MultipleLanguage.getRB().getString("expire"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n",6,MultipleLanguage.getRB().getString("Category"));
			System.out.println("---------------------------");
			System.out.println(MultipleLanguage.getRB().getString("choose"));
			try {
				int ch = Integer.parseInt(scanner.nextLine());
				if (ch >= 1 && ch <= 6) {
					return ch;
				} else {
					System.out.println("[ "+MultipleLanguage.getRB().getString("choose")+ MultipleLanguage.getRB().getString("from") + "1" + MultipleLanguage.getRB().getString("to") + " 6 ]");
				}
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
	}

	private void updateAttr(int attr, Product p) {
		Scanner scanner = new Scanner(System.in);
		switch (attr) {
		case 1:
			p.inputName(productDAOImp.getAll());
			break;
		case 2:
			p.inputPrice();
//			while (true) {
//				System.out.println(MultipleLanguage.getRB().getString("product.inputPrice"));
//				String prc = scanner.nextLine();
//				if (!prc.equals("")) {
//					try {
//						float price = Float.parseFloat(prc);
//						if (price > 0) {
//							p.setPrice(price);
//							break;
//						}else {
//							System.out.println(MultipleLanguage.getRB().getString("product.errorPrice1"));
//						}
//					} catch (NumberFormatException e) {
//						System.out.println(MultipleLanguage.getRB().getString("product.errorPrice2"));
//					}
//				}else {
//					break;					
//				}
//			}
			break;
		case 3:
			p.inputStatus();
			break;
		case 4:
			p.inputDescription();
			break;
		case 5:
			p.inputExpiration();
			break;
		case 6:
			p.inputCategoryId();
			break;
		}
		if (productDAOImp.updateProduct(p)) {
			System.out.println(MultipleLanguage.getRB().getString("success"));
		} else {
			System.out.println(MultipleLanguage.getRB().getString("failed"));
		}
		System.out.println();
	}

	private void showProduct(List<Product> list) {
		header();
		list.forEach(pro -> displayOne(pro));
		border();
		System.out.println();
	}
	
	private void displayOne(Product p) {
		System.out.printf("| %-4s | %-30s | %-10s | %-10s | %-15s | %-12s | %-20s |\n",
				p.getId(),p.getName(),
				MultipleLanguage.getNumberFormat().format(p.getPrice()),
				p.getStatus() == 1 ? MultipleLanguage.getRB().getString("instock") : MultipleLanguage.getRB().getString("outstock"),
				p.getDescription(),p.getExpiration(),
				productDAOImp.getCatName(p.getCategory_id()));
	}
	
	private void header() {
		border();
		System.out.printf("| %-4s | %-30s | %-10s | %-10s | %-15s | %-12s | %-20s |\n", 
				MultipleLanguage.getRB().getString("ID"), 
				MultipleLanguage.getRB().getString("proName"), 
				MultipleLanguage.getRB().getString("price"),
				MultipleLanguage.getRB().getString("status"), 
				MultipleLanguage.getRB().getString("description"), 
				MultipleLanguage.getRB().getString("expire"), 
				MultipleLanguage.getRB().getString("Category"));
		border();
	}
	
	private void border() {
		String s = "";
		for (int i = 0; i < 123; i++) {
			s += "-";
		}
		System.out.println(s);
	}
	
	private void writeToFile(List<Product> list) {
		File file = null;
		FileWriter fwt = null;
		try {
			file = new File("D:\\workspace-eclipse\\BaiTapLon\\product.txt");
			fwt = new FileWriter(file);
			for (Product product : list) {
				String pro = "ID = " + product.getId() + " "
						+ ", Name = " + product.getName() + " "
						+ ", Status = " + (product.getStatus() == 1 ? MultipleLanguage.getRB().getString("instock") : MultipleLanguage.getRB().getString("outstock")) + ""
						+ ", Price = " + MultipleLanguage.getNumberFormat().format(product.getPrice()) + " "
						+ ", Description = " + product.getDescription() + " "
						+ ", Expiration = " + product.getExpiration() + " "
						+ ", Category = " + productDAOImp.getCatName(product.getCategory_id()) + "\n";
				fwt.write(pro);
			}
			fwt.flush();
			fwt.close();
			System.out.println("[ "+MultipleLanguage.getRB().getString("exportFileSuccess")+" product.txt ]");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
