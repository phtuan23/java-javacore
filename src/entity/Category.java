package entity;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import main.MultipleLanguage;

public class Category {
	private String id;
	private String name;
	private boolean status;
	private String parentId;
	
	public Category() {
	}

	public Category(String id, String name, boolean status, String parentId) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.parentId = parentId;
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

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public void input(List<Category> list) {
		inputID(list);
		inputName(list);
		inputStatus();
		inputParentID(list);
	}
	
	public void display() {
		System.out.printf("| %-15s | %-30s | %-10s | %-15s | \n",this.id,this.name,(this.status ? "Hiển thị" : "Ẩn"),this.parentId);
	}
	
	private void inputID(List<Category> list) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(MultipleLanguage.getRB().getString("cate.inputId"));
			boolean isExist = false;
			String id = scanner.nextLine();
			for (Category c : list) {
				if (c.getId().equalsIgnoreCase(id)) {
					isExist = true;
				}
			}
			try {
				int _id = Integer.parseInt(id);
				if (isExist == false) {
					if (_id > 0) {
						this.id = Integer.toString(_id);
						break;
					}else {
						System.err.println(MultipleLanguage.getRB().getString("cate.inputErr1"));
					}
				}else {
					System.err.println(MultipleLanguage.getRB().getString("cate.inputErr2"));
				}
			} catch (NumberFormatException e) {
				System.err.println(MultipleLanguage.getRB().getString("cate.inputErr3"));
			}
		}
	}
	
	private void inputName(List<Category> list) {
		Scanner scanner = new Scanner(System.in);
		Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		Pattern pattern = Pattern.compile("(.){6,30}");
		while (true) {
			boolean isExist = false;
			boolean check = false;
			System.out.println(MultipleLanguage.getRB().getString("cate.inputName"));
			String name = scanner.nextLine();
			
			// kiểm tra ký tự đặc biệt	
			if (special.matcher(name).matches()) {
				check = true;
			}
			
			// kiểm tra đã tồn tại.
			for (Category c : list) {
				if (c.getName().equalsIgnoreCase(name)) {
					isExist = true;
				}
			}
			
			if (pattern.matcher(name).matches()) {
				if (isExist == false) {
					if (check == false) {
						this.name = name;
						break;						
					}else {
						System.err.println(MultipleLanguage.getRB().getString("cate.inputNameErr1"));
					}
				}else {
					System.err.println(MultipleLanguage.getRB().getString("cate.inputNameErr2"));
				}
			}else {
				System.err.println(MultipleLanguage.getRB().getString("cate.inputNameErr3"));
			}
			
		}
	}
	
	private void inputStatus() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(MultipleLanguage.getRB().getString("cate.inputStt"));
		this.status = Integer.parseInt(scanner.nextLine()) > 0 ? true : false;
	}
	
	private void inputParentID(List<Category> list) {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println(MultipleLanguage.getRB().getString("choose") + " " + MultipleLanguage.getRB().getString("Category") + " "+MultipleLanguage.getRB().getString("parent"));
			System.out.println("-----------------------------------------------");
			System.out.printf("| %-15s | %-25s |\n",
					MultipleLanguage.getRB().getString("ID") + " "+MultipleLanguage.getRB().getString("Category"),
					MultipleLanguage.getRB().getString("name") + " "+MultipleLanguage.getRB().getString("Category"));
			System.out.println("-----------------------------------------------");
			for (Category c : list) {
				System.out.printf("| %-15s | %-25s |\n",c.getId(),c.getName());
			}
			System.out.println("-----------------------------------------------");
			try {
				int parentId = Integer.parseInt(scanner.nextLine());
				if (Integer.toString(parentId).equals("")) {
					this.parentId = "0";
					break;
				}else {
					boolean isExist = false;
					for (Category c : list) {
						if (Integer.parseInt(c.getId()) == parentId) {
							isExist = true;
						}
					}
					if (isExist) {
						this.parentId = Integer.toString(parentId);
						break;
					}else {
						System.err.println(MultipleLanguage.getRB().getString("cateNotExist"));
					}
				}
			} catch (Exception e) {
				System.err.println(MultipleLanguage.getRB().getString("cateNotExist"));
			}
		}
	}
}
