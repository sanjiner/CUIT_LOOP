package com.sanjin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanjin.dao.PhotoTypeDao;
import com.sanjin.form.PhotoTypeForm;

/**
 * Servlet implementation class AddPhotoType
 */
@WebServlet("/AddPhotoType")
public class AddPhotoType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPhotoType() {
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
		int number=0;
		request.setCharacterEncoding("UTF-8");
		PhotoTypeDao photoTypeDao=new PhotoTypeDao();
		PhotoTypeForm photoTypeForm=new PhotoTypeForm();
		photoTypeForm.setPhotoType_info(request.getParameter("PhotoType_info"));
		photoTypeForm.setPhotoType_name(request.getParameter("PhotoType_name"));
		number=photoTypeDao.AddPhotoType(photoTypeForm);
		if(number==0)
		{
			request.setAttribute("check", "添加失败！");
			request.getRequestDispatcher("MyPhoto.jsp").forward(request, response);
		}
		else if(number==1)
		{
			request.setAttribute("check", "添加成功！");
			request.getRequestDispatcher("MyPhoto.jsp").forward(request, response);
		}
	}

}
