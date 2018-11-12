package com.jdbctemplateone.demo.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.jdbctemplateone.demo.domain.QueryInfo;
import com.jdbctemplateone.demo.domain.User;
import com.jdbctemplateone.demo.repository.UserRepository;
import com.jdbctemplateone.demo.util.DbConstants;
import com.jdbctemplateone.demo.util.SelectQueryBuilder;
import com.jdbctemplateone.demo.util.UpdateQueryBuilder;

@RestController
@RequestMapping("/users")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping()
	public List<User> users(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/firstnames")
	public List<String> firstNames(){
		return this.userRepository.findFirstNames();
	}
	
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable String userId){
		return this.userRepository.findUserById(Integer.parseInt(userId));
	}
	
	@RequestMapping(name="/user/{userId}/{userNafirstNameme}",method=RequestMethod.POST)
	public User getUserByIdAndFirstNam(@PathVariable String userId, @PathVariable String firstName) {
		return this.userRepository.findUserByIdAndFirstName(Integer.parseInt(userId), firstName);
	}
	
	@GetMapping("/test")
	public String test() {
		long startTime = new Date().getTime();
		QueryInfo x = User.select().id().fName().from().whereId(2).build();
		long endTime = new Date().getTime();
		logger.info("Time taken to build the query using custom builder : "+(endTime-startTime)+"ms");
		return x.getSql();
	}
	
	@GetMapping("/test2")
	public String test2() {
		long startTime = new Date().getTime();
		String qry = null;
//		SelectQueryBuilder selectQueryBuilder = (SelectQueryBuilder)new SelectQueryBuilder()
//												.addCustomColumns(DbConstants.IDCOLUMN, DbConstants.FIRSTNAME)
//												.addCustomFromTable(DbConstants.USERTABLE)
//												.addCondition(BinaryCondition.equalTo(DbConstants.IDCOLUMN, DbConstants.IDCOLUMN));
//		qry = selectQueryBuilder.build();
		//qry = SelectQueryBuilder.select(DbConstants.USERTABLE, DbConstants.IDCOLUMN, DbConstants.FIRSTNAME).build();
		
		qry = SelectQueryBuilder.select()
				.tableAndColumns(DbConstants.USERTABLE, DbConstants.IDCOLUMN, DbConstants.FIRSTNAME)
				.where(DbConstants.IDCOLUMN, "1").build();
		
		long endTime = new Date().getTime();
		logger.info("Time taken to build the query using healthmarket library: "+ (endTime - startTime)+"ms");
		return qry;
	}
	
	@GetMapping("/test5")
	public String test5() {
		User user = new User();
		user.setId(3);
		user.setFName("bere");
		QueryInfo queryInfo = new UpdateQueryBuilder(user).update("fName", "Lisa").where().condition("id", 3).build();
		return queryInfo.getSql();
	}

}
