package main;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainClass {
	
	private int mainMenu() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(MultipleLanguage.getRB().getString("main.title"));
			System.out.println();
			System.out.println(MultipleLanguage.getRB().getString("main.function1"));
			System.out.println("-----------------------");
			System.out.println(MultipleLanguage.getRB().getString("main.function2"));
			System.out.println("-----------------------");
			System.out.println(MultipleLanguage.getRB().getString("main.function3"));
			System.out.println("-----------------------");
			System.out.println(MultipleLanguage.getRB().getString("main.exitFunction"));
			System.out.println("-----------------------");
			System.out.print(MultipleLanguage.getRB().getString("choose"));
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
			}
		}
	}
	
	private void changeLanguage() {
		MultipleLanguage ml = new MultipleLanguage();
		Scanner scanner = new Scanner(System.in);
		System.out.println(MultipleLanguage.getRB().getString("eng"));
		System.out.println(MultipleLanguage.getRB().getString("vie"));
		System.out.print(MultipleLanguage.getRB().getString("choose"));
		int ch = Integer.parseInt(scanner.nextLine());
		if (ch == 1) {
			ml.setResourceBundle(ResourceBundle.getBundle("language.lang", Locale.US));;
			ml.setNb(NumberFormat.getCurrencyInstance(Locale.US));
		}else {
			ml.setResourceBundle(ResourceBundle.getBundle("language.lang", new Locale("vi","VN")));;
			ml.setNb(NumberFormat.getCurrencyInstance(new Locale("vi","VN")));
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		MainClass mc = new MainClass();
		CategoryManagement cm = new CategoryManagement();
		ProductManagement pm = new ProductManagement();
		int ch;
		do {
			ch = mc.mainMenu();
			switch (ch) {
			case 1:
				mc.changeLanguage();
				break;
			case 2:
				cm.mainMenuCate();
				break;
			case 3:
				pm.mainMenuProduct();
				break;
			case 4:
				System.out.println(MultipleLanguage.getRB().getString("goodbye"));
				break;
			default:
				System.out.println(MultipleLanguage.getRB().getString("msg.errorInputInt"));
				break;
			}
		} while (ch != 4);
		System.out.println();
	}
}
