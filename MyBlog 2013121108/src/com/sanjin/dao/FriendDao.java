package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.sanjin.form.FriendForm;

public class FriendDao {
	String sql;
	
	//对朋友进行添加    
	
	// 添加（用登录名查找）
	public int AddUser(String master_loginName,String Mylogname)
	{
		sql="select * from dbo.tb_master where master_loginName=?";
		Connection connection=null;
		int m=0;
		FriendForm friendForm=new FriendForm();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,master_loginName);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				friendForm.setMaster_loginName(resultSet.getString(1));
				friendForm.setFriend_blogID(resultSet.getString(2));
				friendForm.setFriend_oicq(resultSet.getString(7));
				friendForm.setFriend_sex(resultSet.getString(6));
				friendForm.setFriend_name(resultSet.getString(3));
				m=AddtoDataBase(friendForm,Mylogname);
				m++;
			}
			return m;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("添加朋友抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, resultSet);
		}
		
		
		
	}
	
	
	
	public int  AddtoDataBase(FriendForm friendForm,String Mylogname)
	{
		sql="insert dbo.tb_friend(friend_blogID,friend_oicq,friend_sex,"
				+ "friend_name,master_loginName) values(?,?,?,?,?)";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,friendForm.getFriend_blogID());
			preparedStatement.setString(2,friendForm.getFriend_oicq());
			preparedStatement.setString(3, friendForm.getFriend_sex());
			preparedStatement.setString(4, friendForm.getFriend_name());
			preparedStatement.setString(5, Mylogname);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在addtodatabase抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, null);
		}
	}
	
	
	//列出该用户所有好友
	public List<FriendForm> ListAllFriend(String master_loginName)
	{
		sql="select * from dbo.tb_friend where master_loginName=?";
		
		List<FriendForm> list=new ArrayList<>();
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, master_loginName);
			resultSet=preparedStatement.executeQuery();
			System.out.println("resultset长度是"+resultSet.getRow());
			while(resultSet.next())
			{
				FriendForm friendForm=new FriendForm();
				friendForm.setFriend_blogID(resultSet.getString(2));
				friendForm.setFriend_oicq(resultSet.getString(3));
				friendForm.setFriend_sex(resultSet.getString(4));
				friendForm.setFriend_name(resultSet.getString(5));
				friendForm.setMaster_loginName(resultSet.getString(6));
				list.add(friendForm);
			}
			System.out.println("在FriendDao中list 长度"+list.size()+"resultset长度是"+resultSet.getRow());
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在列出当前用户所有的好友时抛出异常");
			return null;
			
		}
		finally
		{
			JDBCUtils.release(connection, preparedStatement, resultSet);
		}
		
	}
}
