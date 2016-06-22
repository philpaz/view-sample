package com.rest.json.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rest.json.bean.ManufacturerBean;

@Component
public class ManufacturerDAO {
	
	@Autowired	
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(ManufacturerDAO.class.getName());

	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ManufacturerBean> getAllManufacturersRowMapper(String nonce){
 		String sql = "SELECT * FROM manufacturer where nonce = '"+nonce+"'";
		return jdbcTemplate.query(sql,new RowMapper<ManufacturerBean>(){
			public ManufacturerBean mapRow(ResultSet rs, int rownumber) throws SQLException {
				ManufacturerBean manufacturerBean = new ManufacturerBean();
				manufacturerBean.setId(rs.getInt(1)); //AI
				manufacturerBean.setName(rs.getString(2)); //DEFAULT SUPPLIER
				manufacturerBean.setActive(rs.getInt(3));
				return manufacturerBean;
			}	
		});
	}
	
	public List<ManufacturerBean> getIdNameManufacturersRowMapper(String nonce){
 		String sql = "SELECT * FROM manufacturer where nonce = '"+nonce+"' and active = 1";
		return jdbcTemplate.query(sql,new RowMapper<ManufacturerBean>(){
			public ManufacturerBean mapRow(ResultSet rs, int rownumber) throws SQLException {
				ManufacturerBean manufacturerBean = new ManufacturerBean();
				manufacturerBean.setId(rs.getInt(1)); //AI
				manufacturerBean.setName(rs.getString(2)); //DEFAULT SUPPLIER
				return manufacturerBean;
			}	
		});
	}
	
	public ManufacturerBean getManufacturerByIdRowMapper(Integer id, String nonce){
		return jdbcTemplate.queryForObject("SELECT * FROM manufacturer WHERE id = " +id.intValue() + " and nonce = '"+nonce.toString()+"'",
				new RowMapper<ManufacturerBean>(){
			public ManufacturerBean mapRow(ResultSet rs, int rownumber) throws SQLException {
				ManufacturerBean manufacturerBean = new ManufacturerBean();
				manufacturerBean.setId(rs.getInt(1)); //AI
				manufacturerBean.setName(rs.getString(2)); //DEFAULT SUPPLIER
				manufacturerBean.setActive(rs.getInt(3));
				return manufacturerBean;
			}	
		});
	}
	@Transactional
	public ManufacturerBean insertManufacturerByPreparedStatement(final ManufacturerBean manufacturerBean){
		final String query ="INSERT INTO manufacturer "
					+ " (name, "	//REQUIRED UI
					+ " active,  "  //REQUIRED UI
					+ " nonce )  " 
					+ " VALUES "
					+ "(?,?,?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection  connection)
						throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
		            ps.setString(1,manufacturerBean.getName());
		            ps.setInt(2,manufacturerBean.getActive());
		            ps.setString(3,manufacturerBean.getNonce());
					return ps;
				}
		    },
		    keyHolder);
			manufacturerBean.setId(keyHolder.getKey().intValue());
		return manufacturerBean;	
		}
	
	public ManufacturerBean updateManufacturerByPreparedStatement(final ManufacturerBean manufacturerBean){
		final String query ="UPDATE manufacturer SET "
						+ " name = ?, " //REQUIRED UI
						+ " active = ?, " //REQUIRED UI
						+ " nonce = ? "
						+ " WHERE ID = ? ";
		
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection  connection)
						throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(query);	            
		            ps.setString(1,manufacturerBean.getName());
		            ps.setInt(2,manufacturerBean.getActive());
		            ps.setString(3,manufacturerBean.getNonce());
		            ps.setInt(4,manufacturerBean.getId());

		            return ps;
				}
		    });
		return manufacturerBean;	
		}

}
