package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.MasterForm;


//实现对用户的插入  查询  删除 


public class ConsumerDao{
	public String sql="";
	
	//插入
	public  int insertuser(MasterForm masterForm ) {
		// TODO 自动生成的方法存根
		sql="insert dbo.tb_master values(?,null,?,?,?,?,?)";
		Connection connection =JDBCUtils.getConnection();
		PreparedStatement stmPreparedStatement=null;
		try {
			stmPreparedStatement=connection.prepareStatement(sql);
			stmPreparedStatement.setString(1, masterForm.getMaster_loginname());
			stmPreparedStatement .setString(2,masterForm.getMaster_name());
			stmPreparedStatement.setString(3, masterForm.getMaster_realname());
			stmPreparedStatement .setString(4,masterForm.getMaster_password());
			stmPreparedStatement.setString(5, masterForm.getMaster_sex());
			stmPreparedStatement .setString(6,masterForm.getMaster_oicq());
			int number=stmPreparedStatement.executeUpdate();
			return number;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("插入用户的预编译抛出异常");
			return 0;
		}
	finally
	{
		JDBCUtils.release(connection, stmPreparedStatement,null);
	}
	}



	//查询返回list
	public List<MasterForm> selectuser(String master_loginname)
	{
		sql="select * from dbo.tb_master where master_loginname=?";
		Connection connection =JDBCUtils.getConnection();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		List<MasterForm> list=new ArrayList<>();
		MasterForm masterForm=new MasterForm();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,master_loginname );
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				masterForm.setMaster_loginname(master_loginname);
				masterForm.setMaster_name(resultSet.getString(3));
				masterForm.setMaster_realname(resultSet.getString(4));
				masterForm.setMaster_password(resultSet.getString(5));
				masterForm.setMaster_sex(resultSet.getString(6));
				masterForm.setMaster_oicq(resultSet.getString(7));
				list.add(masterForm);
			}
			
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在查询用户时预编译抛出异常");
			return null;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement,resultSet);
		}
		
		
	}
	
	//删除
	public int Deleteuser(String userLoginname)
	{
		sql="delete dbo.tb_master where master_loginname=?";
		Connection connection =JDBCUtils.getConnection();
		PreparedStatement stmPreparedStatement=null;
		try {
			stmPreparedStatement=connection.prepareStatement(sql);
			stmPreparedStatement.setString(1,userLoginname);
			int number=stmPreparedStatement.executeUpdate();
			return number;
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("删除用户时 预编译抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, stmPreparedStatement,null);
		}
	}
	
	//更新
	public int changeuser(MasterForm masterForm)
	{
		sql="update dbo.tb_master set master_name=?,master_realname=?,master_password=?,master_sex=?,master_oicq=? from dbo.tb_master where master_loginname=?";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(6,masterForm.getMaster_loginname());
			preparedStatement.setString(1, masterForm.getMaster_name());
			preparedStatement.setString(2,masterForm.getMaster_realname());
			preparedStatement.setString(3, masterForm.getMaster_password());
			preparedStatement.setString(4, masterForm.getMaster_sex());
			preparedStatement.setString(5, masterForm.getMaster_oicq());
			int number=preparedStatement.executeUpdate();
			return number;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在更新用户信息时抛出异常");
			return 0;
		}
	finally{
		JDBCUtils.release(connection, preparedStatement,null);
	}
		
	}
}