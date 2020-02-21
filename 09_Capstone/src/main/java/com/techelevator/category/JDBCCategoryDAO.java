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
		String sql = "INSERT INTO category(id, name) " + "VALUES (?, ?) ";
		newCategory.setCategoryId(getNextCategoryId());
		jdbcTemplate.update(sql, newCategory.getCategoryId(), newCategory.getCategoryName());
	}

	@Override
	public void update(Category category) {
		String sql = "UPDATE category SET name = ? WHERE id = ? ";
		jdbcTemplate.update(sql, category.getCategoryName());
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
		String sql = "SELECT id, name WHERE id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
		
		if (rows.next()) {
			category = mapRowToCategory(rows);
		}
		return category;
	}

	private long getNextCategoryId() {
		SqlRowSet nextCategoryResult = jdbcTemplate.queryForRowSet("SELECT nextval('deq_category_id')");
		if (nextCategoryResult.next()) {
			return nextCategoryResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new category");
		}
	}
	
	private Category mapRowToCategory(SqlRowSet result) {
		Category selectedCategory;
		selectedCategory = new Category();
		selectedCategory.setCategoryId(result.getLong("id"));
		selectedCategory.setCategoryName(result.getString("name"));
		return selectedCategory;
	}

}
