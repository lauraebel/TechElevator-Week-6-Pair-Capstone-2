package com.techelevator.category;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCategoryDAO implements CategoryDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCCategoryDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Category newCategory) {
		String sql = "INSERT INTO category (id, name) VALUES (DEFAULT, ?) RETURNING id";
		Integer categoryId = jdbcTemplate.queryForObject(sql, Integer.class, newCategory.getCategoryName());
	
		newCategory.setCategoryId(categoryId);
	}

	@Override
	public void update(Category category) {
		String sql = "UPDATE category SET name = ? WHERE id = ? ";
		jdbcTemplate.update(sql, category.getCategoryName(), category.getCategoryId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM category WHERE id = ? ";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT id, name ");
		
		while (rows.next()) {
			categories.add(mapRowToCategory(rows));
		}
		return categories;
	}

	@Override
	public Category findCategoryById(long id) {
		Category category = null;
		String sql = "SELECT id, name FROM category WHERE id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
		
		if (rows.next()) {
			category = mapRowToCategory(rows);
		}
		return category;
	}

	
	private Category mapRowToCategory(SqlRowSet result) {
		Category selectedCategory;
		selectedCategory = new Category();
		selectedCategory.setCategoryId(result.getLong("id"));
		selectedCategory.setCategoryName(result.getString("name"));
		return selectedCategory;
	}

}
