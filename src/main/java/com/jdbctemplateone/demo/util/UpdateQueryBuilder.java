package com.jdbctemplateone.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.jdbctemplateone.demo.domain.CustomColumn;
import com.jdbctemplateone.demo.domain.CustomTable;
import com.jdbctemplateone.demo.domain.QueryInfo;

public class UpdateQueryBuilder<T> {
	T t;
	MapSqlParameterSource params = new MapSqlParameterSource();
	UpdateQuery updateQuery;
	Logger logger = LoggerFactory.getLogger(UpdateQueryBuilder.class);
	
	public UpdateQueryBuilder(T instance) {
		this.updateQuery = new UpdateQuery(new Table(instance.getClass().getAnnotation(CustomTable.class).name()));
		this.t= instance;
	}
	
	public UpdateQueryBuilder<T> update(String attributeName, T value) {
		try {
			String columnName = this.t.getClass().getDeclaredField(attributeName)		
				                  .getAnnotation(CustomColumn.class).name();
		this.updateQuery.addCustomSetClause(columnName, columnName);
		params.addValue(columnName, value);
		return this;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}
	
	public WhereBuilder where() {
		return this.new WhereBuilder();
	}
	
	public class WhereBuilder {
		
		public WhereBuilder condition(String attributeName, T value) {
			try {
				String columnName = t.getClass().getDeclaredField(attributeName)
						             .getAnnotation(CustomColumn.class).name();
				updateQuery.addCondition(BinaryCondition.equalTo(columnName, columnName));
				params.addValue(columnName, value);
				return this;
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			return null;
		}
		
		public QueryInfo build() {
			String qry = updateQuery.toString();
			QueryInfo queryInfo = new QueryInfo();
			queryInfo.setSql(qry);
			queryInfo.setParams(params);
			return queryInfo;
		}
	}	

}
