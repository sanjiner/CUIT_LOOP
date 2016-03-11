package com.sanjin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.sanjin.dao.JDBCUtils;

/**
 * Servlet implementation class Smartupload
 */
@WebServlet("/Smartupload")
public class Smartupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Smartupload() {
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
		request.setCharacterEncoding("UTF-8");
//		 request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=UTF-8");
		String PhotoType_id=request.getParameter("id");
		System.out.println("id="+PhotoType_id);
		ServletConfig config = this.getServletConfig();
		 Connection conn=null; 
		 
		     Statement st=null;
conn=JDBCUtils.getConnection();
		//设置request范围字符集

		

		//取得整个web应用的物理根路径（注意不是jsp项目根路径）

//		String root = request.getSession().getServletContext().getRealPath("/");

		//设置上传文件的保存路径（绝对路径/物理路径www.mwcly.cn）

		String root="/upload/";
		String savePath = root + "images/";
//		String savePath;
//		savePath="F:\\Android\\java工作文档\\MyBlog 2013121108\\image\\";

		//声明SmartUpload类对象

		SmartUpload mySmartUpload = new SmartUpload();

		try { 

		//初始化的方法必须先执行

		//参数：config,request,response都是jsp内置对象

		mySmartUpload.initialize(config,request,response);

		//上传文件数据

		mySmartUpload.upload();

		//将全部上传文件保存到指定目录下

		mySmartUpload.save(savePath);
		System.out.println("ddddd"+savePath);

		//取得文件名(因为只上传一个文件，所以用getFile(0))

		String fileName = mySmartUpload.getFiles().getFile(0).getFileName();
//		fileName=new String(fileName.getBytes("UTF-8"),"GBK");
		
		//取得表单中普通控件的值(text,password……)

		String name = mySmartUpload.getRequest().getParameter("name");
//		name= new String(name.getBytes("UTF-8"),"GBK");
		
	System.out.println("相片描述名"+name);

		

		   String sql="INSERT INTO dbo.tb_photo(photo_desc,photo_addr,PhotoType_id) VALUES('"+name+"','"+savePath+fileName+"','"+PhotoType_id+"')";

		      st =  conn.createStatement();    // 创建用于执行静态sql语句的Statement对象   

		int count = st.executeUpdate(sql);  // 执行插入操作的sql语句，并返回插入数据的个数   

		 response.sendRedirect("ShowPhoto.jsp");

		} catch (Exception e){

		    System.out.println("Error : " + e.toString()); 

		}    
//		finally{
//			JDBCUtils.release(conn, st, null);
//		}
		
		
	}

}
