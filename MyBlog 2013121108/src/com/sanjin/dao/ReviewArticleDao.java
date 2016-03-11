package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.ReviewFrom;



public class ReviewArticleDao {
	String sql="";
	//对文章评论信息的操作
	
	//添加评论
	public int AddArticleReview(ReviewFrom reviewFrom)
	{
		sql="insert dbo.tb_review(review_author,review_content,review_sdTime,article_typeID) values(?,?,?,?)";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, reviewFrom.getReview_author());
			preparedStatement.setString(2,reviewFrom.getReview_content());
			preparedStatement.setString(3,reviewFrom.getReview_sdTime());
			preparedStatement.setString(4,reviewFrom.getArticle_typeID());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return 0;
		}
	finally{
		JDBCUtils.release(connection, preparedStatement, null);
	}
		
	}
	
	
	//列出单个文章的所有评论
	public List<ReviewFrom> SelectAllReviewByArticleId(String article_typeID)
	{
		sql="select * from dbo.tb_review where article_typeID=?";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		List<ReviewFrom> list=new ArrayList<>();
		ResultSet resultSet=null;
		
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,article_typeID);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				ReviewFrom reviewFrom=new ReviewFrom();
				reviewFrom.setReviewID(resultSet.getInt(1));
				reviewFrom.setReview_author(resultSet.getString(2));
				reviewFrom.setReview_content(resultSet.getString(3));
				reviewFrom.setReview_sdTime(resultSet.getString(4));
				reviewFrom.setArticle_typeID(resultSet.getString(5));
				list.add(reviewFrom);
			}
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("列出该文章所有评论抛出statement异常");
			return null;
		}
		finally{
			JDBCUtils.release(connection, preparedStatement, resultSet);
		}
	}

}
