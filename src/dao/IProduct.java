package dao;

import java.util.List;

import entity.Product;

public interface IProduct {
	public boolean addProduct(Product p);
	public boolean updateProduct(Product p);
	public List<Product> getAll();
	public List<Product> getByCat(String catId);
	public Product detailProduct(String name);
	public List<Product> proRemainExp(int day); //(có thể xuất ra file txt)
	public String getCatName(String id);
	public Product getById(String id);
	public List<Product> sortByPrice(String type);
	public List<Product> sortByName(String type);
}
