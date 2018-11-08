package com.jdbctemplateone.demo.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CustomTable(name = "mytable")
public class User {
	@CustomColumn(name = "id")
	private int id;
	@CustomColumn(name = "first_name")
	private String fName;
	@CustomColumn(name = "last_name")
	private String email;
	
	private List<String> queryStringsList = new ArrayList<>();
	private StringBuilder sb = new StringBuilder().append("SELECT ");
	private MapSqlParameterSource params = new MapSqlParameterSource();
	
	public boolean flag;
	public boolean whereFlag;
	
	public static User select() {
		return new User();
	}

	public User id() {
		helper("id");
		queryStringsList.add("id");
		return this;
	}
	
	public User fName() {
		helper("first_name");		
		queryStringsList.add("first_name");
		return this;
	}
	
	public User email() {
		helper("last_name");
		sb.append("last_name, ");
		queryStringsList.add("last_name");
		return this;
	}
	
	public User from() {
		sb.append(" FROM STUDENTS ");
		flag = !flag;
		return this;
	}
	
	public User whereId(int id) {
		helper2("id =:id");
		params.addValue("id", id);
		return this;
	}
	
	public User whereFName(String fn) {
		helper2("first_name =:first_name");
		params.addValue("first_name", fn);
		return this;
	}
	
	public User whereEmail(String email) {
		helper2("email =:email");
		params.addValue("email", email);
		return this;
	}
	
	public QueryInfo build() {
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setSql(this.sb.toString());
		queryInfo.setParams(this.params);
		return queryInfo;
	}
	
	private void helper(String columnName) {
		if(!flag) {
			sb.append(columnName);
			flag = true;
		} else {
			sb.append(", "+columnName);
		}
	}
	
	private void helper2(String columnName) {
		if(!flag) {
			sb.append("WHERE "+columnName);
			flag = !flag;
		}else {
			sb.append(" and "+columnName);
		}
	}
	
	
}
