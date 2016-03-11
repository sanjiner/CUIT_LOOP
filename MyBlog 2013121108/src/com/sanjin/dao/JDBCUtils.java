package com.sanjin.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
	private static final String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=Blog"; //自行定义
	private static final String dbUser = "sa"; //自行定义
	private static final String dbPwd =  "TX5842702609"; //自行定义
	private static String driverClass =  "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //自行定义
	//注册驱动
static{
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			System.out.println("加载驱动失败！");
			e.printStackTrace();    
		}
		


//




	}
//获取Connection对象
	public static Connection getConnection(){
		try {
			Connection connection =DriverManager.getConnection(url, dbUser, dbPwd);
			return connection;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("得到connection 失败！");
			return null;
		}
		


	}
	//释放JDBC资源
	public static void release(Connection connection,Statement statement,ResultSet rs){
		try {
			if(rs!=null)
			{
			rs.close();
			}
			else {
				
			}
		} catch (SQLException e2) {
			// TODO 自动生成的 catch 块
			System.out.println("关闭resultset失败");
			
			e2.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			System.out.println("关闭statement失败");
			e1.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.out.println("关闭connection失败");
			e.printStackTrace();
		}
	}
}


