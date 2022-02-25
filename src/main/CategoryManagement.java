package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import dao.CategoryDAOImp;
import entity.Category;

public class CategoryManagement {
	CategoryDAOImp cdi = new CategoryDAOImp();

	private int menuCategory() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("---------------------------"); // 25
			System.out.printf("| %d. %-20s |\n", 1,
					MultipleLanguage.getRB().getString("list") + " " + MultipleLanguage.getRB().getString("Category"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n", 2,
					MultipleLanguage.getRB().getString("add") + " " + MultipleLanguage.getRB().getString("Category"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n", 3, MultipleLanguage.getRB().getString("delete") + " "
					+ MultipleLanguage.getRB().getString("Category"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n", 4, MultipleLanguage.getRB().getString("search") + " "
					+ MultipleLanguage.getRB().getString("Category"));
			System.out.println("---------------------------");
			System.out.printf("| %d. %-20s |\n", 5, MultipleLanguage.getRB().getString("back"));
			System.out.println("---------------------------");
			System.out.print(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
	}

	public void mainMenuCate() {
		int ch;
		do {
			ch = menuCategory();
			switch (ch) {
			case 1:
				listCate();
				break;
			case 2:
				addCate();
				break;
			case 3:
				deleteCate();
				break;
			case 4:
				searchCate();
				break;
			case 5:
				break;
			default:
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
				break;
			}
		} while (ch != 5);
	}

	private void listCate() {
		int ch;
		do {
			ch = listCategory();
			switch (ch) {
			case 1:
				showAllCategory();
				break;
			case 2:
				categoryTree();
				break;
			case 3:
				getCatByID();
				break;
			case 4:
				break;
			default:
				break;
			}
		} while (ch != 4);
	}

	private int listCategory() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("-------------------------------");
			System.out.printf("| %d. %-24s |\n", 1,
					MultipleLanguage.getRB().getString("list") + " " + MultipleLanguage.getRB().getString("Category"));
			System.out.println("-------------------------------");
			System.out.printf("| %d. %-24s |\n", 2, MultipleLanguage.getRB().getString("cateTree"));
			System.out.println("-------------------------------");
			System.out.printf("| %d. %-24s |\n", 3, MultipleLanguage.getRB().getString("detail") + " "
					+ MultipleLanguage.getRB().getString("Category"));
			System.out.println("-------------------------------");
			System.out.printf("| %d. %-24s |\n", 4, MultipleLanguage.getRB().getString("back"));
			System.out.println("-------------------------------");
			System.out.print(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
	}

	private void categoryTree() {
		List<Category> list = cdi.getAll();
		if (list.size() != 0) {
			listCatTree(list, "0", "");
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listCateEmpty"));
		}
		System.out.println();
	}

	private void listCatTree(List<Category> list, String parentId, String title) {
		int lv = 1;
		for (Category category : list) {
			if (category.getParentId().equals(parentId)) {
				System.out.println(title + lv + ". " + category.getName());
				String _title = "\t" + title + lv + ".";
				// "" + 1 + ". " => 1.
				listCatTree(list, category.getId(), _title);
				lv++;
			}
		}
	}

//	private void catgTree(List<Category> list, String parentId) {
//		int cnt1 = 1;
//		int cnt2 = 1;
//		int cnt3 = 1;
//		for (Category category : list) {
//			if (category.getParentId().equals(parentId)) {
//				System.out.println(cnt1 + ". " + category.getName());
//				for (Category category2 : list) {
//					if (category2.getParentId().equals(category.getId())) {
//						System.out.println("\t" + cnt1 + "." + cnt2 + " " + category2.getName());
//						for (Category category3 : list) {
//							if (category3.getParentId().equals(category2.getId())) {
//								System.out.println("\t\t" + + cnt1 + "." + cnt2 + "." + cnt3 + " " + category3.getName());
//								cnt3++;
//							}
//						}
//						cnt2++;
//					}
//				}
//			}
//			cnt1++;
//		}
//	}

	private void getCatByID() {
		List<Category> list = cdi.getAll();
		if (list.size() != 0) {
			Scanner scanner = new Scanner(System.in);
			showCategory(list);
			System.out.println(MultipleLanguage.getRB().getString("cateIdWantShow"));
			String id = scanner.nextLine();
			if (cdi.getById(id) != null) {
				System.out.println(MultipleLanguage.getRB().getString("Category") + " ID = " + id);
				header();
				displayOne(cdi.getById(id));
				body();
			} else {
				System.out.println(MultipleLanguage.getRB().getString("notFound") + " "
						+ MultipleLanguage.getRB().getString("Category") + " " + id);
			}
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listCateEmpty"));
		}
		System.out.println();
	}

	private void addCate() {
		while (true) {
			List<Category> list = cdi.getAll();
			Scanner scanner = new Scanner(System.in);
			Category category = new Category();
			category.input(list);
			int ch = 0;
			if (cdi.addCat(category)) {
				System.out.println(MultipleLanguage.getRB().getString("addedCat"));
				while (true) {
					System.out.println(MultipleLanguage.getRB().getString("continue") + " ? (1: "
							+ MultipleLanguage.getRB().getString("continue") + " - 2: "
							+ MultipleLanguage.getRB().getString("stop") + ")");
					try {
						ch = Integer.parseInt(scanner.nextLine());
						if (ch == 2 || ch == 1) {
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println("[ " + MultipleLanguage.getRB().getString("pleaseChoose") + " 1 "
								+ MultipleLanguage.getRB().getString("or") + " 2 ]");
					}
				}

			} else {
				System.err.println(MultipleLanguage.getRB().getString("addedFailCat"));
			}
			if (ch == 2) {
				break;
			}
		}
		System.out.println();
	}

	private void deleteCate() {
		List<Category> list = cdi.getAll();
		if (list.size() != 0) {
			showCategory(list);
			Scanner scanner = new Scanner(System.in);
			System.out.println(MultipleLanguage.getRB().getString("chooseCateWantDelete"));
			String id = scanner.nextLine();
			boolean isExist = false;
			for (Category category : list) {
				if (category.getId().equalsIgnoreCase(id)) {
					isExist = true;
					if (cdi.deleteCat(id)) {
						System.out.println(MultipleLanguage.getRB().getString("deleted") + " "
								+ MultipleLanguage.getRB().getString("Category") + " " + id);
					} else {
						System.err.println(MultipleLanguage.getRB().getString("cantDeleteThisCat"));
					}
				}
			}
			if (!isExist) {
				System.out.println(MultipleLanguage.getRB().getString("notFound") + " "
						+ MultipleLanguage.getRB().getString("Category") + " " + id);
			}
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listCateEmpty"));
		}
		System.out.println();
	}

	private void searchCate() {
		List<Category> list = cdi.getAll();
		if (list.size() != 0) {
			Scanner scanner = new Scanner(System.in);
			System.out.println(MultipleLanguage.getRB().getString("inputNameSearch"));
			String name = scanner.nextLine();
			List<Category> listCat = cdi.searchByName(name);
			if (listCat.size() > 0) {
				Collections.sort(listCat, new Comparator<Category>() {
					@Override
					public int compare(Category o1, Category o2) {
						return (int) -(Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId()));
					}
				});
				showCategory(listCat);
			} else {
				System.out.println(MultipleLanguage.getRB().getString("notFound") + " "
						+ MultipleLanguage.getRB().getString("Category") + " " + name);
			}
		} else {
			System.out.println(MultipleLanguage.getRB().getString("listCateEmpty"));
		}
		System.out.println();
	}

	private void showCategory(List<Category> list) {
		header();
		list.forEach(c -> displayOne(c));
		body();
	}

	private void displayOne(Category c) {
		System.out.printf("| %-5s | %-30s | %-10s | %-15s | %13d |\n", c.getId(), c.getName(),
				(c.getStatus() ? MultipleLanguage.getRB().getString("show")
						: MultipleLanguage.getRB().getString("hiden")),
				cdi.getParentName(c.getParentId()), cdi.getTotalProduct(c.getId()));
	}

	private void showAllCategory() {
		List<Category> list = cdi.getAll();
		showCategory(list);
	}

	private void header() {
		body();
		System.out.printf("| %-5s | %-30s | %-10s | %-15s | %-13s |\n", MultipleLanguage.getRB().getString("ID"),
				MultipleLanguage.getRB().getString("name") + " " + MultipleLanguage.getRB().getString("Category"),
				MultipleLanguage.getRB().getString("status"),
				MultipleLanguage.getRB().getString("Category") + " " + MultipleLanguage.getRB().getString("parent"),
				MultipleLanguage.getRB().getString("total") + " " + MultipleLanguage.getRB().getString("product"));
		body();
	}

	private void body() {
		String s = "";
		for (int i = 0; i < 89; i++) {
			s += "-";
		}
		System.out.println(s);
	}
}
