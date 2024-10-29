package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDAO {
	
	private String jdbcURL="jdbc:mysql://localhost:3306/userdb";
	private String jdbcUserName="root";
	private String jdbcPassword="abdulSAMAD$$$";
	
	private static final String INSERT_USERS_SQL="INSERT INTO users"+"(uname,email,country,passwd) VALUES "+"(?,?,?,?);";
	private static final String SELECT_USER_BY_ID="SELECT * FROM users where id=?;";
	private static final String SELECT_ALL_USERS="select * from users;";
	private static final String DELETE_USER_SQL="delete from users where id=?;";
	private static final String UPDATE_USERS_SQL="update users set name=?, email=?, country=?, password=?;";
	
	
	public UserDAO() {
		
		
	}
	//database connection
	public Connection getConnection() {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertUser(User user) {
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getCountry());
			preparedStatement.setString(4,user.getPassword());
			
			preparedStatement.executeUpdate();
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	
	
	public User selectUser(int id) {
		
		User user= new User();
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1,id);
			
			ResultSet  resultSet=preparedStatement.executeQuery();
			user.setName(resultSet.getString("uname"));
			user.setEmail(resultSet.getString("email"));
			user.setCountry(resultSet.getString("country"));
			user.setPassword(resultSet.getString("passwd"));
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
		
	}
	
	public List<User> selectAllUsers(){
		List<User> users=new ArrayList<User>();
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet  resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String uname=resultSet.getString("uname");
				String email=resultSet.getString("email");
				String country=resultSet.getString("country");
				String password=resultSet.getString("passwd");
				
				users.add(new User(uname,email,country,password));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	public boolean deleteUser(int id) {
		boolean status=false;
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			
			PreparedStatement preparedStatement =connection.prepareStatement(DELETE_USER_SQL);
			preparedStatement.setInt(1,id);
			
			status=preparedStatement.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return status;
		
	}
	
	
	public boolean updateUser(User user) {
		
		boolean status=false;
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			
			PreparedStatement preparedStatement =connection.prepareStatement(UPDATE_USERS_SQL);
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getCountry());
			preparedStatement.setString(4,user.getPassword());
			
			status=preparedStatement.executeUpdate()>0;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public static void main(String args[]) {
		UserDAO dao=new UserDAO();
		if(dao.getConnection()!=null) {
			System.out.println("Successfully connected to the database!!");
			
		}else {
			System.out.println("Problem in database connection!!");
		}
		
		User user=new User("test","test@abc.com","india","abc@123");
		
		//dao.insertUser(user);
		
		User user1=dao.selectUser(1);
		System.out.println("user1");
		
	}

}