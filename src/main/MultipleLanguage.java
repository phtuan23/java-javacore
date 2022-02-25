package main;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MultipleLanguage {
	private static ResourceBundle resourceBundle = null;
	private static NumberFormat nb = null;

	public MultipleLanguage() {
	}

	public static ResourceBundle getRB() {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle("language.lang", new Locale("vi", "VN"));
		}
		return resourceBundle;
	}
	
	public static NumberFormat getNumberFormat() {
		if (nb == null) {
			nb = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		}
		return nb;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		MultipleLanguage.resourceBundle = resourceBundle;
	}

	public void setNb(NumberFormat nb) {
		MultipleLanguage.nb = nb;
	}
}
