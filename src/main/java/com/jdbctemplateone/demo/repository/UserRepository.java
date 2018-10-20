package com.jdbctemplateone.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.jdbctemplateone.demo.domain.User;

@Service

public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTmplate;
	
	
	
	public List<User> findAll(){
		String qry = "SELECT * FROM student";
		return this.namedJdbcTmplate.query(qry, (rs)->{
			if(rs.next()) {
				List<User> users = new ArrayList<>();
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFName(rs.getString("first_name"));
				user.setEmail(rs.getString("email"));
				users.add(user);
				while(rs.next()) {					
					User user2 = new User();
					user2.setId(rs.getInt("id"));
					user2.setFName(rs.getString("first_name"));
					user2.setEmail(rs.getString("email"));
					users.add(user2);
				}
				return users;
			}else {
				//TODO: logging
				return null;
			}
		});
	}
	
	public List<String> findFirstNames(){
		String qry = "SELECT first_name FROM student";
		return this.namedJdbcTmplate.query(qry, (rs)->{
			if(rs.next()) {
				List<String> listString = new ArrayList<>();
				listString.add(rs.getString("first_name"));
				while(rs.next()) {
					listString.add(rs.getString("first_name"));
				}
				return listString;
			}else {
				//TODO: Logging
				return null;
			}
		});
		//return this.namedJdbcTmplate.queryForList("select first_name from student", String.class);
	}
	
	public User findUserById(int userid) {
		String qry = "SELECT * FROM student WHERE id=:userID";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userID",userid );
		return this.namedJdbcTmplate.query(qry, params,(rs)->{
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFName(rs.getString("first_name"));
				user.setEmail(rs.getString("email"));
				return user;
			}else {
				//TODO: log the error here
				return null;
			}
		});
	}
	
	/*
	 * finds a user by given id and first name.
	 * @return a user
	 */
	public User findUserByIdAndFirstName(int userid, String firstName) {
		String qry = "SELECT * FROM student WHERE id=:userid and first_name=:fname";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userid", userid);
		params.addValue("fname", firstName);
		
		return (User)this.namedJdbcTmplate.query(qry, params,(rs)->{
			if(rs.next()) {
			System.out.println(rs.getObject(1));
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setFName(rs.getString("first_name"));
			user.setEmail(rs.getString("email"));
			return user;
			}else {
				//TODO: log the error here
				return null;
			}
		});
	}

}
