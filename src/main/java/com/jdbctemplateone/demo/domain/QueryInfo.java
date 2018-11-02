package com.jdbctemplateone.demo.domain;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryInfo {
	private String sql;
	private MapSqlParameterSource params;

}
