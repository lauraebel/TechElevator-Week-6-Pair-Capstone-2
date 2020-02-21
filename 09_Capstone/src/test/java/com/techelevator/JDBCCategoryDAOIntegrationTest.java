package com.techelevator;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.category.Category;
import com.techelevator.category.CategoryDAO;
import com.techelevator.category.JDBCCategoryDAO;


public class JDBCCategoryDAOIntegrationTest {
	
	private CategoryDAO dao;
	private JdbcTemplate jdbcTemplate;
	private int categoryId;
	
	private static SingleConnectionDataSource dataSource;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO category(id, name) VALUES (DEFAULT, 'name') RETURNING id";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		rows.next();
		categoryId = rows.getInt("id");
		
		dao = new JDBCCategoryDAO(dataSource);
	}

	@Test
	public void select_category_by_id() {
		Category category = getCategory("name");
		dao.save(category);
		
		Category selectedCategory = dao.findCategoryById(category.getCategoryId());
		
		Assert.assertNotNull("returned null", selectedCategory);
		
		Assert.assertEquals("IDs Not equal", category.getCategoryId(), selectedCategory.getCategoryId());
		Assert.assertEquals("Names not equal", category.getCategoryName(), selectedCategory.getCategoryName());
	}
	
	@Test
	public void insert_new_category() {
		Category category = getCategory("name");
		dao.save(category);
		
		Assert.assertNotEquals("Id not set", 0, category.getCategoryId());
		
		Category selectedCategory = dao.findCategoryById(category.getCategoryId());
		
		Assert.assertNotNull("null", selectedCategory);
		Assert.assertEquals("Category ID not as expected", category.getCategoryId(), selectedCategory.getCategoryId());
		Assert.assertEquals("Category name not as expected", category.getCategoryName(), selectedCategory.getCategoryName());
	}
	
	@Test
	public void update_category() {
		Category category = getCategory("name");
		dao.save(category);
		
		category.setCategoryName("updatedName");
		
		dao.update(category);
		
		Category updatedCategory = dao.findCategoryById(category.getCategoryId());
		
		Assert.assertNotNull("updatedCategory is null", updatedCategory);
		Assert.assertEquals("Updated Category IDs do not match", category.getCategoryId(), updatedCategory.getCategoryId());
		Assert.assertEquals("Updated Category names do not match", category.getCategoryName(), updatedCategory.getCategoryName());
	}
	
	@Test
	public void deleting_a_category() {
		Category category = getCategory("name");
		dao.save(category);
		
		dao.delete(category.getCategoryId());
		
		Category deletedCategory = dao.findCategoryById(category.getCategoryId());
		Assert.assertNull(deletedCategory);
	}
	
	private Category getCategory(String name) {
		Category selectedCategory = new Category();
		selectedCategory.setCategoryName(name);
		return selectedCategory;
	}

}
