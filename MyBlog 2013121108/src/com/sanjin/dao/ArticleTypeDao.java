package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jspsmart.upload.Request;
import com.sanjin.form.ArticleForm;
import com.sanjin.form.ArticleTypeForm;



public class ArticleTypeDao {
	String sql="";
	
	
	//查询所有的分类项
	public List<ArticleTypeForm> selectAllArticleType()
	{
		sql="select * from dbo.tb_articleType";
		Connection connection=null;
		Statement statement =null;
		List<ArticleTypeForm> list=new ArrayList<>();
		ResultSet resultSet =null;
		connection=JDBCUtils.getConnection();
		
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				ArticleTypeForm articleTypeForm=new ArticleTypeForm();
				articleTypeForm.setArticleType_id(resultSet.getInt(1));
				articleTypeForm.setArticleType_name(resultSet.getString(3));
				list.add(articleTypeForm);
			}
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("执行查询文章所有分类抛出statement 异常");
			return null;
		}
		finally{
			JDBCUtils.release(connection, statement, resultSet);
		}
	}
	
	
	//添加分类
	public int AddArticleType(ArticleTypeForm articleTypeForm)
	{
		sql="insert dbo.tb_articleType(articleType_info,articleType_name)  values(?,?)";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, articleTypeForm.getArticleType_info());
			preparedStatement.setString(2, articleTypeForm.getArticleType_name());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("执行添加文章分类信息抛出preparedstatement异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, null);
		}
		
		
		
	}
	
	
//删除文章类别
	public int DeleteArticleType(String articleType_id)
	{
		sql="delete dbo.tb_articleType where articleType_id=?";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, articleType_id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return 0;
		}
		finally
		{
			JDBCUtils.release(connection, preparedStatement,null);
		}
	}
}
