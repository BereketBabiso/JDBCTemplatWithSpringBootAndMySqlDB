package com.jdbctemplateone.demo.util;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.SelectQuery;

public class SelectQueryBuilder extends SelectQuery{
	SelectQueryBuilder selectQueryBuilder;
	MapSqlParameterSource params = new MapSqlParameterSource();

	
	
	public static SelectQueryBuilder select() {
		return new SelectQueryBuilder();
	}
	
	public WhereBuilder tableAndColumns(String tableName, String...columns) {		
		this.selectQueryBuilder = (SelectQueryBuilder) new SelectQueryBuilder()
				                                      .addCustomColumns(columns)
													  .addCustomFromTable(tableName);		
		return this.new WhereBuilder();
	}
	
	private String helper(String str) {
		String result = null;
		String temp;
		String pattern1 = "'|\\(|\\)";
		String pattern2 = "= ";
		String substitute = "=:";
		temp = str.replaceAll(pattern1, "");	
		result = temp.replaceAll(pattern2, substitute);
		return result;
	}
	
	public class WhereBuilder {
		public WhereBuilder where(String columnName, String value) {
			selectQueryBuilder.addCondition(BinaryCondition.equalTo(columnName, columnName));
			params.addValue(columnName, value);
			return this;
		}
		
		public String build() {
			return helper(selectQueryBuilder.toString());
		}
	}
}
