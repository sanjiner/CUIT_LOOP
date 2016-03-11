package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanjin.dao.PhotoTypeDao;

/**
 * Servlet implementation class DeletePhotoType
 */
@WebServlet("/DeletePhotoType")
public class DeletePhotoType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePhotoType() {
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
		PhotoTypeDao photoTypeDao=new PhotoTypeDao();
		int number=photoTypeDao.DeletePhotoTypebyTypeId(request.getParameter("id"));
		if(number==0)
		{
			request.setAttribute("check", "删除失败");
			request.getRequestDispatcher("MyPhoto.jsp").forward(request, response);
		}
		else if(number==1){
			request.setAttribute("check", "删除成功");
			request.getRequestDispatcher("MyPhoto.jsp").forward(request, response);
		}
		
	}

}
