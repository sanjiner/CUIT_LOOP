package com.sanjin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sanjin.form.ReplyForm;

public class ReplyDao {

	 String sql="";
	//对评论进行回复的操作
	
	
	//添加回复
	public int AddReply(ReplyForm replyForm)
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		sql="insert dbo.tb_Reply(Reply_Content,reviewID) values(?,?)";
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, replyForm.getReply_Content());
			preparedStatement.setInt(2, replyForm.getReviewID());
			 return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("添加回复抛出预编译错误");
			return 0;
		}
	}
	
	//通过reviewID查询回复
	public ReplyForm SelectReplyByreviewID(int  ReviewID)
	{
		sql="select * from dbo.tb_Reply where reviewID=?";
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		ReplyForm replyForm=new ReplyForm();
		List<ReplyForm> list=new ArrayList<>();
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, ReviewID);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				
				replyForm.setReply_Content(resultSet.getString(3));
				replyForm.setReply_id(resultSet.getInt(1));
				replyForm.setReviewID(resultSet.getInt(4));
				
				
				
			}
			return replyForm;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("在通过reviewID查询回复时抛出异常");
			return null;
		}
		
		
		
	}

}
