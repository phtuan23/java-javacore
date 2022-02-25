package dao;

import java.util.List;

import entity.Category;

public interface ICategory {
	public List<Category> getAll();
	public boolean addCat(Category c);
	public Category getById(String id);
	public boolean deleteCat(String id);
	public List<Category> searchByName(String name);
	public String getParentName(String id);
	public int getTotalProduct(String id);
}
