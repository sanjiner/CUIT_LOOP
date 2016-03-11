package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.PhotoForm;

public class PhotoDAO {
String sql="";
//对照片进行查看  删除  上传

//显示当前分类下的所有照片
public List<PhotoForm> ShowPhotoByType(int  PhotoType_id)
{
	sql="select * from dbo.tb_photo where PhotoType_id=?";
	
	Connection connection=null;
	PreparedStatement preparedStatement=null;
	List<PhotoForm> list=new ArrayList<>();
	connection=JDBCUtils.getConnection();
	ResultSet resultSet=null;
	try {
		preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setInt(1, PhotoType_id);
		resultSet=preparedStatement.executeQuery();
		while(resultSet.next())
		{
			PhotoForm photoForm=new PhotoForm();
			photoForm.setPhotoID(resultSet.getInt(1));
			photoForm.setPhoto_desc(resultSet.getString(2));
			photoForm.setPhoto_addr(resultSet.getString(3));
			photoForm.setPhotoType_id(resultSet.getInt(5));
			photoForm.setMaster_loginName(resultSet.getString(6));
			list.add(photoForm);
			
			
		}
		return list;
		
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		System.out.println("在显示当前分类下的所有照片抛出异常");
		return null;
	}finally
	{
		JDBCUtils.release(connection, preparedStatement, resultSet);
	}

	
	
}



}
