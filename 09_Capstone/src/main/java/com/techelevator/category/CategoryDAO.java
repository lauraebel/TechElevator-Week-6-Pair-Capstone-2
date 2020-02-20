package com.techelevator.category;

import java.util.List;

public interface CategoryDAO {
	
	public void save(Category newCategory);
	
	public List<Category> getAllCategories();
	
	public List<Category> findCategoryById(long id);
	
	public List<Category> findCategoryByName(String name);
	
	public void update(Category category);
	
	public void delete(long id);
	
	}


