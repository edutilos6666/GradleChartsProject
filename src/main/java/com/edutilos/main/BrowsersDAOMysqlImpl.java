package com.edutilos.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class BrowsersDAOMysqlImpl implements BrowsersDAO{

	private JdbcTemplate template; 
	
	
	public void dropTable() {
		String sql = "drop table if exists Browser"; 
		template.update(sql); 
	}
	
	public void createTable() {
		String sql = "create table if not exists Browser ("
				+ "name varchar(50) not null, "
				+ "value double not null"
				+ ")"; 
		template.update(sql); 
	}
	
	public BrowsersDAOMysqlImpl() {
		  template = mysqlTemplate(); 
		}
	
	@Override
	public void save(Browser browser) {
		String sql = "insert into Browser values(?, ?)"; 
		template.update(sql, new Object[]{browser.getHeader(), browser.getValue()}); 
	}





	@Override
	public List<Browser> findAll() {
		List<Browser> all = 
				template.query("select * from Browser", new RowMapper<Browser>() {

					@Override
					public Browser mapRow(ResultSet rs, int rowNum) throws SQLException {
					  return new Browser(rs.getString(1), rs.getDouble(2)); 
					}
					
				});

		return all ; 
	}
   
	
	
	public JdbcTemplate mysqlTemplate() {
		return new JdbcTemplate(mysqlDataSource()); 
	}
	public DataSource mysqlDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource(); 
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
		ds.setUsername("root");
		ds.setPassword("");
		return ds; 
	}
}
