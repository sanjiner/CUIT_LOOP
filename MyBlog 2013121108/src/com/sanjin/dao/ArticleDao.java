package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.ArticleForm;
import com.sanjin.form.MasterForm;


public class ArticleDao {
	public String sql="";
	
	//实现文章的增加  修改   删除    查询 ；
	
	//添加文章
	public int addArticle(ArticleForm articleForm)
	{
		sql="insert dbo.tb_article (article_info,article_create,"
				+ "article_sdTime,article_content,article_title,articleType_id,master_loginName) values(?,?,?,?,?,?,?) ";
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,articleForm.getArticle_info());
			preparedStatement.setString(2, articleForm.getArticle_create());
			preparedStatement.setString(3, articleForm.getArticle_sdTime());
			preparedStatement.setString(4, articleForm.getArticle_content());
			preparedStatement.setString(5, articleForm.getArticle_title());
			preparedStatement.setInt(6, articleForm.getArticleType_id());
			preparedStatement.setString(7, articleForm.getMaster_loginName());
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在添加文章时预编译抛出异常");
			return 0;
		}
		
		finally{
			JDBCUtils.release(connection, preparedStatement,null);
		}
		
	}
	//删除文章
	public int deleteArticle(String articletitle)
	{
		sql="delete dbo.tb_article where article_title=?";
		Connection connection=null;
		PreparedStatement preaPreparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preaPreparedStatement=connection.prepareStatement(sql);
			preaPreparedStatement.setString(1,articletitle);
			return preaPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在删除文章时预编译抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preaPreparedStatement,null);
		}
		
	}
	
	//删除文章通过 id
	public int deleteArticleByid(String article_typeID)
	{
		sql="delete dbo.tb_article where article_typeID=?";
		Connection connection=null;
		PreparedStatement preaPreparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preaPreparedStatement=connection.prepareStatement(sql);
			preaPreparedStatement.setString(1,article_typeID);
			return preaPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在删除文章时预编译抛出异常");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preaPreparedStatement,null);
		}
		
	}
	
	//修改文章
	public int updateArticle(ArticleForm articleForm)
	{
		sql="update dbo.tb_article set article_info=?,"
		+ "article_content=?  from dbo.tb_article where article_title=?";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,articleForm.getArticle_info());
			preparedStatement.setString(2, articleForm.getArticle_content());
			preparedStatement.setString(3, articleForm.getArticle_title());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在更新文章时抛出预编译错误");
			return 0;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, null);
		}
		
	}
	//查询前十文章
	public List<ArticleForm> selectArticleForms()
	{
		sql="select top 10 article_title,article_content,article_info,article_create,article_typeID from dbo.tb_article";
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		
		List<ArticleForm> list =new ArrayList<>();
		connection=JDBCUtils.getConnection();
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				ArticleForm articleForm=new ArticleForm();
				articleForm.setArticle_title(resultSet.getString(1));
				articleForm .setArticle_content(resultSet.getString(2));
				articleForm.setArticle_info(resultSet.getString(3));
				articleForm.setArticle_create(resultSet.getString(4));
				articleForm.setArticle_typeID(resultSet.getString(5));
				list.add(articleForm);
				
			}
			System.out.println("在ArticleDao中的list大小"+list.size());
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("查询前十条文章抛出statement异常");
			return null;
		}
		finally
		{
			JDBCUtils.release(connection, statement,resultSet);
		}
	}
	
	
	//查询 我的文章
	public List<ArticleForm> SelectMyArticle(String master_loginname)
	{
		sql="select * from dbo.tb_article where master_loginName=?";
		Connection connection=null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet=null;
		
		List<ArticleForm> list=new ArrayList<>();
		connection =JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, master_loginname);
			resultSet =preparedStatement.executeQuery();
			while(resultSet.next())
			{
				ArticleForm articleForm=new ArticleForm();
				articleForm.setArticle_info(resultSet.getString(3));
				articleForm.setArticle_create(resultSet.getString(4));
				articleForm.setArticle_sdTime(resultSet.getString(5));
				articleForm.setArticle_content(resultSet.getString(6));
				articleForm.setArticle_title(resultSet.getString(7));
				articleForm.setArticle_typeID(resultSet.getString(1));
				list.add(articleForm);
			}
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("查询我的所有文章抛出预编译异常");
			return null;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement,resultSet);
		}
	}
	//根据article_typeID 文章ID  查询文章 
		public ArticleForm selectArticleByarticle_typeID(String article_typeID)
		{
			sql="select * from dbo.tb_article where article_typeID=?";
			Connection connection=null;
			PreparedStatement preparedStatement=null;
			ResultSet resultSet=null;
			connection=JDBCUtils.getConnection();
			ArticleForm articleForm=new ArticleForm();
			try {
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1,article_typeID);
				resultSet=preparedStatement.executeQuery();
				if(resultSet.next())
				{
					articleForm.setArticle_title(resultSet.getString(7));
					articleForm.setArticle_content(resultSet.getString(6));
					articleForm.setArticle_create(resultSet.getString(4));
					articleForm.setArticle_info(resultSet.getString(3));
					articleForm.setMaster_loginName(resultSet.getString(9));
					articleForm.setArticle_typeID(resultSet.getString(1));
					articleForm.setArticle_count(resultSet.getInt(2));
					articleForm.setArticle_sdTime(resultSet.getString(5));
				}
				return articleForm;
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("根据article id 查询抛出异常");
				return null;
			}
			finally{
				JDBCUtils.release(connection, preparedStatement, resultSet);
			}
		}
		
		//根据ArticleType_id 文章类别id  查询所属的文章集合
		public List<ArticleForm> selectArticleByArticleType_id(String ArticleType_id)
		{
			sql="select * from dbo.tb_article where ArticleType_id=?";
			Connection connection=null;
			PreparedStatement preparedStatement=null;
			ResultSet resultSet=null;
			connection=JDBCUtils.getConnection();
			
			connection=JDBCUtils.getConnection();
			List<ArticleForm> list=new ArrayList<>();
			try {
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1, ArticleType_id);
				resultSet=preparedStatement.executeQuery();
				if(resultSet.next())
				{
					ArticleForm articleForm=new ArticleForm();
					articleForm.setArticle_title(resultSet.getString(7));
					articleForm.setArticle_content(resultSet.getString(6));
					articleForm.setArticle_create(resultSet.getString(4));
					articleForm.setArticle_info(resultSet.getString(3));
					articleForm.setMaster_loginName(resultSet.getString(9));
					articleForm.setArticle_typeID(resultSet.getString(1));
					articleForm.setArticle_count(resultSet.getInt(2));
					articleForm.setArticle_sdTime(resultSet.getString(5));
					list.add(articleForm);
				}
				return  list;
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("根据ArticleType_id查询所属的文章集合抛出异常");
				return null;
			}
			
			
		}
		
}
