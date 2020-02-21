package com.techelevator.category;

import java.util.List;

public interface CategoryDAO {
	
	public void save(Category newCategory);
	
	public List<Category> getAllCategories();
	
	public Category findCategoryById(long id);
		
	public void update(Category category);
	
	public void delete(long id);
	
	}


