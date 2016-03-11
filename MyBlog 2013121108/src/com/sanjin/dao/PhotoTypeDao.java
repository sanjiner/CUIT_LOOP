package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.PhotoTypeForm;

public class PhotoTypeDao {
String sql="";
	//对照片类别进行查询  删除  添加

//查询
public List<PhotoTypeForm> selectAllPhotoType()
{
	sql="select * from dbo.tb_PhotoType ";
	Connection connection =null;
	Statement statement=null;
	ResultSet  restSet=null;
	List<PhotoTypeForm> list=new ArrayList<>();
	PhotoTypeDao photoTypeDao=new PhotoTypeDao();
	connection=JDBCUtils.getConnection();
	try {
		statement=connection.createStatement();
		restSet=statement.executeQuery(sql);
		while(restSet.next())
		{
			PhotoTypeForm photoTypeForm=new PhotoTypeForm();
			photoTypeForm.setPhotoType_id(restSet.getInt(1));
			photoTypeForm.setPhotoType_info(restSet.getString(2));
			photoTypeForm.setPhotoType_name(restSet.getString(3));
			list.add(photoTypeForm);
			
		}
		return list;
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		System.out.println("查询所有照片分类抛出异常");
		return null;
		
	}
	finally
	{
		JDBCUtils.release(connection, statement, restSet);
	}
	
}


//删除
public int DeletePhotoTypebyTypeId(String PhotoType_id)
{
	sql="delete dbo.tb_PhotoType where PhotoType_id=?";
	Connection connection=null;
	PreparedStatement preparedStatement=null;
	connection=JDBCUtils.getConnection();
	try {
		preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, PhotoType_id);
		return preparedStatement.executeUpdate();
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		System.out.println("在删除相册类别时抛出异常");
		return 0;
		
	}
	finally{
		JDBCUtils.release(connection, preparedStatement, null);
	}
	
	
	}


//添加
	public int AddPhotoType( PhotoTypeForm photoTypeForm)
	{
		sql="insert dbo.tb_PhotoType(PhotoType_info,PhotoType_name) values(?,?)";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, photoTypeForm.getPhotoType_info());
			preparedStatement.setString(2, photoTypeForm.getPhotoType_name());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在添加照片类别时抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, null);
		}
		
	}



}
