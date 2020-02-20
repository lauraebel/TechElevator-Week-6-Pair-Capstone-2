package com.techelevator.category;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCCategoryDAO implements CategoryDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCCategoryDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Category newCategory) {
		
	}

	@Override
	public void update(Category category) {
		
	}

	@Override
	public void delete(long id) {
		
	}

	@Override
	public List<Category> getAllCategories() {
		return null;
	}

	@Override
	public List<Category> findCategoryById(long id) {
		return null;
	}

	@Override
	public List<Category> findCategoryByName(String name) {
		return null;
	}

}
