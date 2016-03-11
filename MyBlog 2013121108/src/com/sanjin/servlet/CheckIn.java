package com.sanjin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sanjin.dao.JDBCUtils;
import com.sanjin.form.MasterForm;


@WebServlet("/dologin.html")
public class CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		MasterForm masterForm=new MasterForm();
		ResultSet resultSet=null;
		String loginnameString="";
		String password="";
		HttpSession session=request.getSession();
		String master_loginName=request.getParameter("master_loginName");
		String master_password=request.getParameter("master_password");
		String sql="select * from dbo.tb_master where master_loginName=? and master_password=?";
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		connection=JDBCUtils.getConnection();
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, master_loginName);
			preparedStatement.setString(2,master_password );
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				loginnameString=resultSet.getString(1);
				password=resultSet.getString(5);
				masterForm.setMaster_loginname(resultSet.getString(1));
				masterForm.setMaster_blogID(resultSet.getString(2));
				masterForm.setMaster_name(resultSet.getString(3));
				masterForm.setMaster_realname(resultSet.getString(4));
				masterForm.setMaster_password(resultSet.getString(5));
				masterForm.setMaster_sex(resultSet.getString(6));
				masterForm.setMaster_oicq(resultSet.getString(7));
			}
			if(loginnameString.equals(master_loginName)&& password.equals(master_password))
			{
				
				session.setAttribute("user", masterForm);
				response.sendRedirect("index.jsp");
			}
			else {
				response.sendRedirect("login.jsp");
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}

}
